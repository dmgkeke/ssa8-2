package com.sds.metac.core;

import org.apache.log4j.Logger;

import com.sds.metac.input.processor.InputProcessor;
import com.sds.metac.input.reader.InputReader;
import com.sds.metac.input.reader.InputReaderFacotry;
import com.sds.metac.input.writer.InputWriter;
import com.sds.metac.input.writer.InputWriterFactory;
import com.sds.metac.output.processor.OutputProcessor;
import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.output.reader.OutputReaderFactory;
import com.sds.metac.output.writer.OutputWriter;
import com.sds.metac.output.writer.OutputWriterFactory;
import com.sds.metac.post.PostProcessor;
import com.sds.metac.post.PostProcessorFactory;

public enum Core {
	INSTANCE;

	Logger logger = Logger.getLogger(Core.class);

	public void doProcess() {
		// Input Reader 생성
		logger.debug("Input Reader 생성");
		InputReaderFacotry inputReaderFactory = InputReaderFacotry.INSTANCE;
		InputReader inputReader = inputReaderFactory.getReader();

		// Input Writer 생성
		logger.debug("Input Writer 생성");
		InputWriterFactory inputWriterFactory = InputWriterFactory.INSTANCE;
		InputWriter inputWriter = inputWriterFactory.getWriter();

		// Input 처리
		logger.debug("Input 처리");
		InputProcessor inputProcessor = new InputProcessor();
		inputProcessor.doRead(inputReader, inputWriter);

		// Ouput Reader 생성
		logger.debug("Output Reader 생성");
		OutputReaderFactory outputReaderFactory = OutputReaderFactory.INSTANCE;
		OutputReader outputReader = outputReaderFactory.getOutputReader();

		// Output Writer 생성
		logger.debug("Output Writer 생성");
		OutputWriterFactory outputWriterFactory = OutputWriterFactory.INSTANCE;
		OutputWriter outputWriter = outputWriterFactory.getOutputWriter();

		// Output 처리
		logger.debug("Output 처리");
		OutputProcessor outputProcessor = new OutputProcessor();
		outputProcessor.doWrite(outputReader, outputWriter);

		// PostProcessor
		logger.debug("후속처리");
		PostProcessorFactory postProcessorFactory = PostProcessorFactory.INSTANCE;
		PostProcessor postProcessor = postProcessorFactory.getPostProcessor();
		if (postProcessor != null) {
			postProcessor.doProcess();
		}
	}

}
