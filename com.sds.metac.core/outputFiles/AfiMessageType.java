

/**
 * AFI메세지타입 공통코드
 * 
 */
public enum AfiMessageType {


    /**
     * 고정
     * 
     */
    Fixed("01"),

    /**
     * ISO
     * 
     */
    Iso("07"),

    /**
     * 맵
     * 
     */
    Map("09"),

    /**
     * XML
     * 
     */
    Xml("03"),

    /**
     * 키값
     * 
     */
    KeyValue("06"),

    /**
     * 엑셀
     * 
     */
    Excel("08"),

    /**
     * VO
     * 
     */
    Vo("04"),

    /**
     * JSON
     * 
     */
    Json("02"),

    /**
     * 정의안함
     * 
     */
    DefineNot("00"),

    /**
     * SOAP
     * 
     */
    Soap("05");
    /**
     * 코드에 매핑된 실제 값
     * 
     */
    private String codeValue;

    private AfiMessageType(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

}
