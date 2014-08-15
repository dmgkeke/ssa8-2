package com.sds.metac.exception;

public class MetaCException extends RuntimeException {

	public MetaCException(String string) {
		super(string);
	}

	public MetaCException(Exception e) {
		super(e);
	}
}
