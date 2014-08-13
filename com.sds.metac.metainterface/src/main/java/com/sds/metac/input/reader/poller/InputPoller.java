package com.sds.metac.input.reader.poller;

import com.sds.metac.input.reader.InputReader;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public interface InputPoller extends InputReader {
	public boolean hasNextStandard();
	public StandardVO readStandard();
	
	public boolean hasNextGroup();
	public GroupVO readGroup();
}
