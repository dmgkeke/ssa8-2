package com.sds.metac.input.reader.poller;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.samsung.sds.ssa8_2.inputmeta.InputMeta;
import com.sds.metac.file.FileManager;

public class TestInputJsonFilePollerTest {

	@Test
	public void testTestInputJsonFilePoller() throws Exception {
		TestInputJsonFilePoller inputJsonFilePoller = new TestInputJsonFilePoller();
		while(inputJsonFilePoller.hasNextGroup()){
			System.out.println(inputJsonFilePoller.readGroup());
		}
		while(inputJsonFilePoller.hasNextStandard()){
			System.out.println(inputJsonFilePoller.readStandard());
		}
	}
	
	@Test
	public void testInputJsonFilePoller() throws Exception {
		InputJsonFilePoller inputJsonFilePoller = new InputJsonFilePoller();
		while(inputJsonFilePoller.hasNextGroup()){
			System.out.println(inputJsonFilePoller.readGroup());
		}
		while(inputJsonFilePoller.hasNextStandard()){
			System.out.println(inputJsonFilePoller.readStandard());
		}
	}
	
	@Test
	public void testJaxb() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("com.samsung.sds.ssa8_2.inputmeta");
		Unmarshaller u = jc.createUnmarshaller();
		
		InputMeta im = (InputMeta)u.unmarshal(new File("./config/Inputmeta.xml"));
		System.out.println(im.getMetaType());
		System.out.println(im.getConfiguration().getInputFilesPath());
		System.out.println(im.getConfiguration().getStandardFileName());
		System.out.println(im.getConfiguration().getCommonCodeFileName());
		
		System.out.println(Paths.get("").toAbsolutePath().toString());
		
		FileManager fileManger = FileManager.INSTANCE;
		InputMeta im1 = fileManger.readConfigXmlFile("InputMeta.xml", InputMeta.class);
		
		System.out.println(im1.getMetaType());
	}
	
//	@Test
	/*public void test() throws ParserConfigurationException, SAXException,
			IOException {
		String configXmlPath = "./config/inputReadConfig.xml";
		InputReadConfig inputReadConfig = new InputReadConfig(configXmlPath);
		Map<String, InputFileInfo> temp = inputReadConfig.getInputFileInfoMap();
		System.out.println(temp.keySet());
		System.out.println(temp.get("code").getExtention());

		TestInputJsonFilePoller inputJsonFilePoller = new TestInputJsonFilePoller();

		File file = inputJsonFilePoller.getFileInfo(inputReadConfig
				.getInputFileInfoMap().get("code"));
		System.out.println(file);
		boolean result = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			counter++;
//			System.out.println(line);
//			System.out.println(line);
//			System.out.println(line);
//			System.out.println(line);
			
			
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JsonReader tmp = new JsonReader(new FileReader(file));
//			System.out.println(tmp);
			JsonObject test = gson.fromJson(tmp, JsonObject.class);
//			System.out.println(test);
			
			System.out.println(test.get("DESCRIPTION"));
//			System.out.println(test.get("DATA"));
			
			JsonArray array = test.get("DATA").getAsJsonArray();
			System.out.println(array.size());
			System.out.println(array.get(0));
			
			
			CustomVO[] codeVOArr = gson.fromJson(array, CustomVO[].class);
			System.out.println("array: "+codeVOArr[0]);
			
			List list = new ArrayList<CustomVO>(Arrays.asList(codeVOArr));
			System.out.println("list: "+list.get(0));
//			JsonObject temp = gson.fromJson(new FileReader(file), CodeVO.class);
			
//			while (line != null) {
//				line = br.readLine();
//				System.out.println(line);
//				if (StringUtil.isEmpty(line) || line == null) {
//					// 종료, static counter 초기화
//					result = false;
//					counter = 0;
//				} else {
//					logger.debug(line);
//					result = true;
//
//					name = line.split("\\" + splitter)[0];
//					value = line.split("\\" + splitter)[1];
//					// 초기값
//					if (tmpKey == null) {
//						tmpKey = name;
//						groupVO = new GroupVO();
//					}
//					if (!tmpKey.equals(name)) {
//						result = true;
//						groupVO = new GroupVO();
//					} else {
//						result = false;
//					}
//					groupVO.addCodeSet(name, value);
//					tmpKey = name;
//				}
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MetaCException("!!! FileNotFoundException - doHasNext()");
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetaCException("!!! IOException - doHasNext()");
		}

	}*/
}
