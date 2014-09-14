package com.sds.metac.input.reader.vo;

public class CustomVO {
	String CATEGORY_CD;
	String CATEGORY_NM_KR;
	String CATEGORY_NM_EN;
	String SECTION_CD;
	String SECTION_NM_KR;
	String SECTION_NM_EN;
	String CONTENTS_CD;
	String CONTENTS_NM_KR;
	String CONTENTS_NM_EN;
	
	String OBJ_KNM;
	String OBJ_ENM;
	
	String DB_NM;
	String INF_ID;
	String PAR_INFO_TYPE_NM;
	String INFO_TYPE_NM;
	
	public String getCATEGORY_CD() {
		return CATEGORY_CD;
	}
	public void setCATEGORY_CD(String cATEGORY_CD) {
		CATEGORY_CD = cATEGORY_CD;
	}
	public String getCATEGORY_NM_KR() {
		return CATEGORY_NM_KR;
	}
	public void setCATEGORY_NM_KR(String cATEGORY_NM_KR) {
		CATEGORY_NM_KR = cATEGORY_NM_KR;
	}
	public String getCATEGORY_NM_EN() {
		return CATEGORY_NM_EN;
	}
	public void setCATEGORY_NM_EN(String cATEGORY_NM_EN) {
		CATEGORY_NM_EN = cATEGORY_NM_EN;
	}
	public String getSECTION_CD() {
		return SECTION_CD;
	}
	public void setSECTION_CD(String sECTION_CD) {
		SECTION_CD = sECTION_CD;
	}
	public String getSECTION_NM_KR() {
		return SECTION_NM_KR;
	}
	public void setSECTION_NM_KR(String sECTION_NM_KR) {
		SECTION_NM_KR = sECTION_NM_KR;
	}
	public String getSECTION_NM_EN() {
		return SECTION_NM_EN;
	}
	public void setSECTION_NM_EN(String sECTION_NM_EN) {
		SECTION_NM_EN = sECTION_NM_EN;
	}
	public String getCONTENTS_CD() {
		return CONTENTS_CD;
	}
	public void setCONTENTS_CD(String cONTENTS_CD) {
		CONTENTS_CD = cONTENTS_CD;
	}
	public String getCONTENTS_NM_KR() {
		return CONTENTS_NM_KR;
	}
	public void setCONTENTS_NM_KR(String cONTENTS_NM_KR) {
		CONTENTS_NM_KR = cONTENTS_NM_KR;
	}
	public String getCONTENTS_NM_EN() {
		return CONTENTS_NM_EN;
	}
	public void setCONTENTS_NM_EN(String cONTENTS_NM_EN) {
		CONTENTS_NM_EN = cONTENTS_NM_EN;
	}
	public String getOBJ_KNM() {
		return OBJ_KNM;
	}
	public void setOBJ_KNM(String oBJ_KNM) {
		OBJ_KNM = oBJ_KNM;
	}
	public String getOBJ_ENM() {
		return OBJ_ENM;
	}
	public void setOBJ_ENM(String oBJ_ENM) {
		OBJ_ENM = oBJ_ENM;
	}
	public String getDB_NM() {
		return DB_NM;
	}
	public void setDB_NM(String dB_NM) {
		DB_NM = dB_NM;
	}
	public String getINF_ID() {
		return INF_ID;
	}
	public void setINF_ID(String iNF_ID) {
		INF_ID = iNF_ID;
	}
	public String getPAR_INFO_TYPE_NM() {
		return PAR_INFO_TYPE_NM;
	}
	public void setPAR_INFO_TYPE_NM(String pAR_INFO_TYPE_NM) {
		PAR_INFO_TYPE_NM = pAR_INFO_TYPE_NM;
	}
	public String getINFO_TYPE_NM() {
		return INFO_TYPE_NM;
	}
	public void setINFO_TYPE_NM(String iNFO_TYPE_NM) {
		INFO_TYPE_NM = iNFO_TYPE_NM;
	}
	@Override
	public String toString() {
		return "CustomVO [CATEGORY_CD=" + CATEGORY_CD + ", CATEGORY_NM_KR="
				+ CATEGORY_NM_KR + ", CATEGORY_NM_EN=" + CATEGORY_NM_EN
				+ ", SECTION_CD=" + SECTION_CD + ", SECTION_NM_KR="
				+ SECTION_NM_KR + ", SECTION_NM_EN=" + SECTION_NM_EN
				+ ", CONTENTS_CD=" + CONTENTS_CD + ", CONTENTS_NM_KR="
				+ CONTENTS_NM_KR + ", CONTENTS_NM_EN=" + CONTENTS_NM_EN
				+ ", OBJ_KNM=" + OBJ_KNM + ", OBJ_ENM=" + OBJ_ENM + ", DB_NM="
				+ DB_NM + ", INF_ID=" + INF_ID + ", PAR_INFO_TYPE_NM="
				+ PAR_INFO_TYPE_NM + ", INFO_TYPE_NM=" + INFO_TYPE_NM
				+ ", getCATEGORY_CD()=" + getCATEGORY_CD()
				+ ", getCATEGORY_NM_KR()=" + getCATEGORY_NM_KR()
				+ ", getCATEGORY_NM_EN()=" + getCATEGORY_NM_EN()
				+ ", getSECTION_CD()=" + getSECTION_CD()
				+ ", getSECTION_NM_KR()=" + getSECTION_NM_KR()
				+ ", getSECTION_NM_EN()=" + getSECTION_NM_EN()
				+ ", getCONTENTS_CD()=" + getCONTENTS_CD()
				+ ", getCONTENTS_NM_KR()=" + getCONTENTS_NM_KR()
				+ ", getCONTENTS_NM_EN()=" + getCONTENTS_NM_EN()
				+ ", getOBJ_KNM()=" + getOBJ_KNM() + ", getOBJ_ENM()="
				+ getOBJ_ENM() + ", getDB_NM()=" + getDB_NM()
				+ ", getINF_ID()=" + getINF_ID() + ", getPAR_INFO_TYPE_NM()="
				+ getPAR_INFO_TYPE_NM() + ", getINFO_TYPE_NM()="
				+ getINFO_TYPE_NM() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
