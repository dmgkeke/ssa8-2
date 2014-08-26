package com.sds.metac.input.reader.poller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.input.reader.poller.InputReadConfig;
import com.sds.metac.input.reader.vo.CustomVO;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class TestInputJsonFilePllerTest {

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
	Logger logger = Logger.getLogger(TestInputJsonFilePllerTest.class);

	// groupVO
	GroupVO groupVO;
	// strndartVO
	StandardVO standardVO;

//	@Test
	/*public void test() throws ParserConfigurationException, SAXException,
			IOException {
		String configXmlPath = "./config/inputReadConfig.xml";
		InputReadConfig inputReadConfig = new InputReadConfig(configXmlPath);
		Map<String, InputFileInfo> temp = inputReadConfig.getInputFileInfoMap();
		System.out.println(temp.keySet());
		System.out.println(temp.get("code").getExtention());

		TestInputJsonFilePoller inputJsonFilePoller = new TestInputJsonFilePoller();

		File file = inputJsonFilePoller.getFileInfo(inputReadConfig
				.getInputFileInfoMap().get("code"));
		System.out.println(file);
		boolean result = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			counter++;
//			System.out.println(line);
//			System.out.println(line);
//			System.out.println(line);
//			System.out.println(line);
			
			
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JsonReader tmp = new JsonReader(new FileReader(file));
//			System.out.println(tmp);
			JsonObject test = gson.fromJson(tmp, JsonObject.class);
//			System.out.println(test);
			
			System.out.println(test.get("DESCRIPTION"));
//			System.out.println(test.get("DATA"));
			
			JsonArray array = test.get("DATA").getAsJsonArray();
			System.out.println(array.size());
			System.out.println(array.get(0));
			
			
			CustomVO[] codeVOArr = gson.fromJson(array, CustomVO[].class);
			System.out.println("array: "+codeVOArr[0]);
			
			List list = new ArrayList<CustomVO>(Arrays.asList(codeVOArr));
			System.out.println("list: "+list.get(0));
//			JsonObject temp = gson.fromJson(new FileReader(file), CodeVO.class);
			
//			while (line != null) {
//				line = br.readLine();
//				System.out.println(line);
//				if (StringUtil.isEmpty(line) || line == null) {
//					// 종료, static counter 초기화
//					result = false;
//					counter = 0;
//				} else {
//					logger.debug(line);
//					result = true;
//
//					name = line.split("\\" + splitter)[0];
//					value = line.split("\\" + splitter)[1];
//					// 초기값
//					if (tmpKey == null) {
//						tmpKey = name;
//						groupVO = new GroupVO();
//					}
//					if (!tmpKey.equals(name)) {
//						result = true;
//						groupVO = new GroupVO();
//					} else {
//						result = false;
//					}
//					groupVO.addCodeSet(name, value);
//					tmpKey = name;
//				}
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException("!!! FileNotFoundException - doHasNext()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - doHasNext()");
		}

	}*/
	
	@Test
	public void test() throws Exception {
		TestInputJsonFilePoller inputJsonFilePoller = new TestInputJsonFilePoller();
		while(inputJsonFilePoller.hasNextGroup()){
			System.out.println(inputJsonFilePoller.readGroup());
		}
		while(inputJsonFilePoller.hasNextStandard()){
			System.out.println(inputJsonFilePoller.readStandard());
		}
	}
}
