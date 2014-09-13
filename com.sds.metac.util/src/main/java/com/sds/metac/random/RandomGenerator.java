package com.sds.metac.random;

import java.util.Random;

public class RandomGenerator {
	
	private static final String alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890";
	private static final int alphaNumLength = alphaNum.length();
	private static Random random = new Random(System.currentTimeMillis()); 
	
	
	public static String getAlphaNumString(int length) {
		StringBuffer ret = new StringBuffer();
		
		for (int i=0 ; i<length ; i++) {
			ret.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
		}
		
		return ret.toString();
	}
}
