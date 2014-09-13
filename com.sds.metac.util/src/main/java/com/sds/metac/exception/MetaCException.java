package com.sds.metac.exception;

import org.apache.log4j.Logger;

public class MetaCException extends RuntimeException {

	private static final Logger logger = Logger.getLogger(MetaCException.class);
	
	public MetaCException(String msg) {		
		super(msg);
		printError(msg, null);
	}

	public MetaCException(Exception e) {
		super(e);
		printError(null, e);
	}
	public MetaCException(String msg, Exception e) {
		super(msg, e);
		printError(msg, e);
	}
	
	
	private void printError(String msg, Exception e) {
		if (msg == null) {
			msg = "";
		}
		if (e == null) {
			logger.debug(msg);
		}
		else {
			logger.debug(msg, e);
		}
	}
}
