package com.sds.metac.post;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.samsung.sds.ssa8_2.postprocessorconfiguration.PostProcessorConfiguration;

public class TestPostJavaProcessor {
	@Test
	public void test() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.samsung.sds.ssa8_2.postprocessorconfiguration");
		Unmarshaller u = jc.createUnmarshaller();
		
		PostProcessorConfiguration ppc = (PostProcessorConfiguration)u.unmarshal(new File("./src/main/resources/PostProcessorConfiguration.xml"));
		System.out.println(ppc.getProgramming());
		System.out.println(ppc.getCompileConfiguration().getInputFilesPath());
		System.out.println(ppc.getCreateJarConfiguration().getFileName());
	}
}
