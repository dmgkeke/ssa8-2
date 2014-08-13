package com.sds.metac.file;

import java.nio.file.Paths;

public enum FileManager {
	INSTANCE;

	private static final String PATH = "file:/" + Paths.get("").toAbsolutePath().toString();
	
	public MetaCFile readFile(String fileName) {		
		
		return new MetaCFile();
	}

	
	public String getResourceFilePath(String path) {
		return PATH + "\\" + path;
	}
}
