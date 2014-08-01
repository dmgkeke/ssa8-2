import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {
	private static Printer newPrinterFromJar() throws Exception {
		String path = "file:/E:/dev/workspace_meta/05 ImplementOut/Implement.jar";
		URL url = new URL(path);
		
		URLClassLoader loader = URLClassLoader.newInstance(new URL[]{url});
		
		Class<?> c = loader.loadClass("PrinterImpl");
		
		return (Printer) c.newInstance();
	}
	
	private static Printer newPrinterFromClass() throws Exception {
		String path = "E:\\dev\\workspace_meta\\05 ImplementOut";
		File file = new File(path);
		URLClassLoader loader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
		Class<?> c = loader.loadClass("PrinterImpl");
		
		return (Printer)c.newInstance();
	}
	
	public static void main(String[] args) throws Exception {
		Printer p = newPrinterFromJar();
		
		p.out();
		
		p = newPrinterFromClass();
		
		p.out();
	}
}
