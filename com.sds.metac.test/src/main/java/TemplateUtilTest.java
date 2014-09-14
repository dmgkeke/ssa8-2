import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.sds.metac.file.FileManager;
import com.sds.metac.util.TemplateUtil;


public class TemplateUtilTest {
	static String path = "outputTemplates";
	static String templateName = "outputFinalWriterTemplate.template";
	
	static List<Code> list = new ArrayList<Code>();
	
	static String className = "CodeTemplateClass";
	
	static {
		list.add(new Code("CODE_NUM_1", "1"));
		list.add(new Code("CODE_NUM_2", "2"));
		list.add(new Code("CODE_NUM_3", "3"));
		list.add(new Code("CODE_NUM_4", "4"));
	}
	
	public static void main(String[] args) {
		Template template = TemplateUtil.getTemplate(path, templateName);
		VelocityContext context = TemplateUtil.getVelocityContext();
		
		context.put("className", className);
		context.put("codes", list);
		
		System.out.println(TemplateUtil.getCompleteClass(template, context));
		
		File file = new File(path + FileManager.FOLDER_SEP + className + ".java");
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(file);
			writer.write(TemplateUtil.getCompleteClass(template, context));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
