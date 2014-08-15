package com.sds.metac.vo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sds.metac.vo.AbstractVO;

/**
 * 공통코드 집합
 * 
 * - 구성요소
 * 		공통코드명
 * 		{코드명, 코드값}
 * 
 * - 코드명은 유일해야하며
 *   코드값도 사실상 유일해야한다
 *   하지만 자료구조상 코드값은 중복이 발생할 수 있다
 * 
 * @author JUNE
 */
public class GroupVO extends AbstractVO {
	/** 공통코드명 */
	private String name;
	
	/** 공통코드의 집합 */
	private Map<String, String> codeMap = new HashMap<String, String>();

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addCodeSet(String code, String codeValue) {
		codeMap.put(code, codeValue);
	}
	
	public void addCodeSets(Map<String, String> codeMap) {
		this.codeMap.putAll(codeMap);
	}
	
	/**
	 * 코드명 Set을 리턴한다
	 * @return
	 */
	public Set<String> getCodes() {
		return codeMap.keySet();
	}
	
	/**
	 * 코드값을 리턴한다
	 * @param code
	 * @return
	 */
	public String getCodeValue(String code) {
		return codeMap.get(code);
	}
	
	/**
	 * 코드값들을 리턴한다
	 * @return
	 */
	public List<String> getCodeValues() {
		List<String> ret = new ArrayList<String>();
		
		for (String key : codeMap.keySet()) {
			ret.add(codeMap.get(key));
		}
		
		return ret;
	}
	
	
	/**
	 * 코드명에 해당하는 내용을 삭제한다
	 * @param code
	 */
	public void removeCode(String code) {
		codeMap.remove(code);
	}
	
	/**
	 * 코드값에 해당하는 내용을 삭제한다
	 * @param codeValue
	 */
	public void removeCodeValue(String codeValue) {
		String delCode = null;
		for (String code : codeMap.keySet()) {
			String value = codeMap.get(code);
			
			if (value.equals(codeValue)) {
				delCode = code;
				break;
			}
		}
		if (delCode != null) {
			codeMap.remove(delCode);
		}
	}
}
