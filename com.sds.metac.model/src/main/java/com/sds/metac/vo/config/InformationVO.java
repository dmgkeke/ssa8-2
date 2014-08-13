package com.sds.metac.vo.config;

import java.util.List;

import com.sds.metac.vo.AbstractVO;
import com.sds.metac.vo.core.ClassInfoVO;

public class InformationVO extends AbstractVO {

	private List<ClassInfoVO> inputReaderInfoList;
	private List<ClassInfoVO> outputWriterInfoList;
	private List<ClassInfoVO> postProcessorInfoList;

	public List<ClassInfoVO> getPostProcessorInfoList() {
		return postProcessorInfoList;
	}

	public void setPostProcessorInfoList(List<ClassInfoVO> postProcessorInfoList) {
		this.postProcessorInfoList = postProcessorInfoList;
	}

	public List<ClassInfoVO> getOutputWriterInfoList() {
		return outputWriterInfoList;
	}

	public void setOutputWriterInfoList(List<ClassInfoVO> outputWriterInfoList) {
		this.outputWriterInfoList = outputWriterInfoList;
	}

	public List<ClassInfoVO> getInputReaderInfoList() {
		return inputReaderInfoList;
	}

	public void setInputReaderInfoList(List<ClassInfoVO> inputReaderInfoList) {
		this.inputReaderInfoList = inputReaderInfoList;
	}
}
