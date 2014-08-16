package com.sds.metac.util;

public class StringUtil {
	
	public static final String EMPTY = "";
	
	/**
	 * 두 문자열 비교
	 * 둘중 하나라도 null 이면 false
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
	 * @param key
	 * @return
	 */
	public static boolean isEmpty(String key) {
		return key == null || key.trim().isEmpty();
	}
}
