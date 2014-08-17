package com.sds.metac.input.writer;

import com.sds.metac.input.writer.impl.InputIndexedFileWriter;


public enum InputWriterFactory {
	INSTANCE;

	InputWriter writer = new InputIndexedFileWriter();
	
	public InputWriter getWriter() {
		return writer;
	}

}
