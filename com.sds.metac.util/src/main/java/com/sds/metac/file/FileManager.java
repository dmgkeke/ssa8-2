package com.sds.metac.file;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.sds.metac.exception.MetaCException;

public enum FileManager {
	INSTANCE;
	
	/**
	 * 확장자 구분자 DOT
	 */
	public static final String DOT = ".";

	private static final String PATH = "file:/" + Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_PATH = Paths.get("").toAbsolutePath().toString();
	private static final String FOLDER_SEP = "\\";
	
	private static final String FOLDER_CONFIG = "config";
	
	/**
	 * 프로그램의 리소스를 사용하는 파일 Path
	 * 
	 * file://절대경로 형태를 취한다.
	 * 
	 * @param path
	 * @return
	 */
	public String getResourceFilePath(String path) {
		return PATH + FOLDER_SEP + path;
	}
	
	/**
	 * 환경설정이 포함되어있는 폴더를 읽는다
	 * - 프로그램경로\config 밑을 탐색한다.
	 * 
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	public <T> T readConfigXmlFile(String fileName, Class<T> clazz) {
		return readXmlFile(FOLDER_PATH + FOLDER_SEP + FOLDER_CONFIG, fileName, clazz);
	}


	/**
	 * 
	 * 특정 폴더(프로그램 하위) 에 있는 xml 파일을 읽는다.
	 *  - 파일이 존재하지 않으면 생성한다.
	 *  
	 * @param folder
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	public <T> T readXmlFile(String folder, String fileName, Class<T> clazz) {		
		File file = new File(folder + FOLDER_SEP + fileName);
		
		if (!file.exists()) {
			this.createFile(file);
		}
		
		return createObjectFromXml(file, clazz);
	}


	/**
	 * XML 파일 전체를 하나의 Master Class로 읽는다 (JAXB 활용)
	 * @param file
	 * @param clazz
	 * @return
	 */
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
	
	/**
	 * 파일을 읽는다 (프로그램 폴더 하위)
	 *  - 없을경우에 상위 폴더구조부터 생성
	 *  
	 * @param folder
	 * @param fileName
	 * @return
	 */
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


	/**
	 * 파일을 생성한다
	 * @param file
	 */
	private void createFile(File file) {
		try {
			file.createNewFile();
		}
		catch (Exception e) {
			throw new MetaCException(e);
		}
	}


	/**
	 * 프로그램 폴더 밑에 특정 파일을 생성한다
	 * @param folder
	 * @param fileName
	 * @return
	 */
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

	/**
	 * 프로그램 하위 폴더내의 특정폴더의 모든 파일을 삭제한다 (폴더내부 포함)
	 * @param folderName
	 */
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

	/**
	 * 특정 폴더 생성
	 * @param folderName
	 */
	public void createFolder(String folderName) {
		File folder = new File(FOLDER_PATH + FOLDER_SEP + folderName);
		
		if (folder.exists()) {
			return;
		}
		else {
			folder.mkdirs();
		}
	}
	
	/**
	 * 특정 폴더 읽기
	 * @param folderName
	 * @return
	 */
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
