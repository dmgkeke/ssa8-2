import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sds.metac.config.OutputConfigManager;
import com.sds.metac.schema.MetaFomula;
import com.sds.metac.schema.OutputWriterConfig;
import com.sds.metac.schema.VariableSyntax;

public class ConfigTest {
	static OutputConfigManager configManager = OutputConfigManager.INSTANCE;
	static OutputWriterConfig config = configManager.getOutputWriterConfig();
	
	@Test
	public void metaFomula는_topDown이다() {
		assertEquals(config.getMetaFomula(), MetaFomula.TOP_DOWN);
	}
	
	@Test
	public void variableSyntax는_camelCase이다() {
		assertEquals(config.getVariableSyntax(), VariableSyntax.CAMEL_CASE);
	}
	
	@Test
	public void minCharSize는_2이다() {
		assertEquals(config.getMinCharSize(), 2);
	}
	
	@Test
	public void tempFilePath는_outputFiles이다() {
		assertEquals(config.getTempFilePath(), "outputFiles");
	}
}
