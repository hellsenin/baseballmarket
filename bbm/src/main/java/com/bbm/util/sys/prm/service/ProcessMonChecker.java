package com.bbm.util.sys.prm.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bbm.cmm.service.Globals;

/**
 * 개요
 * - 프로세스 모니터링을 위한 Check 클래스
 *
 * 상세내용
 * - 프로세스의 상태 결과를 제공한다.
 * @author 박종선
 * @version 1.0
 * @created 7-9-2010
 */

public class ProcessMonChecker {

    // Log
    protected static final Log log = LogFactory.getLog(ProcessMonChecker.class);

	/**
	 * <pre>
	 * Comment : 프로세스 정보를 확인한다. (
	 * </pre>
	 * @param String processName
	 * @return List<String[]> 프로세스 정보를 리턴한다.
	 * @version 1.0 (2009.01.12.)
	 * @see
	 */
    public static String getProcessId(String processNm) throws Exception {

		Process p = null;
    	String procsSttus  = null;
        BufferedReader buf = null;
        String result = null;
		String execStr = "tasklist /fo table /nh /fi \"imagename eq "+processNm+"\"";
		int cnt = 0;
		String str = null;

		try {
			// 2011.10.10 보안점검 후속조치
			if (Globals.OS_TYPE == null) {
				throw new RuntimeException("Globals.OS_TYPE property value is needed!!!");
			    }
			// 2011.10.10 보안점검 후속조치 끝

			if ("WINDOWS".equals(Globals.OS_TYPE)) {
				cnt = -1;							//윈도우의 경우 정상 프로세스 일때 두번째 줄에 결과를 리턴한다.
				p = Runtime.getRuntime().exec(execStr);

			} else if ("UNIX".equals(Globals.OS_TYPE)) {
				String[] cmd = {"/bin/csh", "-c", "ps -A | grep " + processNm};
				p = Runtime.getRuntime().exec(cmd);
			}

			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((str = buf.readLine()) != null) {
				cnt++;
			}
			if (cnt > 0)
				procsSttus = "01";
			else
				procsSttus = "02";

        }catch(Exception e){
        	procsSttus = "02";
    	} finally {
    		if (buf != null)
    			try {
    				buf.close();
    			} catch (Exception e) {
    			    log.debug(e.getMessage());	// 2011.10.10 보안점검 후속조치
    			}
    	}

	    return procsSttus;
    }

}
