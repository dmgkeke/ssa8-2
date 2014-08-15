//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.15 at 11:07:46 PM KST 
//


package com.sds.metac.schema.userSetting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="folder">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="implementation" type="{http://www.samsung.com/sds/ssa8-2/user-setting}Folder"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="selection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Input" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
 *                   &lt;element name="Output" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
 *                   &lt;element name="PostProc" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
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
    "folder",
    "selection"
})
@XmlRootElement(name = "setting")
public class Setting {

    @XmlElement(required = true)
    protected Setting.Folder folder;
    @XmlElement(required = true)
    protected Setting.Selection selection;

    /**
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link Setting.Folder }
     *     
     */
    public Setting.Folder getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Setting.Folder }
     *     
     */
    public void setFolder(Setting.Folder value) {
        this.folder = value;
    }

    /**
     * Gets the value of the selection property.
     * 
     * @return
     *     possible object is
     *     {@link Setting.Selection }
     *     
     */
    public Setting.Selection getSelection() {
        return selection;
    }

    /**
     * Sets the value of the selection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Setting.Selection }
     *     
     */
    public void setSelection(Setting.Selection value) {
        this.selection = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="implementation" type="{http://www.samsung.com/sds/ssa8-2/user-setting}Folder"/>
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
        "implementation"
    })
    public static class Folder {

        @XmlElement(required = true)
        protected com.sds.metac.schema.userSetting.Folder implementation;

        /**
         * Gets the value of the implementation property.
         * 
         * @return
         *     possible object is
         *     {@link com.sds.metac.schema.userSetting.Folder }
         *     
         */
        public com.sds.metac.schema.userSetting.Folder getImplementation() {
            return implementation;
        }

        /**
         * Sets the value of the implementation property.
         * 
         * @param value
         *     allowed object is
         *     {@link com.sds.metac.schema.userSetting.Folder }
         *     
         */
        public void setImplementation(com.sds.metac.schema.userSetting.Folder value) {
            this.implementation = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Input" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
     *         &lt;element name="Output" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
     *         &lt;element name="PostProc" type="{http://www.samsung.com/sds/ssa8-2/user-setting}ClassInfo"/>
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
        "input",
        "output",
        "postProc"
    })
    public static class Selection {

        @XmlElement(name = "Input", required = true)
        protected ClassInfo input;
        @XmlElement(name = "Output", required = true)
        protected ClassInfo output;
        @XmlElement(name = "PostProc", required = true)
        protected ClassInfo postProc;

        /**
         * Gets the value of the input property.
         * 
         * @return
         *     possible object is
         *     {@link ClassInfo }
         *     
         */
        public ClassInfo getInput() {
            return input;
        }

        /**
         * Sets the value of the input property.
         * 
         * @param value
         *     allowed object is
         *     {@link ClassInfo }
         *     
         */
        public void setInput(ClassInfo value) {
            this.input = value;
        }

        /**
         * Gets the value of the output property.
         * 
         * @return
         *     possible object is
         *     {@link ClassInfo }
         *     
         */
        public ClassInfo getOutput() {
            return output;
        }

        /**
         * Sets the value of the output property.
         * 
         * @param value
         *     allowed object is
         *     {@link ClassInfo }
         *     
         */
        public void setOutput(ClassInfo value) {
            this.output = value;
        }

        /**
         * Gets the value of the postProc property.
         * 
         * @return
         *     possible object is
         *     {@link ClassInfo }
         *     
         */
        public ClassInfo getPostProc() {
            return postProc;
        }

        /**
         * Sets the value of the postProc property.
         * 
         * @param value
         *     allowed object is
         *     {@link ClassInfo }
         *     
         */
        public void setPostProc(ClassInfo value) {
            this.postProc = value;
        }

    }

}
