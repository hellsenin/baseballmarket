package com.bbm.cop.cmy.service;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 커뮤티니 사용자 관리를 위한 모델  클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.4.2  이삼섭          최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class CommunityUser implements Serializable {

    /** 커뮤니티아이디 */
    private String cmmntyId = "";
    
    /** 최초등록자 아이디 */
    private String frstRegisterId = "";
    
    /** 최초등록시점 */
    private String frstRegisterPnttm = "";
    
    /** 최종수정자 아이디 */
    private String lastUpdusrId = "";
    
    /** 최종수정시점 */
    private String lastUpdusrPnttm = "";
    
    /** 관리자여부 */
    private String mngrAt = "";
    
    /** 탈퇴일 */
    private String secsnDe = "";
    
    /** 가입일 */
    private String sbscrbDe = "";
    
    /** 사용여부 */
    private String useAt = "";
    
    /** 사용자 아이디 */
    private String emplyrId = "";
    
    /** 사용자명 */
    private String emplyrNm = "";

    /**
     * cmmntyId attribute를 리턴한다.
     * 
     * @return the cmmntyId
     */
    public String getCmmntyId() {
	return cmmntyId;
    }

    /**
     * cmmntyId attribute 값을 설정한다.
     * 
     * @param cmmntyId
     *            the cmmntyId to set
     */
    public void setCmmntyId(String cmmntyId) {
	this.cmmntyId = cmmntyId;
    }

    /**
     * frstRegisterId attribute를 리턴한다.
     * 
     * @return the frstRegisterId
     */
    public String getFrstRegisterId() {
	return frstRegisterId;
    }

    /**
     * frstRegisterId attribute 값을 설정한다.
     * 
     * @param frstRegisterId
     *            the frstRegisterId to set
     */
    public void setFrstRegisterId(String frstRegisterId) {
	this.frstRegisterId = frstRegisterId;
    }

    /**
     * frstRegisterPnttm attribute를 리턴한다.
     * 
     * @return the frstRegisterPnttm
     */
    public String getFrstRegisterPnttm() {
	return frstRegisterPnttm;
    }

    /**
     * frstRegisterPnttm attribute 값을 설정한다.
     * 
     * @param frstRegisterPnttm
     *            the frstRegisterPnttm to set
     */
    public void setFrstRegisterPnttm(String frstRegisterPnttm) {
	this.frstRegisterPnttm = frstRegisterPnttm;
    }

    /**
     * lastUpdusrId attribute를 리턴한다.
     * 
     * @return the lastUpdusrId
     */
    public String getLastUpdusrId() {
	return lastUpdusrId;
    }

    /**
     * lastUpdusrId attribute 값을 설정한다.
     * 
     * @param lastUpdusrId
     *            the lastUpdusrId to set
     */
    public void setLastUpdusrId(String lastUpdusrId) {
	this.lastUpdusrId = lastUpdusrId;
    }

    /**
     * lastUpdusrPnttm attribute를 리턴한다.
     * 
     * @return the lastUpdusrPnttm
     */
    public String getLastUpdusrPnttm() {
	return lastUpdusrPnttm;
    }

    /**
     * lastUpdusrPnttm attribute 값을 설정한다.
     * 
     * @param lastUpdusrPnttm
     *            the lastUpdusrPnttm to set
     */
    public void setLastUpdusrPnttm(String lastUpdusrPnttm) {
	this.lastUpdusrPnttm = lastUpdusrPnttm;
    }

    /**
     * mngrAt attribute를 리턴한다.
     * 
     * @return the mngrAt
     */
    public String getMngrAt() {
	return mngrAt;
    }

    /**
     * mngrAt attribute 값을 설정한다.
     * 
     * @param mngrAt
     *            the mngrAt to set
     */
    public void setMngrAt(String mngrAt) {
	this.mngrAt = mngrAt;
    }

    /**
     * secsnDe attribute를 리턴한다.
     * 
     * @return the secsnDe
     */
    public String getSecsnDe() {
	return secsnDe;
    }

    /**
     * secsnDe attribute 값을 설정한다.
     * 
     * @param secsnDe
     *            the secsnDe to set
     */
    public void setSecsnDe(String secsnDe) {
	this.secsnDe = secsnDe;
    }

    /**
     * sbscrbDe attribute를 리턴한다.
     * 
     * @return the sbscrbDe
     */
    public String getSbscrbDe() {
	return sbscrbDe;
    }

    /**
     * sbscrbDe attribute 값을 설정한다.
     * 
     * @param sbscrbDe
     *            the sbscrbDe to set
     */
    public void setSbscrbDe(String sbscrbDe) {
	this.sbscrbDe = sbscrbDe;
    }

    /**
     * useAt attribute를 리턴한다.
     * 
     * @return the useAt
     */
    public String getUseAt() {
	return useAt;
    }

    /**
     * useAt attribute 값을 설정한다.
     * 
     * @param useAt
     *            the useAt to set
     */
    public void setUseAt(String useAt) {
	this.useAt = useAt;
    }

    /**
     * emplyrId attribute를 리턴한다.
     * 
     * @return the emplyrId
     */
    public String getEmplyrId() {
	return emplyrId;
    }

    /**
     * emplyrId attribute 값을 설정한다.
     * 
     * @param emplyrId
     *            the emplyrId to set
     */
    public void setEmplyrId(String emplyrId) {
	this.emplyrId = emplyrId;
    }

    /**
     * emplyrNm attribute를 리턴한다.
     * 
     * @return the emplyrNm
     */
    public String getEmplyrNm() {
	return emplyrNm;
    }

    /**
     * emplyrNm attribute 값을 설정한다.
     * 
     * @param emplyrNm
     *            the emplyrNm to set
     */
    public void setEmplyrNm(String emplyrNm) {
	this.emplyrNm = emplyrNm;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
}
