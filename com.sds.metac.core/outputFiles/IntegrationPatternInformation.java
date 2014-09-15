

/**
 * 연계패턴정보 공통코드
 * 
 */
public enum IntegrationPatternInformation {


    /**
     * 전문TO전문
     * 
     */
    IntgmessageToIntgmessage("1"),

    /**
     * DBTODB
     * 
     */
    DbToDb("3"),

    /**
     * DBTO전문
     * 
     */
    DbToIntgmessage("4"),

    /**
     * 단말TOMCI
     * 
     */
    TerminalToMci("2"),

    /**
     * 전문TODB
     * 
     */
    IntgmessageToDb("5");
    /**
     * 코드에 매핑된 실제 값
     * 
     */
    private String codeValue;

    private IntegrationPatternInformation(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

}
