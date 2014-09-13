import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class MetacCommonUtilTest {
	static Map<String, String> keyMap = new HashMap<String, String>();
	static String targetStr = "계약상태코드";
	static {
		keyMap.put("계", "A");
		keyMap.put("약", "B");
		keyMap.put("상", "C");
		keyMap.put("태", "D");
		keyMap.put("코", "E");
		keyMap.put("드", "F");
		keyMap.put("계약", "CONT");
		keyMap.put("상태", "STAT");
		keyMap.put("코드", "CD");
		keyMap.put("계약상태", "CONTS");
	}
	
	@Test
	public void 계약상태코드가_잘_쪼개어지는가() {
		System.out.println(MetacCommonUtil.getStandardStrList(targetStr, 1, "topDown"));
		System.out.println("----------------------------------------------------");
		System.out.println(MetacCommonUtil.getStandardStrList(targetStr, 1, "bottomUp"));
	}
	
	@Test
	public void 쪼개어진_코드의_경우의수가_정확한가_TOPDOWN_MINLENGTH_1() {
		List<List<String>> topDownList = MetacCommonUtil.getStandardStrList(targetStr, 1, "topDown");
		assertEquals(topDownList.size(), 32);
	}
	
	@Test
	public void 쪼개어진_코드의_경우의수가_정확한가_BOTTOMUP_MINLENGTH_1() {
		List<List<String>> topDownList = MetacCommonUtil.getStandardStrList(targetStr, 1, "bottomUp");
		assertEquals(topDownList.size(), 32);
	}
	
	@Test
	public void 쪼개어진_코드의_경우의수가_정확한가_TOPDOWN_MINLENGTH_2() {
		List<List<String>> topDownList = MetacCommonUtil.getStandardStrList(targetStr, 2, "topDown");
		assertEquals(topDownList.size(), 5);
	}
	
	@Test
	public void 쪼개어진_코드의_경우의수가_정확한가_BOTTOMUP_MINLENGTH_2() {
		List<List<String>> topDownList = MetacCommonUtil.getStandardStrList(targetStr, 2, "bottomUp");
		assertEquals(topDownList.size(), 5);
	}
}
