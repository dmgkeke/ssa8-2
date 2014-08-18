package com.sds.metac.output.writer;

import java.util.Map;

import com.sds.metac.output.reader.StandardReader;
import com.sds.metac.vo.domain.GroupVO;

public interface OutputWriter {
	
	public void write(GroupVO groupVO, StandardReader standardReader);
	
	@Deprecated
	public void write(GroupVO groupVO, Map<String, String> keySet);
}
