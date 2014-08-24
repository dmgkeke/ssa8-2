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
		boolean isHeaderWrod = true;
		boolean isHeaderCode = true;
		boolean isNextStandard = false;
		BufferedReader bufferedReaderWord = new BufferedReader(new FileReader(
				"./inputfiles/word.csv"));
		try {
			while (true) {
				String readLine = bufferedReaderWord.readLine();
				// 중간에 공백이 생기는 부분 처리
				if (StringUtil.isEmpty(readLine)) {
					while (true) {
						readLine = bufferedReaderWord.readLine();
						if (readLine == null) {
							isNextStandard = false;
							bufferedReaderWord.close();
						} else {
							break;
						}
					}
				}
				if (readLine == null) {
					isNextStandard = false;
					bufferedReaderWord.close();
				} else {
					// 파일에 타이틀이 포함된경우, 한줄을 더읽음
					if (isHeaderWrod) {
						readLine = bufferedReaderWord.readLine();
						isHeaderWrod = false;
					}
					logger.debug(readLine);
					standardVO = new StandardVO(readLine.split(splitter)[0],
							readLine.split(splitter)[0]);
					isNextStandard = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - hasNextStandard()");
		}
	}

}
