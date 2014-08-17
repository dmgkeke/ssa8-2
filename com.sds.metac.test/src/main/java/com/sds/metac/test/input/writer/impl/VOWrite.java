package com.sds.metac.test.input.writer.impl;

import java.io.IOException;

import com.sds.metac.vo.domain.GroupVO;

public class VOWrite {
	public static void main(String[] args) throws IOException {
		GroupVO groupVO = new GroupVO();
		
		groupVO.setName("계약상태코드");
		groupVO.addCodeSet("정상", "10");
		groupVO.addCodeSet("비정상", "20");
		
		String jsonString = groupVO.toJson();
		
		System.out.println(jsonString);
		
		GroupVO dup = GroupVO.fromJson(jsonString, GroupVO.class);
		
		System.out.println(dup);
	}
}
