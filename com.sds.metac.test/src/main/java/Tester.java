import org.junit.Test;

import com.sds.metac.core.Core;


public class Tester {
	
	@Test
	public void coreTest() {
		Core c = Core.INSTANCE;
		
		c.doProcess();
	}
	
}
