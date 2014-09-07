import org.junit.Test;

import com.sds.metac.core.Core;
import com.sds.metac.ui.swing.SwingUILauncher;


public class Tester {
	
	@Test
	public void coreTest() {
		Core c = Core.INSTANCE;
		
		c.doProcess();
	}
	
}
