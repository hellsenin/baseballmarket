package com.bbm.util.sys.nsm.service;

import java.io.IOException;
import java.net.Socket;

/**
 * 개요
 * - 네트워크서비스 모니터링을 위한 Check 클래스
 * 
 * 상세내용
 * - 소켓으로 네트워크서비스에 접속한 뒤 해당 결과를 제공한다.
 * @author 장철호
 * @version 1.0
 * @created 28-6-2010 오전 11:33:43
 */

public class NtwrkSvcMntrngChecker {

	/**
	 * 네트워크서비스 모니터링을 수행한다.
	 * @param String - 네트워크시스템 IP
	 * @param int - 네트워크시스템 포트
	 * @return  NtwrkSvcMntrngResult - 네트워크서비스 모니터링 결과
	 * 
	 * @param sysIp 
	 * @param sysPort 
	 */
	public static NtwrkSvcMntrngResult check(String sysIp, int sysPort) {
		
		Socket clientSocket = null;
		
		try {
			
			clientSocket = new Socket(sysIp, sysPort);
			
			return new NtwrkSvcMntrngResult(true, null);
		} catch (IOException e) {
			//log.error("네트워크서비스모니터링 에러 : " + e.getMessage());
			//log.debug(e.getMessage(), e);
			return new NtwrkSvcMntrngResult(false, e);
		} catch (Exception e) {
			//log.error("네트워크서비스모니터링 에러 : " + e.getMessage());
			//log.debug(e.getMessage(), e);
			return new NtwrkSvcMntrngResult(false, e);
		} finally {
			if(clientSocket != null){
				try{clientSocket.close();}catch(Exception e){return new NtwrkSvcMntrngResult(false, e);}
			}
		}
		
	}

}
