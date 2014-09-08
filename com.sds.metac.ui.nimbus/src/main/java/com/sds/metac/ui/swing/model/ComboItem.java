package com.sds.metac.ui.swing.model;


public class ComboItem {

	private String text;
	private String value;

	public ComboItem(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null
			&& this.value != null
			&& obj instanceof ComboItem) {
			return this.value.equals(((ComboItem)obj).getValue());
		}
		return false;
	}
}