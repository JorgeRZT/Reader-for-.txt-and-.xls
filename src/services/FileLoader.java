package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class FileLoader implements IFileLoader {

	private static final URL APP_CONFIG = Thread.currentThread().getContextClassLoader().getResource("properties.properties");

	private static String dirPath = "dirPath";
	private static File[] files = null;

	public FileLoader() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(APP_CONFIG.getPath()));
			dirPath = properties.getProperty(dirPath);

			files = new File(dirPath).listFiles();
		} catch (IOException e) {

		}
	}

	@Override
	public void loadTxt(File _file) {
		mostrarCabecera(_file.getName());
		try(BufferedReader br = new BufferedReader(new FileReader(_file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	System.out.println(line);
		    }
		}catch(Exception e) {
			System.out.println(String.format("Error {}", e.getMessage()));
		}
	}
	
	private void mostrarCabecera(String _titulo) {
		System.out.println("\n\n");
		System.out.println("===============================================================================================================");
		System.out.println("Leyendo ["+_titulo+"]");
		System.out.println("===============================================================================================================");
		System.out.println("\n\n");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadExcel(File _file) {
		mostrarCabecera(_file.getName());
		try {
			FileInputStream file = new FileInputStream(_file);
	
			HSSFWorkbook workbook = new HSSFWorkbook(file);
	
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			Row row;
	
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell celda;
				while (cellIterator.hasNext()) {
					celda = cellIterator.next();
					switch (celda.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(celda)) {
							System.out.println(celda.getDateCellValue());
						} else {
							System.out.println(celda.getNumericCellValue());
						}
						System.out.println(celda.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.println(celda.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(celda.getBooleanCellValue());
						break;
					}
				}
			}
			workbook.close();
		}catch(Exception e) {
			System.out.println(String.format("Erro: {}", e.getMessage()));
		}
	}

	@Override
	public void unknownFileFormat(File _file) {
		mostrarCabecera(_file.getName());
		System.out.println(String.format("Formato de fichero %s desconocido", _file.getName()));
	}

	@Override
	public void loadDir() {
		for (File file : files) {
			if (file.getName().toLowerCase().endsWith(".txt")) {
				loadTxt(file);
			}else if (file.getName().toLowerCase().endsWith(".xls")) {
				loadExcel(file);
			}else {
				unknownFileFormat(file);
			}
		}
	}

}
