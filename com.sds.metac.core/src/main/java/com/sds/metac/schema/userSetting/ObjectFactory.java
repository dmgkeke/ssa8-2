//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.16 at 08:05:45 PM KST 
//


package com.sds.metac.schema.userSetting;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sds.metac.schema.userSetting package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sds.metac.schema.userSetting
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Setting }
     * 
     */
    public Setting createSetting() {
        return new Setting();
    }

    /**
     * Create an instance of {@link Setting.Folder }
     * 
     */
    public Setting.Folder createSettingFolder() {
        return new Setting.Folder();
    }

    /**
     * Create an instance of {@link Setting.Selection }
     * 
     */
    public Setting.Selection createSettingSelection() {
        return new Setting.Selection();
    }

    /**
     * Create an instance of {@link Setting.Extension }
     * 
     */
    public Setting.Extension createSettingExtension() {
        return new Setting.Extension();
    }

    /**
     * Create an instance of {@link com.sds.metac.schema.userSetting.Folder }
     * 
     */
    public com.sds.metac.schema.userSetting.Folder createFolder() {
        return new com.sds.metac.schema.userSetting.Folder();
    }

    /**
     * Create an instance of {@link ExtType }
     * 
     */
    public ExtType createExtType() {
        return new ExtType();
    }

    /**
     * Create an instance of {@link ClassInfo }
     * 
     */
    public ClassInfo createClassInfo() {
        return new ClassInfo();
    }

}
