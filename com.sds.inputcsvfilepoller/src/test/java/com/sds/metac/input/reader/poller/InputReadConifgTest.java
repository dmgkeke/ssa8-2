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
import com.sds.metac.input.reader.poller.InputReadConfig;
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
		String configXmlPath = "./config/inputReadConfig.xml";
		InputReadConfig inputReadConfig = new InputReadConfig(configXmlPath);

		InputCsvFilePoller inputCsvFilePoller = new InputCsvFilePoller();

		File file = inputCsvFilePoller.getFileInfo(inputReadConfig
				.getInputFileInfoMap().get("code"));

		boolean result = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			counter++;
			while (line != null) {
				line = br.readLine();

				if (StringUtil.isEmpty(line) || line == null) {
					// 종료, static counter 초기화
					result = false;
					counter = 0;
				} else {
					logger.debug(line);
					result = true;

					name = line.split("\\" + splitter)[0];
					value = line.split("\\" + splitter)[1];
					// 초기값
					if (tmpKey == null) {
						tmpKey = name;
						groupVO = new GroupVO();
					}
					if (!tmpKey.equals(name)) {
						result = true;
						groupVO = new GroupVO();
					} else {
						result = false;
					}
					groupVO.addCodeSet(name, value);
					tmpKey = name;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException("!!! FileNotFoundException - doHasNext()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - doHasNext()");
		}

	}
}
