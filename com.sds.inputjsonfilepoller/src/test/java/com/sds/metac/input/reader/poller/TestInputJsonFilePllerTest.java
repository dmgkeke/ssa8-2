package com.sds.metac.input.reader.poller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.samsung.sds.ssa8_2.inputmeta.InpuMeata;

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
	
	@Test
	public void TestJaxb() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.samsung.sds.ssa8_2.inputmeta");
		Unmarshaller u = jc.createUnmarshaller();
		
		InpuMeata im = (InpuMeata)u.unmarshal(new File("./src/main/resources/Inputmeta.xml"));
		System.out.println(im.getMetaType());
		System.out.println(im.getConfiguration().getInputFilesPath());
		System.out.println(im.getConfiguration().getStandardFileName());
		System.out.println(im.getConfiguration().getCommonCodeFileName());
	}
}
