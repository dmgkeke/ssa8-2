package com.sds.metac.output.reader;

import com.sds.metac.vo.domain.GroupVO;

public interface OutputReader {

	public boolean hasNextGroup();
	public GroupVO readGroup();
	public void validateGroup(GroupVO groupVO);

}
