//
// �� ������ JAXB(JavaTM Architecture for XML Binding) ���� ���� 2.2.8-b130911.1802 ������ ���� �����Ǿ����ϴ�. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>�� �����Ͻʽÿ�. 
// �� ������ �����ϸ� �ҽ� ��Ű���� ���������� �� ���� ������ �սǵ˴ϴ�. 
// ���� ��¥: 2014.09.14 �ð� 10:30:50 AM KST 
//


package com.sds.metac.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Encoding�� ���� Java Ŭ�����Դϴ�.
 * 
 * <p>���� ��Ű�� ������ �� Ŭ������ ���ԵǴ� �ʿ��� �������� �����մϴ�.
 * <p>
 * <pre>
 * &lt;simpleType name="Encoding">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UTF-8"/>
 *     &lt;enumeration value="EUC-KR"/>
 *     &lt;enumeration value="ISO-8859-1"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Encoding")
@XmlEnum
public enum Encoding {

    @XmlEnumValue("UTF-8")
    UTF_8("UTF-8"),
    @XmlEnumValue("EUC-KR")
    EUC_KR("EUC-KR"),
    @XmlEnumValue("ISO-8859-1")
    ISO_8859_1("ISO-8859-1");
    private final String value;

    Encoding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Encoding fromValue(String v) {
        for (Encoding c: Encoding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
