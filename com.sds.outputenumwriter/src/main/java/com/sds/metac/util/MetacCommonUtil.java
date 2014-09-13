package com.sds.metac.util;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.schema.MetaFomula;
import com.sds.metac.vo.domain.StandardVO;

public class MetacCommonUtil {

	/**
	 * targetStr을 표준용어로 교체하여 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String convertStandardStr(String targetStr, MetaFomula metaFomula, int minLength, StandardReader standardReader) {
		String result = "";
		
		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}

		// 문자열의 길이 관련 variable
		int firstStrLength = 0;
		int splitPosition = 0;
		int searchCnt = 0;
		int sign = 0;
		
		if(MetaFomula.TOP_DOWN.equals(metaFomula)) {
			firstStrLength = targetStr.length();
			sign = -1;
		}else {
			firstStrLength = minLength;
			sign = 1;
		}
		
		splitPosition = firstStrLength;
		searchCnt = targetStr.length() - minLength;
		
		for (int i = 0; i <= searchCnt; i++) {
			splitPosition = firstStrLength + (sign * i);
			
			if( (result = getPostStandardStr(targetStr, splitPosition, minLength, standardReader)) != null) {
				return result;
			} 
		}
		
		return null;
	}
	
	/**
	 * targetStr에서 첫 단어의 길이가 preStrLength 만큼이고 minLength단위로 찾아지는 표준용어를 얻는다.
	 * 
	 * @param targetStr
	 * @param preStrLength
	 * @param minLength
	 * @param standardReader
	 * @return
	 */
	private static String getPostStandardStr(String targetStr, int preStrLength, int minLength, StandardReader standardReader) {
		String result = "";
		
		// 문자열의 길이 관련 variable
		int splitPointer = preStrLength;
		String splitStr = StringUtil.splitStr(targetStr, splitPointer);

		// 문자열의 왼쪽부터 마지막 글자까지 단어를 분리해가며 search
		while (true) {
			try {
				String standardValue = getStandardValue(splitStr, standardReader);
				
				if(splitPointer == targetStr.length()) {
					if(standardValue == null) {
						return null;
					}
					result += standardValue;
					break;
				}else if(standardValue == null) {
					splitStr = splitStr + StringUtil.splitStr(targetStr, splitPointer, splitPointer+minLength);
					splitPointer += minLength;
					continue;
				}
				
				result += standardValue + "_";
				splitStr = StringUtil.splitStr(targetStr, splitPointer, splitPointer + minLength);
				splitPointer += minLength;
			}catch (Exception e) {
				return null;
			}
		}

		return result;
	}
	
	/**
	 * str과 매치되는 표준용어를 얻는다. 
	 * 
	 * @param str
	 * @param standardReader
	 * @return
	 */
	private static String getStandardValue(String str, StandardReader standardReader) {
		StandardVO standardVO = standardReader.getStandardVO(str);
		if(standardVO == null) {
			return null;
		}else {
			return standardVO.getValue();
		}
	}
}
