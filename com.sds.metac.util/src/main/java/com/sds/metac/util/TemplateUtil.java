package com.sds.metac.util;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;



public class TemplateUtil {
	private static final Logger LOGGER = Logger.getLogger(TemplateUtil.class);
	
	public static Template getTemplate(String path, String templateName) {
		return getTemplate(path, templateName, getDefaultEncodingType());
	}
	
	public static Template getTemplate(String path, String templateName, String encodingType) {
		try {
			Velocity.init();

			Template t = Velocity.getTemplate(path + FileManager.FOLDER_SEP + templateName);
			t.setEncoding(encodingType);
			
			return t;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new MetaCException(e);
		}
	}
	
	public static VelocityContext getVelocityContext() {
		return new VelocityContext();
	}
	
	public static String getCompleteClass(Template template, VelocityContext context) {
		Writer writer = new StringWriter();
		
		try {
			template.merge(context, writer);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new MetaCException(e);
		}
		
		return writer.toString();
	}
	
	private static String getDefaultEncodingType() {
		return "UTF-8";
	}
}
