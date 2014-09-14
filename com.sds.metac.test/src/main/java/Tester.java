import org.junit.Test;

import com.sds.metac.core.Core;
import com.sds.metac.util.IndexedFileUtil;


public class Tester {
	
	@Test
	public void coreTest() {
		Core c = Core.INSTANCE;
		
		c.doProcess();
	}
	
	public static void main(String[] args) {
		System.out.println("계약상태코드".hashCode());
	}
	
}
