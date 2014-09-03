//
// 이 파일은 JAXB(JavaTM Architecture for XML Binding) 참조 구현 2.2.8-b130911.1802 버전을 통해 생성되었습니다. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
// 생성 날짜: 2014.09.04 시간 12:13:11 AM KST 
//


package com.sds.metac.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sds.metac.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sds.metac.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InputConfig }
     * 
     */
    public InputConfig createInputConfig() {
        return new InputConfig();
    }

    /**
     * Create an instance of {@link InputConfig.InputInformation }
     * 
     */
    public InputConfig.InputInformation createInputConfigInputInformation() {
        return new InputConfig.InputInformation();
    }

    /**
     * Create an instance of {@link FileNameWord }
     * 
     */
    public FileNameWord createFileNameWord() {
        return new FileNameWord();
    }

    /**
     * Create an instance of {@link Forder }
     * 
     */
    public Forder createForder() {
        return new Forder();
    }

    /**
     * Create an instance of {@link InputType }
     * 
     */
    public InputType createInputType() {
        return new InputType();
    }

    /**
     * Create an instance of {@link FileNameCode }
     * 
     */
    public FileNameCode createFileNameCode() {
        return new FileNameCode();
    }

    /**
     * Create an instance of {@link Encoding }
     * 
     */
    public Encoding createEncoding() {
        return new Encoding();
    }

    /**
     * Create an instance of {@link Splitter }
     * 
     */
    public Splitter createSplitter() {
        return new Splitter();
    }

}
