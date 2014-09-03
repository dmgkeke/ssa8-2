//
// 이 파일은 JAXB(JavaTM Architecture for XML Binding) 참조 구현 2.2.8-b130911.1802 버전을 통해 생성되었습니다. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
// 생성 날짜: 2014.09.04 시간 12:13:11 AM KST 
//


package com.sds.metac.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="inputInformation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="forder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fileNameWord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fileNameCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="inputType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="splitter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "inputInformation"
})
@XmlRootElement(name = "inputConfig")
public class InputConfig {

    protected List<InputConfig.InputInformation> inputInformation;

    /**
     * Gets the value of the inputInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputConfig.InputInformation }
     * 
     * 
     */
    public List<InputConfig.InputInformation> getInputInformation() {
        if (inputInformation == null) {
            inputInformation = new ArrayList<InputConfig.InputInformation>();
        }
        return this.inputInformation;
    }


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
     *         &lt;element name="forder" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fileNameWord" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fileNameCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="inputType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="splitter" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "forder",
        "fileNameWord",
        "fileNameCode",
        "inputType",
        "splitter",
        "encoding"
    })
    public static class InputInformation {

        @XmlElement(required = true)
        protected String forder;
        @XmlElement(required = true)
        protected String fileNameWord;
        @XmlElement(required = true)
        protected String fileNameCode;
        @XmlElement(required = true)
        protected String inputType;
        @XmlElement(required = true)
        protected String splitter;
        @XmlElement(required = true)
        protected String encoding;

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
         * inputType 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInputType() {
            return inputType;
        }

        /**
         * inputType 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInputType(String value) {
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
         * encoding 속성의 값을 가져옵니다.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEncoding() {
            return encoding;
        }

        /**
         * encoding 속성의 값을 설정합니다.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEncoding(String value) {
            this.encoding = value;
        }

    }

}
