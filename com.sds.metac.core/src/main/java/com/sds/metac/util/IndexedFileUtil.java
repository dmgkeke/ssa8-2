package com.sds.metac.util;

import java.io.File;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.file.FileManager;
import com.sds.metac.vo.config.UserSettingVO;

public class IndexedFileUtil {

	public static final String TAG_INDEX = "index_";
	public static final String INDEX_SEP = "##";
	public static final String TAG_STN = "stn_";
	public static final String TAG_GRP = "grp_";
	
	/**
	 * JSON 스트링의 첫번째 키를 추출한다.
	 * 
	 * @param line
	 * @return
	 */
	public static String getFirstJsonKey(String line) {
		String ret = null;
		int first = line.indexOf(":");
		int last = line.indexOf("\"", first+2);
		
		ret = line.substring(first+2, last);
		
		return ret;
	}
	
	
	/**
	 * 문자열에 대한 Hash값을 구한다.
	 * 
	 * @param key
	 * @return
	 */
	public static int createHash(String key) {
		int value = 256;
		int hash = value;
		
		for (int i=0 ; i<key.length() ; i++) {
			hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		
		return Math.abs(hash%value)+1;
	}
	
	/**
	 * 용어와 구분자를 이용해서 Temp파일명을 생성한다.
	 * 
	 * @param name
	 * @param tag
	 * @return
	 */
	public static String createFileName(String name, String tag) {
		return tag + name.substring(0, 1).hashCode();
	}
	
	/**
	 * 파일명에 대한 인덱스 파일명을 구한다.
	 * @param fileName
	 * @return
	 */
	public static String createIndexFileName(String fileName) {
		return TAG_INDEX + fileName;
	}
	
	/**
	 * 임시 파일을 읽는다. (없으면 생성)
	 * @param name
	 * @param tag
	 * @return
	 */
	public static File readTempFile(String name, String tag) {
		FileManager fileManager = FileManager.INSTANCE;
		ConfigManager configManager = ConfigManager.INSTANCE;
		
		UserSettingVO userSetting = configManager.getUserSetting();
		String folder = userSetting.getTempFileFolder();
		String ext = userSetting.getTempFileExt();
		
		String fileName = IndexedFileUtil.createFileName(name, tag) + FileManager.DOT + ext;
		
		File file = fileManager.loadFile(folder, fileName);
		
		return file;
	}
}
