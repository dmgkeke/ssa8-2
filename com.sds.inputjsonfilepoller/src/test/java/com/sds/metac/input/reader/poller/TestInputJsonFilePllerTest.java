package com.sds.metac.input.reader.poller;

import org.junit.Test;

public class TestInputJsonFilePllerTest {

	@Test
	public void test() throws Exception {
		TestInputJsonFilePoller inputJsonFilePoller = new TestInputJsonFilePoller();
		while(inputJsonFilePoller.hasNextGroup()){
			System.out.println(inputJsonFilePoller.readGroup());
		}
		while(inputJsonFilePoller.hasNextStandard()){
			System.out.println(inputJsonFilePoller.readStandard());
		}
	}
}
