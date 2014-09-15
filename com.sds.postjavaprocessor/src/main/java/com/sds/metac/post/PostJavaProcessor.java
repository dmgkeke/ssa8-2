package com.sds.metac.post;

import org.apache.log4j.Logger;

public class PostJavaProcessor implements PostProcessor {

	Logger logger = Logger.getLogger(PostJavaProcessor.class);

	public void doProcess() {
		logger.debug("컴파일을 수행한다");
		CompileJavaFile compiler = new CompileJavaFile();
		compiler.compile();
		logger.debug("패키징을 수행한다");
		CreateJarFile createJar = new CreateJarFile();
		createJar.createJarArchive();
	}

}
