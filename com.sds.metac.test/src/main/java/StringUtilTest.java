import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sds.metac.util.StringUtil;

public class StringUtilTest {
	static String camelStr = "StringUtilTest";
	static String underStr = "STRING_UTIL_TEST";
	
	static String firstCharUpper = "First";
	static String firstCharLower = "first";
	
	@Test
	public void camelcase문자열을_underscore로_변환한다() {
		assertEquals(StringUtil.convertCamelToUnderscore(camelStr), underStr);
	}
	
	@Test
	public void underscore문자열을_camelcase문자열로_변환한다() {
		assertEquals(StringUtil.convertUnderscoreToCamel(underStr), camelStr);
	}
	
	@Test
	public void 첫번째_문자를_대문자로_변환한다() {
		assertEquals(StringUtil.replaceUpperCaseToFirstChar(firstCharLower), firstCharUpper);
	}
	
	@Test
	public void 첫번째_문자를_소문자로_변환한다() {
		assertEquals(StringUtil.replaceLowerCaseToFirstChar(firstCharUpper), firstCharLower);
	}
}
