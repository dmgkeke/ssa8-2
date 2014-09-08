package com.sds.metac.ui.swing.model;

public class ListItem<T> {
	public static final String TAG_INPUT = "[I]";
	public static final String TAG_OUTPUT = "[O]";
	public static final String TAG_POST = "[P]";
	
	String text;
	String tag;
	T value;
	
	public ListItem(String text, String tag, T value) {
		this.text = text;
		this.tag = tag;
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public String getTag() {
		return tag;
	}
	
	@Override
	public String toString() {		
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null
			&& this.value != null
			&& obj instanceof ListItem
			) {
			return this.value.equals(((ListItem<?>)obj).getValue());
		}
		
		return false;
	}
}
