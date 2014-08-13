package com.sds.metac.output.reader;

import com.sds.metac.output.reader.impl.OutputFileReader;

public enum OutputReaderFactory {
	INSTANCE;

	OutputReader reader = new OutputFileReader();
	
	public OutputReader getOutputReader() {
		return reader;
	}

}
