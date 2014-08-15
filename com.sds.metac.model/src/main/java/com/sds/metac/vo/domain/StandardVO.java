package com.sds.metac.vo.domain;

import com.sds.metac.vo.AbstractVO;

public class StandardVO extends AbstractVO {

	private String name;
	private String value;
	
	public StandardVO() {
		
	}
	
	public StandardVO(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
