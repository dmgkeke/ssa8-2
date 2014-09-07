package com.sds.metac.ui.message;


public class Message {
	private static MessageLoader messageLoader = null;
	
	public static void init() {
		if (messageLoader == null) {
			messageLoader = MessageLoader.INSTANCE;
			messageLoader.loadMessage();
		}
	}
	
	public static String get(String key) {
		init();		
		return messageLoader.getMessage(key);
	}
}
