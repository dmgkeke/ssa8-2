package com.sds.metac.vo.core;

import com.sds.metac.vo.AbstractVO;

public class ClassInfoVO extends AbstractVO {
	private String name;
	private String className;
	private String uiClassName;
	private String classFilePath;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassFilePath() {
		return classFilePath;
	}
	public void setClassFilePath(String classFilePath) {
		this.classFilePath = classFilePath;
	}
	public String getUiClassName() {
		return uiClassName;
	}
	public void setUiClassName(String uiClassName) {
		this.uiClassName = uiClassName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null
			&& obj instanceof ClassInfoVO
			&& this.name != null
			) {
			return this.name.equals(((ClassInfoVO)obj).getName());
		}
		return false;
	}
}
