//
// �� ������ JAXB(JavaTM Architecture for XML Binding) ���� ���� 2.2.8-b130911.1802 ������ ���� �����Ǿ����ϴ�. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>�� �����Ͻʽÿ�. 
// �� ������ �����ϸ� �ҽ� ��Ű���� ���������� �� ���� ������ �սǵ˴ϴ�. 
// ���� ��¥: 2014.09.14 �ð� 10:30:50 AM KST 
//


package com.sds.metac.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� ���� Java Ŭ�����Դϴ�.
 * 
 * <p>���� ��Ű�� ������ �� Ŭ������ ���ԵǴ� �ʿ��� �������� �����մϴ�.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="encoding" type="{http://www.example.org/information}Encoding"/>
 *         &lt;element name="fileNameCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileNameWord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="forder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inputType" type="{http://www.example.org/information}InputType"/>
 *         &lt;element name="splitter" type="{http://www.example.org/information}Splitter"/>
 *         &lt;element name="tempFilePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "encoding",
    "fileNameCode",
    "fileNameWord",
    "forder",
    "inputType",
    "splitter",
    "tempFilePath"
})
@XmlRootElement(name = "inputConfig")
public class InputConfig {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Encoding encoding;
    @XmlElement(required = true)
    protected String fileNameCode;
    @XmlElement(required = true)
    protected String fileNameWord;
    @XmlElement(required = true)
    protected String forder;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected InputType inputType;
    @XmlElement(required = true)
    protected String splitter;
    @XmlElement(required = true)
    protected String tempFilePath;

    /**
     * encoding �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link Encoding }
     *     
     */
    public Encoding getEncoding() {
        return encoding;
    }

    /**
     * encoding �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link Encoding }
     *     
     */
    public void setEncoding(Encoding value) {
        this.encoding = value;
    }

    /**
     * fileNameCode �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNameCode() {
        return fileNameCode;
    }

    /**
     * fileNameCode �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNameCode(String value) {
        this.fileNameCode = value;
    }

    /**
     * fileNameWord �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNameWord() {
        return fileNameWord;
    }

    /**
     * fileNameWord �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNameWord(String value) {
        this.fileNameWord = value;
    }

    /**
     * forder �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForder() {
        return forder;
    }

    /**
     * forder �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForder(String value) {
        this.forder = value;
    }

    /**
     * inputType �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link InputType }
     *     
     */
    public InputType getInputType() {
        return inputType;
    }

    /**
     * inputType �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link InputType }
     *     
     */
    public void setInputType(InputType value) {
        this.inputType = value;
    }

    /**
     * splitter �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSplitter() {
        return splitter;
    }

    /**
     * splitter �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSplitter(String value) {
        this.splitter = value;
    }

    /**
     * tempFilePath �Ӽ��� ���� �����ɴϴ�.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempFilePath() {
        return tempFilePath;
    }

    /**
     * tempFilePath �Ӽ��� ���� �����մϴ�.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempFilePath(String value) {
        this.tempFilePath = value;
    }

}
