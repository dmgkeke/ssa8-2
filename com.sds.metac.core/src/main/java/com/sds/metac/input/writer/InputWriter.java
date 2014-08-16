package com.sds.metac.input.writer;

import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public interface InputWriter {

	void write(StandardVO standardVO);
	void write(GroupVO groupVO);
	
	void postProcess();
}
