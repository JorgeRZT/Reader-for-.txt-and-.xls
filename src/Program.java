import org.springframework.context.support.ClassPathXmlApplicationContext;

import services.IFileLoader;

public class Program {
	
	private static final String CONTEXT_CONFIG = "applicationContext.xml";
	private static IFileLoader fileLoader;
	private static ClassPathXmlApplicationContext applicationContext ;
	
	public static void main(String [] args) {
		applicationContext = new ClassPathXmlApplicationContext(CONTEXT_CONFIG);
		fileLoader = (IFileLoader) applicationContext.getBean("fileLoaderBean");
		fileLoader.loadDir();
	}

}
