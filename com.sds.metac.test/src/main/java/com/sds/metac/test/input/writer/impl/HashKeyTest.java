package com.sds.metac.test.input.writer.impl;

import java.io.File;
import java.util.SortedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jramoyo.io.IndexedFileReader;

public class HashKeyTest {
	
	
	
	public static void main(String[] args) throws Exception {
		
		String key = "계약상태코드";
		System.out.println(key.hashCode());
		
		String fileName = getFileName(key);
		System.out.println(fileName);
		
		//File file = new File(fileName);
		
		String indexFileName = getIndexFileName(fileName);
		System.out.println(indexFileName);
		
		
		int hash = createHash(key);
		
		System.out.println(hash);
		
		File indexFile = new File(indexFileName);
		IndexedFileReader indexReader = new IndexedFileReader(indexFile);
		
		SortedMap<Integer, String> indexLineMap = indexReader.readLines(hash, hash);
		String indexInfo = indexLineMap.get(hash);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		IndexData indexData = gson.fromJson(indexInfo, IndexData.class);
		
		System.out.println(indexData.getIndex());
		
		indexReader.close();
		
		File dataFile = new File(fileName);
		IndexedFileReader dataReader = new IndexedFileReader(dataFile);
		
		SortedMap<Integer, String> dataLineMap = dataReader.readLines(indexData.getIndex(), indexData.getIndex());
		String dataInfo = dataLineMap.get(indexData.getIndex());
		
		System.out.println(dataInfo);
	}

	private static int createHash(String key) {
		int hash = 10;
		
		for (int i=0 ; i<key.length() ; i++) {
			hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		
		return Math.abs(hash%10)+1;
	}

	private static String getIndexFileName(String fileName) {
		return "index" + fileName;
	}

	private static String getFileName(String key) {		
		return "" + key.substring(0, 1).hashCode() + ".dat";
	}
	
}

/**
 * @author JUNE
 *
 */
class IndexData {
	private String hashCode;
	private Integer index;
	public String getHashCode() {
		return hashCode;
	}
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
}
