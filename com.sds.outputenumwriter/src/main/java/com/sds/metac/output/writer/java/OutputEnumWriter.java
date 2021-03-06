package com.sds.metac.output.writer.java;

import static com.sun.codemodel.JExpr._this;
import static com.sun.codemodel.JExpr.lit;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sds.metac.config.OutputConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.schema.OutputWriterConfig;
import com.sds.metac.util.MetacCommonUtil;
import com.sds.metac.util.StringUtil;
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

	private final Logger logger = Logger.getLogger(OutputEnumWriter.class);
	private final JCodeModel codeModel = new JCodeModel();
	private final static OutputConfigManager CONFIG_MANAGER = OutputConfigManager.INSTANCE;
	private final static OutputWriterConfig CONFIG = CONFIG_MANAGER.getOutputWriterConfig();
	
	private JDefinedClass definedClass;
	
	private GroupVO groupVO;

	private String standardCodeName;
	
	private StandardReader standardReader;
	
	private final static String CODEVALUE_FIELD_NAME = "codeValue";
	
	private final static String ENUM_FILE_PATH = CONFIG.getTempFilePath();
	
	@Override
	public void write(GroupVO groupVO, StandardReader standardReader) {
		this.groupVO = groupVO;
		this.standardReader = standardReader;
		logger.debug("Enum 으로 변환해라 : " + groupVO);
		
		try {
			if (standardReader != null && getCodeName() != null) {
				// FIXME 일단 TOP-DOWN, 두글자 이상, underscore 방식으로 구현 
				// 차후에 ConfigManager를 통하여 처리하도록 수정해야함
				this.standardCodeName = convertStandardStr(getCodeName());
				
				logger.debug("standardCodeName : " + standardCodeName);
				if(StringUtil.isEmpty(standardCodeName)) {
					logger.debug("Enum 으로 변환실패 : " + groupVO);
					return;
				}
				createEnum();
				
				createEnumConstant();
				
				createMethod();
				
				codeModel.build(new File(ENUM_FILE_PATH));
			}
		
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new MetaCException(e);
		}
		logger.debug("Enum 으로 변환성공 : " + groupVO);
		logger.debug(standardCodeName);
	}
	
	@Deprecated
	@Override
	public void write(GroupVO groupVO, Map<String, String> keySet) {
		// FIXME Deprecated
	}

	private void createEnum() throws JClassAlreadyExistsException {
		definedClass = codeModel.rootPackage()._class(JMod.PUBLIC, standardCodeName, ClassType.ENUM);
		definedClass.javadoc().append(getCodeName() + " 공통코드");
	}

	private void createEnumConstant() throws JClassAlreadyExistsException{
		for (String key : getCodeMap().keySet()) {
			String variableStr = convertStandardStr(key);
			
			if(StringUtil.isEmpty(variableStr)) {
				logger.debug(variableStr + "에 매칭되는 값을 찾을 수 없습니다.");
				continue;
			}
			
			JEnumConstant enumConstant = definedClass.enumConstant(variableStr);
			enumConstant.arg(lit(getCodeMap().get(key)));
			
			enumConstant.javadoc().append(key);
		}
	}
	
	private void createMethod() throws JClassAlreadyExistsException {
		JFieldVar codeValue = definedClass.field(JMod.PRIVATE, String.class, CODEVALUE_FIELD_NAME);
		codeValue.javadoc().append("코드에 매핑된 실제 값");
		
		JMethod constructor = definedClass.constructor(JMod.PRIVATE);
		constructor.param(String.class, CODEVALUE_FIELD_NAME);
		constructor.body().assign(_this().ref(codeValue), codeValue);
		
		JMethod getCodeValue = definedClass.method(JMod.PUBLIC, String.class, "get" + StringUtil.replaceUpperCaseToFirstChar(CODEVALUE_FIELD_NAME));
		getCodeValue.body()._return(JExpr._this().ref(codeValue));
	}
	
	private String getCodeName() {
		return groupVO.getName();
	}
	
	private Map<String, String> getCodeMap() {
		return groupVO.getCodeMap();
	}
	
	private String convertStandardStr(String targetStr) {
		String standardStr = "";
		
		List<List<String>> standardStrList = MetacCommonUtil.getStandardStrList(targetStr, CONFIG.getMinCharSize(), CONFIG.getMetaFomula().value()); 
		
		for (List<String> row : standardStrList) {
			for (String splitStr : row) {
				StandardVO result = null;
				if( (result = standardReader.getStandardVO(splitStr)) == null ) {
					standardStr = "";
					break;
				}
				standardStr += (StringUtil.isNotEmpty(standardStr)?"_":"") + result.getValue();
			}
			
			if(StringUtil.isNotEmpty(standardStr)) {
				switch (CONFIG.getVariableSyntax()) {
				case CAMEL_CASE:
					return StringUtil.convertUnderscoreToCamel(standardStr);
				case UNDERSCORE_CASE:
					return standardStr.toUpperCase();
				default:
					return standardStr;
				}
			}
		}
		
		return null;
	}
	
}
