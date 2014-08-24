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
	
	/** 공통코드 자체의 Method */
	private Method method = Method.NEW;
	
	/** 각 코드별 Method */
	private Map<String, Method> codeMethodMap = new HashMap<String, Method>();

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, String> getCodeMap() {
		return codeMap;
	}
	
	/** GroupVO 전체의 Method 설정 */
	public Method getMethod() {
		return method;
	}

	/** GroupVO 전체의 Method */
	public void setMethod(Method method) {
		this.method = method;
	}

	public Map<String, Method> getCodeMethodMap() {
		return codeMethodMap;
	}

	/** JSON 클래스를 위한 용도 가급적 사용하지 말것 */
	public void setCodeMethodMap(Map<String, Method> codeMethodMap) {
		this.codeMethodMap = codeMethodMap;
	}
	
	/** JSON 클래스를 위한 용도 가급적 사용하지 말것 */
	public void setCodeMap(Map<String, String> codeMap) {
		this.codeMap = codeMap;
	}
	
	/** 코드 추가, DEFAULT - NEW */
	public void addCodeSet(String code, String codeValue) {
		codeMap.put(code, codeValue);
		codeMethodMap.put(code, Method.NEW);
	}
	
	/** 코드 추가, Method 지정필요 */
	public void addCodeSet(String code, String codeValue, Method codeMethod) {
		codeMap.put(code, codeValue);
		codeMethodMap.put(code, codeMethod);
	}
	
	/** 코드집합 전체 추가, DEFAULT - NEW */
	public void addCodeSets(Map<String, String> codeMap) {
		this.codeMap.putAll(codeMap);
		for (String key : codeMap.keySet()) {
			this.codeMethodMap.put(key, Method.NEW);
		}
	}
	
	/** 코드집합 전체추가, Method 는 일괄지정 */
	public void addCodeSet(Map<String, String> codeMap, Method method) {
		this.codeMap.putAll(codeMap);
		for (String key : codeMap.keySet()) {
			this.codeMethodMap.put(key, method);
		}
	}
	
	/** 코드집합 전체추가, Method는 codeMethodMap에서 추출 */
	public void addCodeSets(Map<String, String> codeMap, Map<String, Method> codeMethodMap) {
		this.codeMap.putAll(codeMap);
		for (String key : codeMap.keySet()) {
			this.codeMethodMap.put(key, codeMethodMap.get(key));
		}
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
	 * 코드의 Method를 리턴한다
	 */
	public Method getCodeMehtod(String code) {
		return codeMethodMap.get(code) == null ? Method.NEW : codeMethodMap.get(code);
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
		codeMethodMap.remove(code);
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
			codeMethodMap.remove(delCode);
		}
	}
}
