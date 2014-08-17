package com.sds.metac.output.writer.java;

import java.io.FileWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaEnumSource;

import com.sds.metac.vo.domain.GroupVO;

public class OutputEnumWriter implements OutputJavaWriter {

	Logger logger = Logger.getLogger(OutputEnumWriter.class);
	JavaEnumSource source = Roaster.create(JavaEnumSource.class);
	
	private final static String DEFAULT_PACKAGE = "code";
	private final static String CODEVALUE_FIELD_NAME = "codeValue";
	
	private final static String ENUM_FILE_PATH = "temp/";

	public void write(GroupVO groupVO, Map<String, String> keySet) {
		createEnum(groupVO.getName());
		
		createEnumConstant(groupVO.getCodeMap());
		
		createConstructor();
		
		createGetterMethod();
		
		createEnumFile(groupVO.getName() + ".java");
		
		logger.debug("Enum 으로 변환해라 : " + groupVO);
		logger.debug(keySet);
	}


	private void createEnum(String enumName) {
		source.setDefaultPackage();
		
		source.setName(enumName).setPublic();
	}

	private void createEnumConstant(Map<String, String> codeMap) {
		for (String key : codeMap.keySet()) {
			source.addEnumConstant(key)
				.setConstructorArguments(codeMap.get(key));
		}
	}
	
	private void createConstructor() {
		source.addField()
			.setName(CODEVALUE_FIELD_NAME).setType(String.class);
		
		source.addMethod()
			.setConstructor(true)
			.setBody("this." + CODEVALUE_FIELD_NAME + " = " + CODEVALUE_FIELD_NAME + ";")
			.addParameter(String.class, CODEVALUE_FIELD_NAME);
	}
	
	private void createGetterMethod() {
		source.addMethod()
			.setName("get" + CODEVALUE_FIELD_NAME.substring(0, 1).toUpperCase() + CODEVALUE_FIELD_NAME.substring(1))
			.setPublic()
			.setReturnType(String.class)
			.setBody("return " + CODEVALUE_FIELD_NAME + ";");
	}
	
	private void createEnumFile(String fileName) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(ENUM_FILE_PATH + fileName);
			fw.append(source.toString());
			fw.close();
		} catch (Exception e) {
		} finally {
			try {
				fw.close();
			} catch (Exception e2) {
			}
		}
	}
}
