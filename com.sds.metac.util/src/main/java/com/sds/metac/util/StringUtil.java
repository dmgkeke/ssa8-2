package com.sds.metac.util;

import com.sds.metac.exception.MetaCException;

public class StringUtil {

	public static final String EMPTY = "";

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
	 * 문자열을 앞에서 부터 length 길이만금 잘라낸 후 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String splitStr(String targetStr, int length) {
		String result = "";

		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}

		result = targetStr.substring(0, length);

		return result;
	}
}
