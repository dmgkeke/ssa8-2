package com.sds.metac.output.reader.impl;

import org.apache.log4j.Logger;

import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.vo.domain.GroupVO;

public class OutputFileReader implements OutputReader {

	Logger logger = Logger.getLogger(OutputFileReader.class);

	// 테스트용
	int count = 0;

	public boolean hasNextGroup() {
		return count++ < 2;
	}

	public GroupVO readGroup() {
		// TODO Auto-generated method stub
		GroupVO groupVO = new GroupVO();
		groupVO.setName("" + count);

		return groupVO;
	}

	public void validateGroup(GroupVO groupVO) {
		// TODO Auto-generated method stub
		logger.debug("검사 : " + groupVO);
	}

}
