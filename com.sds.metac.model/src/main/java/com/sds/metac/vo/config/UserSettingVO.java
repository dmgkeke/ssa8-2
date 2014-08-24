package com.sds.metac.vo.config;

import com.sds.metac.vo.AbstractVO;

public class UserSettingVO extends AbstractVO {

	private String inputReaderName;
	private String outputWriterName;
	private String postProcessorName;
	
	private String implementationFolder;
	private String tempFileFolder;
	
	private String tempFileExt;
	
	private Integer maxCacheSize;
	
	

	public Integer getMaxCacheSize() {
		return maxCacheSize;
	}

	public void setMaxCacheSize(Integer maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}

	public String getTempFileFolder() {
		return tempFileFolder;
	}

	public void setTempFileFolder(String tempFileFolder) {
		this.tempFileFolder = tempFileFolder;
	}

	public String getPostProcessorName() {
		return postProcessorName;
	}

	public void setPostProcessorName(String postProcessorName) {
		this.postProcessorName = postProcessorName;
	}

	public String getOutputWriterName() {
		return outputWriterName;
	}

	public void setOutputWriterName(String outputWriterName) {
		this.outputWriterName = outputWriterName;
	}

	public String getInputReaderName() {
		return inputReaderName;
	}

	public void setInputReaderName(String readerName) {
		this.inputReaderName = readerName;
	}

	public String getImplementationFolder() {
		return implementationFolder;
	}

	public void setImplementationFolder(String implementationFolder) {
		this.implementationFolder = implementationFolder;
	}

	public String getTempFileExt() {
		return tempFileExt;
	}

	public void setTempFileExt(String tempFileExt) {
		this.tempFileExt = tempFileExt;
	}
	
}
