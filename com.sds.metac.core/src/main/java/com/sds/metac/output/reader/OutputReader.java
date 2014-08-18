package com.sds.metac.output.reader;

import java.util.Map;

import com.sds.metac.vo.domain.GroupVO;

public interface OutputReader {

	public boolean hasNextGroup();
	public GroupVO readGroup();
}
