package com.sds.metac.output.writer.java;

import org.apache.log4j.Logger;

import com.sds.metac.vo.domain.GroupVO;

public class OutputEnumWriter implements OutputJavaWriter {

	Logger logger = Logger.getLogger(OutputEnumWriter.class);

	public void write(GroupVO groupVO) {
		logger.debug("Enum 으로 변환해라 : " + groupVO);
	}

}
