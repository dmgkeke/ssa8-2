package com.sds.metac.output.processor;

import com.sds.metac.output.reader.OutputReader;
import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.output.reader.impl.StandardFileReader;
import com.sds.metac.output.writer.OutputWriter;
import com.sds.metac.vo.domain.GroupVO;

public class OutputProcessor {

	public void doWrite(OutputReader outputReader, OutputWriter outputWriter) {
	
		StandardReader standardReader = new StandardFileReader();
		
		while (outputReader.hasNextGroup()) {
			GroupVO groupVO = outputReader.readGroup();
			outputWriter.write(groupVO, standardReader);
		}
	}

}
