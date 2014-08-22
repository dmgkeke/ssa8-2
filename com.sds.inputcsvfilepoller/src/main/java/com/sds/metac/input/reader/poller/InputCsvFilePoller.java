package com.sds.metac.input.reader.poller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.input.reader.poller.InputPoller;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputCsvFilePoller implements InputPoller {

	// logging
	Logger logger = Logger.getLogger(InputCsvFilePoller.class);

	// counter
	int countStandard = 0;
	int countGroup = 0;

	// 대상 파일 위치 및 설정값
	final String filePathWord = "./inputfiles/word.csv";
	final String filePathCode = "./inputfiles/code.csv";
	final String splitter = "\\|";
	boolean isHeaderWrod = true;
	boolean isHeaderCode = true;

	// 그룹VO의 코드값비교를 위한 변수
	String nextLine;
	boolean isKeyChanged = false;

	// file read 관련
	BufferedReader bufferedReaderWord;
	BufferedReader bufferedReaderCode;

	// VO
	GroupVO groupVO;
	StandardVO standardVO;

	// 생성자
	public InputCsvFilePoller() {
		// 각각 버퍼를 read하며 vo 생성
		try {
			bufferedReaderWord = new BufferedReader(
					new FileReader(filePathWord));
			bufferedReaderCode = new BufferedReader(
					new FileReader(filePathCode));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException(
					"!!! FileNotFoundException - InputCsvFilePoller()");
		}
	}

	// 단어 next
	public boolean hasNextStandard() {
		boolean isNextStandard = false;
		try {
			String readLine = bufferedReaderWord.readLine();
			// 중간에 공백이 생기는 부분 처리
			if (StringUtil.isEmpty(readLine)) {
				while (true) {
					readLine = bufferedReaderWord.readLine();
					if (readLine == null) {
						isNextStandard = false;
						break;
					} else {
						if (StringUtil.isEmpty(readLine)) {
							continue;
						}
						break;
					}
				}
			}
			if (readLine == null) {
				isNextStandard = false;
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
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - hasNextStandard()");
		}

		return isNextStandard;
	}

	// 공통코드 next
	public boolean hasNextGroup() {
		boolean isNextStandard = false;
		String readLine = "";
		try {
			// 읽다가 키값이 변경된경우는 읽지않고 이전 loop에 읽었던 nextLine 값을 사용
			if (isKeyChanged) {
				isKeyChanged = false;
				readLine = nextLine;
			} else {
				readLine = bufferedReaderCode.readLine();
			}

			if (readLine == null) {
				isNextStandard = false;
			} else {
				// 파일에 타이틀이 포함된경우, 한줄을 더읽음
				if (isHeaderCode) {
					readLine = bufferedReaderCode.readLine();
					isHeaderCode = false;
				}
				logger.debug(readLine);

				// 현재라인 저장
				groupVO = new GroupVO();
				groupVO.setName(readLine.split(splitter)[0]);
				groupVO.addCodeSet(readLine.split(splitter)[1],
						readLine.split(splitter)[2]);

				// 다음라인과 비교
				while (true) {
					nextLine = bufferedReaderCode.readLine();
					// 중간에 공백이 생기는 부분 처리
					if (StringUtil.isEmpty(nextLine)) {
						while (true) {
							nextLine = bufferedReaderCode.readLine();
							if (nextLine == null) {
								isNextStandard = false;
								break;
							} else {
								if (StringUtil.isEmpty(nextLine)) {
									continue;
								}
								break;
							}
						}
					}
					// 다음라인이 null인경우 마지막 코드 저장 후종료
					if (nextLine == null) {
						isNextStandard = true;
						break;
					}

					// 다음라인의 키값과 같은경우 코드값저장
					if (readLine.split(splitter)[0].equals(nextLine
							.split(splitter)[0])) {
						logger.debug(nextLine);
						groupVO.addCodeSet(nextLine.split(splitter)[1],
								nextLine.split(splitter)[2]);
					} else {
						// 다음라인과 코드값이 달라진경우 플래그를 주고 종료
						isKeyChanged = true;
						isNextStandard = true;
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - hasNextGroup()");
		}

		return isNextStandard;
	}

	// 단어 VO
	public StandardVO readStandard() {
		StandardVO standardVO = new StandardVO();
		standardVO.setName(this.standardVO.getName());
		standardVO.setName(this.standardVO.getValue());
		return standardVO;
	}

	// 공통코드 VO
	public GroupVO readGroup() {

		GroupVO groupVO = new GroupVO();
		groupVO.setName(this.groupVO.getName());
		groupVO.setCodeMap(this.groupVO.getCodeMap());
		return groupVO;
	}

}
