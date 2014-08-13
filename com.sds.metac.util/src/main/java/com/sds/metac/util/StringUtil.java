package com.sds.metac.util;

public class StringUtil {
	
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
}
