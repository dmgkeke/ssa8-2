package com.sds.metac.input.reader.poller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InputReadConfig {

	// xml 관련
	Document document;
	DocumentBuilderFactory documentBuilderFactory;
	DocumentBuilder documentBuilder;
	NodeList fileList;

	// xml 내용
	Map<String, InputFileInfo> inputFileInfoMap;
	InputFileInfo inputFileInfo;

	// 생성자에서 xml 읽기
	public InputReadConfig(String configXmlPath)
			throws ParserConfigurationException, SAXException, IOException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(configXmlPath);

		NodeList nodeList = document.getElementsByTagName("file");
		inputFileInfoMap = new HashMap<String, InputFileInfo>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node headNode = nodeList.item(i);
			if (headNode.getNodeType() == Node.ELEMENT_NODE) {
				Element headLineElement = (Element) headNode;
				inputFileInfo = new InputFileInfo();

				NodeList nameElement = headLineElement
						.getElementsByTagName("extention");
				Element subItem = (Element) nameElement.item(0);
				NodeList subElement = subItem.getChildNodes();
				inputFileInfo.setExtention(subElement.item(0).getNodeValue());

				nameElement = headLineElement.getElementsByTagName("fileName");
				subItem = (Element) nameElement.item(0);
				subElement = subItem.getChildNodes();
				inputFileInfo.setFileName(subElement.item(0).getNodeValue());

				nameElement = headLineElement.getElementsByTagName("filePath");
				subItem = (Element) nameElement.item(0);
				subElement = subItem.getChildNodes();
				inputFileInfo.setFilePath(subElement.item(0).getNodeValue());

				nameElement = headLineElement.getElementsByTagName("spiltter");
				subItem = (Element) nameElement.item(0);
				subElement = subItem.getChildNodes();
				inputFileInfo.setSpiltter(subElement.item(0).getNodeValue());

				nameElement = headLineElement.getElementsByTagName("key");
				subItem = (Element) nameElement.item(0);
				subElement = subItem.getChildNodes();

				inputFileInfoMap.put(subElement.item(0).getNodeValue(),
						inputFileInfo);

			}
		}
	}

	public Map<String, InputFileInfo> getInputFileInfoMap() {
		return inputFileInfoMap;
	}

	public void setInputFileInfoMap(Map<String, InputFileInfo> inputFileInfoMap) {
		this.inputFileInfoMap = inputFileInfoMap;
	}

}
