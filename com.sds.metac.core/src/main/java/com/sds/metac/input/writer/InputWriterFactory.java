package com.sds.metac.input.writer;

import com.sds.metac.input.writer.impl.InputFileWriter;


public enum InputWriterFactory {
	INSTANCE;

	InputWriter writer = new InputFileWriter();
	
	public InputWriter getWriter() {
		return writer;
	}

}
