package com.sds.metac.util;

import com.sds.metac.exception.MetaCException;
import com.sds.metac.output.reader.impl.StandardFileReader;
import com.sds.metac.vo.domain.StandardVO;

public class MetacCommonUtil {

	/**
	 * targetStr을 표준용어로 교체하여 리턴
	 * 
	 * @param targetStr
	 * @param length
	 * @return
	 */
	public static String convertStandardStr(String targetStr, int length) {
		// 결과문자열
		String result = "";
		StandardVO standardVO = null;
		StandardFileReader standardFileReader = new StandardFileReader();

		// input이 null or empty 인경우
		if (targetStr == null || StringUtil.isEmpty(targetStr)) {
			throw new MetaCException("변경할 용어가 null입니다!!");
		}

		// 문자열의 길이 관련 variable
		int searchCnt = 0;
		int splitPointer = 0;
		String splitStr = "";

		// 문자열의 왼쪽부터 마지막 글자까지 단어를 분리해가며 search
		while (searchCnt != length) {
			searchCnt++;

			// 용어 분리
			splitStr = targetStr.substring(splitPointer, searchCnt);
			try {
				standardVO = standardFileReader.getStandardVO(splitStr);
				// 용어가 존재하는경우 영문명_ 를 붙임
				result += standardVO.getValue() + "_";
				// System.out.println("converting... -> " + result);
				splitPointer = searchCnt;
			} catch (Exception e) {
				// 문자열의 길이까지 도달했지만 용어를 찾지 못한경우
				if (searchCnt == length) {
					result += targetStr.substring(splitPointer, searchCnt);
					String failResult = "문자열찾기 실패!! -> 대상 : " + targetStr
							+ ", 결과 : " + result;
					// System.out.println(failResult);
					throw new MetaCException(failResult);
				}
				continue;
			}
		}
		// 정상적으로 종료된 경우(마지막 _를 제거)
		result = result.substring(0, result.length() - 1);
		System.out.println("result : " + result);
		return result;
	}
}
