package com.sds.metac.post;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.samsung.sds.ssa8_2.postprocessorconfiguration.PostProcessorConfiguration;
import com.sds.metac.file.FileManager;

public class TestPostJavaProcessor {
	
	@Test 
	public void testJavaComplie() throws Exception {
		CompileJavaFile compiler = new CompileJavaFile();
		compiler.compile();
	}
	
	@Test 
	public void testCreateJar() throws Exception {
		CreateJarFile jar = new CreateJarFile();
		jar.createJarArchive();
	}
	
	@Test
	public void testJaxb() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.samsung.sds.ssa8_2.postprocessorconfiguration");
		Unmarshaller u = jc.createUnmarshaller();
		
		PostProcessorConfiguration ppc = (PostProcessorConfiguration)u.unmarshal(new File("./config/PostProcessorConfiguration.xml"));
		System.out.println(ppc.getProgramming());
		System.out.println(ppc.getCompileConfiguration().getInputFilesPath());
		System.out.println(ppc.getCreateJarConfiguration().getFileName());
		
		FileManager fileManger = FileManager.INSTANCE;
		PostProcessorConfiguration im1 = fileManger.readConfigXmlFile("PostProcessorConfiguration.xml", PostProcessorConfiguration.class);
		
		System.out.println(im1.getProgramming());
	}
}