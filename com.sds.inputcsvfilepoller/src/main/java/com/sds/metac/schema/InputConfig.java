//
// 이 파일은 JAXB(JavaTM Architecture for XML Binding) 참조 구현 2.2.8-b130911.1802 버전을 통해 생성되었습니다. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
// 생성 날짜: 2014.09.14 시간 10:30:50 AM KST 
//


package com.sds.metac.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
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
     * encoding 속성의 값을 가져옵니다.
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
     * encoding 속성의 값을 설정합니다.
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
     * fileNameCode 속성의 값을 가져옵니다.
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
     * fileNameCode 속성의 값을 설정합니다.
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
     * fileNameWord 속성의 값을 가져옵니다.
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
     * fileNameWord 속성의 값을 설정합니다.
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
     * forder 속성의 값을 가져옵니다.
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
     * forder 속성의 값을 설정합니다.
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
     * inputType 속성의 값을 가져옵니다.
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
     * inputType 속성의 값을 설정합니다.
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
     * splitter 속성의 값을 가져옵니다.
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
     * splitter 속성의 값을 설정합니다.
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
     * tempFilePath 속성의 값을 가져옵니다.
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
     * tempFilePath 속성의 값을 설정합니다.
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
