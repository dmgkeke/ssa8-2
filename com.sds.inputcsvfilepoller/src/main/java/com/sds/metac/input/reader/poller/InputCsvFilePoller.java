package com.sds.metac.input.reader.poller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	String filePathWordSort = "";
	String filePathCodeSort = "";

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
			// 표준단어 정렬
			filePathWordSort = this.sortSvcFile(filePathWord, isHeaderWrod);
			// 코드 정렬
			filePathCodeSort = this.sortSvcFile(filePathCode, isHeaderCode);

			bufferedReaderWord = new BufferedReader(new FileReader(
					filePathWordSort));
			bufferedReaderCode = new BufferedReader(new FileReader(
					filePathCodeSort));
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
			// 끝까지 다읽은 경우
			if (readLine == null) {
				isNextStandard = false;
			} else {
				logger.debug(readLine);
				standardVO = new StandardVO(readLine.split(splitter)[0],
						readLine.split(splitter)[0]);
				isNextStandard = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - hasNextStandard()");
		}

		if (!isNextStandard) {
			try {
				bufferedReaderWord.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new MetaCException(
						"!!! IOException - hasNextStandard() - closd()");
			}
		}
		return isNextStandard;
	}

	// 공통코드 next
	public boolean hasNextGroup() {
		boolean isNextGroup = false;
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
				isNextGroup = false;
			} else {
				logger.debug(readLine);

				// 현재라인 저장
				groupVO = new GroupVO();
				groupVO.setName(readLine.split(splitter)[0]);
				groupVO.addCodeSet(readLine.split(splitter)[1],
						readLine.split(splitter)[2]);

				// 다음라인과 비교
				while (true) {
					nextLine = bufferedReaderCode.readLine();

					// 다음라인이 null인경우 마지막 코드 저장 후종료
					if (nextLine == null) {
						isNextGroup = true;
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
						isNextGroup = true;
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - hasNextGroup()");
		}

		if (!isNextGroup) {
			try {
				bufferedReaderCode.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new MetaCException(
						"!!! IOException - hasNextGroup() - close()");
			}
		}
		return isNextGroup;
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

	// 파일정렬 후 temp 새로운 temp 파일생성하여 file명을 리턴
	public String sortSvcFile(String fileName, boolean isHeader) {
		String outputFile = fileName + "_sort.csv";
		List<String> fileReadList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = "";
			// 타이틀이 있는경우 건너뜀
			if (isHeader) {
				line = br.readLine();
			}
			while ((line = br.readLine()) != null) {
				// 공백line 건너뜀
				if (StringUtil.isEmpty(line)) {
					continue;
				}
				fileReadList.add(line);
			}
			br.close();
			// 정렬
			Collections.sort(fileReadList);

			// output 생성
			BufferedWriter wr = new BufferedWriter(new FileWriter(outputFile));
			for (int i = 0; i < fileReadList.size(); i++) {
				wr.write(fileReadList.get(i));
				wr.newLine();
			}
			wr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException(
					"!!! FileNotFoundException - hasNsortSvcFile()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException(
					"!!! FileNotFoundException - IOException()");
		}

		return outputFile;
	}

}
