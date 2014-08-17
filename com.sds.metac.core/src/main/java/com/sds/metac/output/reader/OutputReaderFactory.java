package com.sds.metac.output.reader;

import com.sds.metac.output.reader.impl.OutputIndexedFileReader;

public enum OutputReaderFactory {
	INSTANCE;

	OutputReader reader = new OutputIndexedFileReader();
	
	public OutputReader getOutputReader() {
		return reader;
	}

}
