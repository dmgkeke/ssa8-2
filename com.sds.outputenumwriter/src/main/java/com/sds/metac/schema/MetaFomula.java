//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 01:10:23 AM KST 
//


package com.sds.metac.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MetaFomula.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MetaFomula">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="bottomUp"/>
 *     &lt;enumeration value="topDown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MetaFomula")
@XmlEnum
public enum MetaFomula {

    @XmlEnumValue("bottomUp")
    BOTTOM_UP("bottomUp"),
    @XmlEnumValue("topDown")
    TOP_DOWN("topDown");
    private final String value;

    MetaFomula(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MetaFomula fromValue(String v) {
        for (MetaFomula c: MetaFomula.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
