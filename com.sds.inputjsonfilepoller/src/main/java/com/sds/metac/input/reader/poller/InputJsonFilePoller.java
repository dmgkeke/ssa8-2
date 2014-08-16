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
		return countStandard++ < 4;
	}

	public GroupVO readGroup() {
		// TODO Auto-generated method stub
		GroupVO groupVO = new GroupVO();
		groupVO.setName("Group" + countGroup);
		groupVO.addCodeSet("stat1", countGroup + "" + countGroup);
		groupVO.addCodeSet("stat2", (countGroup+1) + "" + (countGroup+1));
		return groupVO;
	}

	public StandardVO readStandard() {
		// TODO Auto-generated method stub
		StandardVO standardVO = new StandardVO();
		if (countStandard == 1) {
			standardVO.setName("Group1");
			standardVO.setValue("1번그룹");
		}
		if (countStandard == 2) {
			standardVO.setName("Group2");
			standardVO.setValue("2번그룹");
		}
		if (countStandard == 3) {
			standardVO.setName("stat1");
			standardVO.setValue("1번값입니다");
		}
		if (countStandard == 4) {
			standardVO.setName("stat2");
			standardVO.setValue("2번값입니다");
		}
		return standardVO;
	}

}
