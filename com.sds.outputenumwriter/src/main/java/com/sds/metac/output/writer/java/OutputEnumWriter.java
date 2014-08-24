package com.sds.metac.output.writer.java;

import static com.sun.codemodel.JExpr._this;
import static com.sun.codemodel.JExpr.lit;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;
import com.sun.codemodel.ClassType;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public class OutputEnumWriter implements OutputJavaWriter {

	Logger logger = Logger.getLogger(OutputEnumWriter.class);
	JCodeModel codeModel = new JCodeModel();
	JDefinedClass definedClass;
	
	GroupVO groupVO;
	StandardVO standardVO;
	
	private final static String DEFAULT_PACKAGE = "code";
	private final static String CODEVALUE_FIELD_NAME = "codeValue";
	
	private final static String ENUM_FILE_PATH = "temp/";
	
	@Override
	public void write(GroupVO groupVO, StandardReader standardReader) {
		this.groupVO = groupVO;
		if (standardReader != null) {
			this.standardVO = standardReader.getStandardVO(groupVO.getName());
		}
		
		try {
			createEnum();
			
			createEnumConstant();
			
			createMethod();
			
			codeModel.build(new File(ENUM_FILE_PATH));
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		logger.debug("Enum 으로 변환해라 : " + groupVO);
		logger.debug(standardVO);
		
		
	}
	
	@Override
	public void write(GroupVO groupVO, Map<String, String> keySet) {
		// FIXME Deprecated
	}



	private void createEnum() throws JClassAlreadyExistsException {
		definedClass = codeModel.rootPackage()._class(JMod.PUBLIC, getCodeName(), ClassType.ENUM);
		definedClass.javadoc().append(standardVO.getValue() + " 공통코드");
	}

	private void createEnumConstant() throws JClassAlreadyExistsException{
		JEnumConstant enumConstant = definedClass.enumConstant(standardVO.getName());
		enumConstant.arg(lit("코드값 : " + getCodeMap().get(standardVO.getName())));
		
		enumConstant.javadoc().append(standardVO.getValue());
	}
	
	private void createMethod() throws JClassAlreadyExistsException {
		JFieldVar codeValue = definedClass.field(JMod.PRIVATE, String.class, CODEVALUE_FIELD_NAME);
		codeValue.javadoc().append("코드에 매핑된 실제 값");
		
		JMethod constructor = definedClass.constructor(JMod.PRIVATE);
		constructor.param(String.class, CODEVALUE_FIELD_NAME);
		constructor.body().assign(_this().ref(codeValue), codeValue);
		
		JMethod getCodeValue = definedClass.method(JMod.PUBLIC, String.class, "get" + getFieldNameWithFirstLetterToUpperCase(CODEVALUE_FIELD_NAME));
		getCodeValue.body()._return(JExpr._this().ref(codeValue));
	}
	
	private String getCodeName() {
		return groupVO.getName();
	}
	
	private Map<String, String> getCodeMap() {
		return groupVO.getCodeMap();
	}
	
	private String getFieldNameWithFirstLetterToUpperCase(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
