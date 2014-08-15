package com.sds.metac.file;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.sds.metac.exception.MetaCException;

public enum FileManager {
	INSTANCE;

	private static final String PATH = "file:/" + Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_PATH = Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_SEP = "\\";
	
	public MetaCFile readFile(String fileName) {		
		
		return new MetaCFile();
	}

	
	public String getResourceFilePath(String path) {
		return PATH + FOLDER_SEP + path;
	}
	
	public <T> T readConfigXmlFile(String fileName, Class<T> clazz) {
		return readXmlFile(FOLDER_PATH + FOLDER_SEP + "config", fileName, clazz);
	}


	public <T> T readXmlFile(String folder, String fileName, Class<T> clazz) {		
		File file = new File(folder + FOLDER_SEP + fileName);
		
		if (!file.exists()) {
			this.createFile(file);
		}
		
		return createObjectFromXml(file, clazz);
	}


	@SuppressWarnings("unchecked")
	private <T> T createObjectFromXml(File file, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
						
			return (T)unmarshaller.unmarshal(file);
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}


	private void createFile(File file) {
		try {
			file.createNewFile();
		}
		catch (Exception e) {
			throw new MetaCException(e);
		}
	}
}
