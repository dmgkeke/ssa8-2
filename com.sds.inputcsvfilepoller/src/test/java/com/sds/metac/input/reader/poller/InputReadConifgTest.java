package com.sds.metac.input.reader.poller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.input.reader.poller.InputCsvFilePoller;

import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputReadConifgTest {

	// count 변수
	static int counter = 0;

	String splitter = "|";
	String name;
	String value;
	Map<String, String> codeMap;
	String tmpKey = null;

	// final string
	final String configXmlPath = "./config/inputReadConfig.xml";
	final String CODE = "code"; // 공통코트
	final String WORD = "word"; // 단어

	// logging
	Logger logger = Logger.getLogger(InputReadConifgTest.class);

	// groupVO
	GroupVO groupVO;
	// strndartVO
	StandardVO standardVO;

	@Test
	public void test() throws ParserConfigurationException, SAXException,
			IOException {

	}

}
