package com.sds.metac.input.reader.poller;

public class InputFileInfo {

	String key;
	String extention;
	String fileName;
	String filePath;
	String spiltter;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSpiltter() {
		return spiltter;
	}

	public void setSpiltter(String spiltter) {
		this.spiltter = spiltter;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
