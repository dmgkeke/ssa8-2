package com.sds.metac.input.reader.poller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.input.reader.poller.InputPoller;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputCsvFilePoller implements InputPoller {
	// 테스트를 위한 변수 - 미사용
	static int countStandard = 0;
	static int countGroup = 0;
	// count 변수
	static int counter = 0;

	String splitter;
	String name;
	String key;
	String value;

	static String tmpName = "";
	static String tmpLine = "";
	static boolean codeMapComplete = false;

	// final string
	final String configXmlPath = "./config/inputReadConfig.xml";
	final String CODE = "code"; // 공통코트
	final String WORD = "word"; // 단어

	// logging
	Logger logger = Logger.getLogger(InputCsvFilePoller.class);

	// groupVO
	GroupVO groupVO;
	// strndartVO
	StandardVO standardVO;

	// 공통코드
	public boolean hasNextGroup() {
		boolean ret = false;

		InputReadConfig inputReadConfig = this.getConfigInfo();
		splitter = inputReadConfig.getInputFileInfoMap().get(CODE)
				.getSpiltter();
		InputFileInfo wordFileInfo = inputReadConfig.getInputFileInfoMap().get(
				CODE);
		File wordFile = this.getFileInfo(wordFileInfo);

		ret = doHasNext(wordFile, CODE, splitter);
		return ret;
	}

	// 단어
	public boolean hasNextStandard() {
		boolean ret = false;

		InputReadConfig inputReadConfig = this.getConfigInfo();
		splitter = inputReadConfig.getInputFileInfoMap().get(WORD)
				.getSpiltter();
		InputFileInfo wordFileInfo = inputReadConfig.getInputFileInfoMap().get(
				WORD);
		File wordFile = this.getFileInfo(wordFileInfo);

		ret = doHasNext(wordFile, WORD, splitter);
		return ret;
	}

	@SuppressWarnings("resource")
	public boolean doHasNext(File file, String type, String splitter) {
		boolean result = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			counter++;
			for (int i = 0; i < counter; i++) {
				line = br.readLine();
			}
			if (StringUtil.isEmpty(line) || line == null) {
				// 종료, static counter 초기화
				result = false;
				counter = 0;
			} else {
				logger.debug(line);
				result = true;

				if (WORD.equals(type)) {
					name = line.split("\\" + splitter)[0];
					value = line.split("\\" + splitter)[1];
					standardVO = new StandardVO(name, value);
				} else if (CODE.equals(type)) {
					name = line.split("\\" + splitter)[0];
					key = line.split("\\" + splitter)[1];
					value = line.split("\\" + splitter)[2];
					// 초기값
					if (counter == 1) {
						groupVO = new GroupVO();
					}

					logger.debug(tmpName + " : " + name);
					// 이전값과 비교
					if (!StringUtil.isEmpty(tmpName) && !tmpName.equals(name)) {
						codeMapComplete = true;
						tmpName = name;
						tmpLine = line;
						return true;
					} else {
						if (codeMapComplete) {
							codeMapComplete = false;
							groupVO = new GroupVO();
							groupVO.setName(tmpLine.split("\\" + splitter)[0]);
							groupVO.addCodeSet(
									tmpLine.split("\\" + splitter)[1],
									tmpLine.split("\\" + splitter)[2]);
						}
						codeMapComplete = false;
					}
					groupVO.setName(name);
					groupVO.addCodeSet(key, value);
					tmpName = name;
					logger.debug("tmpName : " + tmpName + "**************");
					// 마지막 값 확인
					if (br.readLine() == null) {
						codeMapComplete = true;
						return true;
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException("!!! FileNotFoundException - doHasNext()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - doHasNext()");
		}
		return result;
	}

	public GroupVO readGroup() {
		GroupVO groupVO = null;
		if (codeMapComplete) {
			groupVO = new GroupVO();
			groupVO.setName(this.groupVO.getName());
			groupVO.setCodeMap(this.groupVO.getCodeMap());
		}
		return groupVO;
	}

	public StandardVO readStandard() {
		StandardVO standardVO = new StandardVO();
		standardVO.setName(this.name);
		standardVO.setValue(this.value);
		return standardVO;
	}

	public File getFileInfo(InputFileInfo inputFileInfo) {
		String uri = inputFileInfo.getFilePath() + inputFileInfo.getFileName()
				+ "." + inputFileInfo.getExtention();
		return new File(uri);
	}

	public InputReadConfig getConfigInfo() {
		InputReadConfig inputReadConfig = null;
		try {
			inputReadConfig = new InputReadConfig(configXmlPath);
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
			throw new MetaCException(
					"!!! ParserConfigurationException - getConfigInfo()");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new MetaCException("!!! SAXException - getConfigInfo()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - getConfigInfo()");
		}
		return inputReadConfig;
	}

}
