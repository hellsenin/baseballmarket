package com.bbm.util.sys.rsc.service;

import org.springframework.stereotype.Service;

import com.bbm.cmm.LoginVO;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.sec.security.userdetails.EgovUserDetails;

/**
 * 개요
 * - 로그인 세션정보체크 컴포넌트에 대한 util 클래스를 정의한다.
 * 
 * 상세내용
 * - 로그인 세션정보체크에 대한 기능을 제공한다.
 * @author lee.m.j
 * @version 1.0
 * @created 28-6-2010 오전 10:44:26
 */

@Service("egovLoginSesionCeckUtil")
public class EgovLoginSesionCeckUtil {
	
	 
	 

	/**
	 * 로그인 후 이동할 처리화면을 세션에 등록한다.
	 * @param url - String
	 * @return String
	 */
	public void setLoginSession(String url) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		user.setUrl("");
		user.setUrl(url);
		new EgovUserDetails(user.getId(), user.getPassword(), true, user);
	}

	/**
	 * 로그인 세션정보체크 화면 이동
	 * @return String - 세션URL
	 */	
	public String checkLoginSessionView() throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		return user.getUrl();
	}

}
