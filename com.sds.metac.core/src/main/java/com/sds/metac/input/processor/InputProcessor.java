package com.sds.metac.input.processor;

import com.sds.metac.input.reader.InputReader;
import com.sds.metac.input.reader.poller.InputPoller;
import com.sds.metac.input.writer.InputWriter;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputProcessor {

	public void doRead(InputReader inputReader, InputWriter inputWriter) {
		
		// Poller 일 경우
		if (inputReader instanceof InputPoller) {
			this.doPolling((InputPoller)inputReader, inputWriter);
		}
	}

	private void doPolling(InputPoller inputPoller, InputWriter inputWriter) {
		
		while (inputPoller.hasNextStandard()) {
			StandardVO standardVO = inputPoller.readStandard();
			inputWriter.write(standardVO);
		}
		
		while (inputPoller.hasNextGroup()) {
			GroupVO groupVO = inputPoller.readGroup();
			inputWriter.write(groupVO);
		}
		
		inputWriter.postProcess();
	}
}
