package com.sds.metac.input.reader.poller;

import com.sds.metac.input.reader.poller.InputPoller;
import com.sds.metac.vo.domain.GroupVO;
import com.sds.metac.vo.domain.StandardVO;

public class InputJsonFilePoller implements InputPoller {

	// 테스트를 위한 변수
	int countStandard = 0;
	int countGroup = 0;

	public boolean hasNextGroup() {
		// TODO Auto-generated method stub
		return countGroup++ < 2;
	}

	public boolean hasNextStandard() {
		// TODO Auto-generated method stub
		return countStandard++ < 2;
	}

	public GroupVO readGroup() {
		// TODO Auto-generated method stub
		GroupVO groupVO = new GroupVO();
		groupVO.setName("" + countGroup);
		return groupVO;
	}

	public StandardVO readStandard() {
		// TODO Auto-generated method stub
		StandardVO standardVO = new StandardVO();
		standardVO.setName("" + countStandard);
		return standardVO;
	}

}
