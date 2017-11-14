package services;

import java.io.File;

public interface IFileLoader {

	/**
	 * Carga todos los ficheros ubicados bajo un directorio. En función de su extensión se utiliza un método u otro para su lectura
	 */
	void loadDir();
	
	/**
	 * Recibe un File en formato .txt, lo lee y lo pinta por pantalla
	 * @param _file
	 */
	void loadTxt(File _file);
	
	/**
	 * Recibe un File en formato .xls, lo lee y lo pinta por pantalla
	 * @param _file
	 */
	void loadExcel(File _file);
	
	/**
	 * Recibe un File en formato distinto a .xls o .txt y muestra un mensaje indicando que no se puede leer este formato
	 * @param _file
	 */
	void unknownFileFormat(File _file);

}
