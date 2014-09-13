package com.sds.metac.util;


import java.util.ArrayList;
import java.util.List;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.util.StringUtil;

public class MetacCommonUtil {
	// FIXME 현재는 Util 패키지에서 Impl config schema를 불러올 수 없으므로 일단 하드코딩
	private static final String TOP_DOWN = "topDown";
	private static final String BOTTOM_UP = "bottomUp";

	/**
	 * targetStr을 표준용어로 교체하여 리턴
	 * 
	 * @param targetStr
	 * @param minLength
	 * @return split된 row(list형태)들을 담고 있는 list
	 */
	public static List<List<String>> getStandardStrList(String targetStr, int minLength, String metaFomula) {
		List<List<String>> standardStrList = new ArrayList<List<String>>();
		List<String> standardStrRow = new ArrayList<String>();
		
		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}
		
		if( TOP_DOWN.equals(metaFomula) ) {
			putTopDownStandardStrRow(standardStrList, standardStrRow, targetStr, minLength, 0);
		}else if( BOTTOM_UP.equals(metaFomula) ) {
			putBottomUpStandardStrRow(standardStrList, standardStrRow, targetStr, minLength, 0);
		}
		
		return standardStrList;
	}
	
	

	/**
	 * split된 형태의 표준용어 row를 전체 list에 넣어준다. - TopDown 형식
	 * 
	 * @param list
	 * @param row
	 * @param targetStr
	 * @param minLength
	 * @param depth
	 */
	private static void putTopDownStandardStrRow(List<List<String>> list, List<String> row, String targetStr, int minLength, int depth) {
		for (int i = targetStr.length(); i >= minLength; i--) {
			String preStr = StringUtil.splitStr(targetStr, i);
			String postStr = StringUtil.splitStrOfIndex(targetStr, i);
			
			putStrToRow(row, preStr, depth);
			
			if(StringUtil.isEmpty(postStr)) {
				list.add(new ArrayList<String>(row));
			}else if(postStr.length() == minLength) {
				putStrToRow(row, postStr, depth+1);
				list.add(new ArrayList<String>(row));
			}else if(postStr.length() > minLength){
				putTopDownStandardStrRow(list, row, postStr, minLength, depth+1);
			}else {
				rowClearFromDepth(row, depth);
			}
		}
	}
	
	/**
	 * split된 형태의 표준용어 row를 전체 list에 넣어준다. - BottomUp 형식
	 * 
	 * @param list
	 * @param row
	 * @param targetStr
	 * @param minLength
	 * @param depth
	 */
	private static void putBottomUpStandardStrRow(List<List<String>> list, List<String> row, String targetStr, int minLength, int depth) {
		for (int i = minLength; i <= targetStr.length(); i++) {
			String preStr = StringUtil.splitStr(targetStr, i);
			String postStr = StringUtil.splitStrOfIndex(targetStr, i);
			
			putStrToRow(row, preStr, depth);
			
			if(StringUtil.isEmpty(postStr)) {
				list.add(new ArrayList<String>(row));
			}else if(postStr.length() == minLength) {
				putStrToRow(row, postStr, depth+1);
				list.add(new ArrayList<String>(row));
			}else if(postStr.length() > minLength){
				putBottomUpStandardStrRow(list, row, postStr, minLength, depth+1);
			}else {
				rowClearFromDepth(row, depth);
			}
		}
	}
	
	private static void putStrToRow(List<String> row, String str, int depth) {
		if(row.size() > depth && StringUtil.isNotEmpty(row.get(depth))) {
			rowClearFromDepth(row, depth);
		}
		
		row.add(depth, str);
	}
	
	private static void rowClearFromDepth(List<String> row, int depth) {
		row.removeAll(row.subList(depth, row.size()));
	}
	
}
