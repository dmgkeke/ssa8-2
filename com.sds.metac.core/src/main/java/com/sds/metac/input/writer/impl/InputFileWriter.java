package com.sds.metac.input.writer.impl;

import org.apache.log4j.Logger;

import com.sds.metac.input.writer.InputWriter;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputFileWriter implements InputWriter {

	Logger logger = Logger.getLogger(InputFileWriter.class);

	public void write(GroupVO groupVO) {
		logger.debug("Group : " + groupVO);
	}

	public void write(StandardVO standardVO) {
		logger.debug("Standard : " + standardVO);
	}
}
