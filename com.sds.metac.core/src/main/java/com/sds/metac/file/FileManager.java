package com.sds.metac.file;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.sds.metac.exception.MetaCException;

public enum FileManager {
	INSTANCE;
	
	public static final String DOT = ".";

	private static final String PATH = "file:/" + Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_PATH = Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_SEP = "\\";
	
	
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
	
	public File loadFile(String folder, String fileName) {
		File file = new File(FOLDER_PATH + FOLDER_SEP + folder + FOLDER_SEP + fileName);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (Exception e) {
				throw new MetaCException(e);
			}
		}
		
		return file;
	}


	private void createFile(File file) {
		try {
			file.createNewFile();
		}
		catch (Exception e) {
			throw new MetaCException(e);
		}
	}


	public File createNewFile(String folder, String fileName) {
		File file = new File(FOLDER_PATH + FOLDER_SEP + folder + FOLDER_SEP + fileName);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		try {
			file.createNewFile();
		} catch (Exception e) {
			throw new MetaCException(e);
		}
		
		return file;
	}

	public void deleteAllFiles(String folderName) {
		File folder = new File(FOLDER_PATH + FOLDER_SEP + folderName);
		
		if (!folder.isDirectory()) {
			return;
		}
		
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				file.delete();
			}
			else if (file.isDirectory()) {
				deleteAllFiles(folderName + FOLDER_SEP + file.getName());
				file.delete();
			}
		}
	}

	public void createFolder(String folderName) {
		File folder = new File(FOLDER_PATH + FOLDER_SEP + folderName);
		
		if (folder.exists()) {
			return;
		}
		else {
			folder.mkdirs();
		}
	}
	
	public File loadFolder(String folderName) {
		File folder = new File(FOLDER_PATH + FOLDER_SEP + folderName);
		
		if (folder.exists()) {
			return folder;
		}
		else {
			folder.mkdirs();
			return folder;
		}
	}
}
