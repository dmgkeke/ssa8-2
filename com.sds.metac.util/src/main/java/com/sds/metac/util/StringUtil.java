package com.sds.metac.util;

import com.sds.metac.exception.MetaCException;

public class StringUtil {

	public static final String EMPTY = "";
	public static final String SPACE = " ";

	/**
	 * Oracle nvl과 동일
	 * null일때 default반환
	 */
	public static String nvl(String val, String defaultVal) {
		return val == null ? defaultVal : val;
	}
	
	/**
	 * 두 문자열 비교 둘중 하나라도 null 이면 false
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static boolean equals(String left, String right) {
		return left != null && left.equals(right);
	}

	/**
	 * 문자열 변환
	 */
	public static String toString(Object o) {
		return EMPTY + o;
	}

	/**
	 * 문자열 Empty 체크
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isEmpty(String key) {
		return key == null || key.trim().isEmpty();
	}
	
	/**
	 * 문자열 NotEmpty 체크
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isNotEmpty(String key) {
		return !isEmpty(key);
	}
	
	/**
	 * 문자열 trim처리
	 */
	public static String trim(String str) {
		return str != null ? str.trim() : str;
	}

	/**
	 * 문자열을 앞에서 부터 length 길이만금 잘라낸 후 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String splitStr(String targetStr, int length) {
		return splitStr(targetStr, 0, length);
	}
	
	/**
	 * 문자열을 앞에서 부터 length 길이만금 잘라낸 후 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String splitStr(String targetStr, int startIndex, int endIndex) {
		String result = "";

		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}

		result = targetStr.substring(startIndex, endIndex);

		return result;
	}
	
	/**
	 * 문자열을 index 이후 문자열로 잘라낸 후 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String splitStrOfIndex(String targetStr, int index) {
		String result = "";

		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}

		result = targetStr.substring(index);

		return result;
	}
	
	/**
	 * 첫번째 문자를 대문자로 변경한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceUpperCaseToFirstChar(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		return splitStr(str, 1).toUpperCase() + splitStrOfIndex(str, 1);
	}
	
	/**
	 * 첫번째 문자를 소문자로 변경한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceLowerCaseToFirstChar(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		return splitStr(str, 1).toLowerCase() + splitStrOfIndex(str, 1);
	}
	
	/**
	 * Underscore형태의 문자열을 CamelCase로 변경한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String convertUnderscoreToCamel(String str) {
		StringBuffer sb = new StringBuffer();
		
		if (isEmpty(str)) {
			return str;
		}
		
		for (String splitStr : str.split("_")) {
			if(isNotEmpty(str)) {
				splitStr = splitStr.toLowerCase();
				sb.append(replaceUpperCaseToFirstChar(splitStr));
			}
		}
		
		
		return sb.toString();
	}
	
	/**
	 * CamelCase형태의 문자열을 Underscore형태로 변경한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String convertCamelToUnderscore(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		return str.replaceAll("(.)([A-Z]+)", "$1_$2").toUpperCase();
	}
	
}
