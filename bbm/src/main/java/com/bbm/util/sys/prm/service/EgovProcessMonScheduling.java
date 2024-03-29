package com.bbm.util.sys.prm.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.bbm.util.fcc.service.EgovDateUtil;
import com.bbm.util.fcc.service.EgovStringUtil;

/**
 * 개요
 * - 프로세스 모니터링을 위한 스케쥴링 클래스를 정의한다.
 * @author 박종선
 * @version 1.0
 * @created 08-9-2010 오후 3:54:45
 */

@Service("egovProcessMonScheduling")
public class EgovProcessMonScheduling {
	
	@Resource(name="EgovProcessMonService")
	private EgovProcessMonService egovProcessMonService;
	
	@Resource(name="mntrngMailSender")
    private MailSender mntrngMailSender; 

    @Resource(name="mntrngMessage")
    private SimpleMailMessage mntrngMessage;
    
    Logger log = Logger.getLogger(this.getClass());

	// 모니터링 대상을 읽기위한 페이지 크기 
	private static final int RECORD_COUNT_PER_PAGE = 10000;	

	/**
	 * 프로세스 모니터링를 수행한다.
	 * @param
	 * @return
	 */
	public void monitorProcess() throws Exception {
		
		// 모니터링 대상 정보 읽어들이기
		List<ProcessMon> targetList = null;
		ProcessMonVO searchVO = new ProcessMonVO();
		
		// 모니터링 대상 검색 조건 초기화 
		searchVO.setPageIndex(1);
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(RECORD_COUNT_PER_PAGE);
		targetList = egovProcessMonService.selectProcessMonList(searchVO);
		
		log.debug("조회조건 " + searchVO);
		if (log.isDebugEnabled()) {
			log.debug("Result 건수 : " + targetList.size());
		}
		
		// 서비스체크 함수 호출.
		Iterator<ProcessMon> iter = targetList.iterator();
		ProcessMon target = null;
    	String procsSttus = null;
		String processNm  = "";    	
  
		boolean nrmltAt = true;
		
		while(iter.hasNext()) {
			
			nrmltAt = true;
			target = (ProcessMon)iter.next();
			if (log.isDebugEnabled()) {
				log.debug("Data : " + target);
			}
			
			// 서비스 체크 수행. 
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
			target.setCreatDt(formatter.format(new java.util.Date()));
			
			processNm = target.getProcessNm();
			
			try{
				
				procsSttus = ProcessMonChecker.getProcessId(processNm);
				target.setProcsSttus(procsSttus);
				
			}catch (Exception e1) {
				target.setLogInfo(e1.getMessage());
				nrmltAt = false;
			}
			
			if (procsSttus == "02"){
				nrmltAt = false;
			}
			
			// email 전송. 
			if (!nrmltAt) {
				target.setProcsSttus("비정상");	
				sendEmail(target);
			}	
			
			// DB에 결과값 저장
			target.setProcsSttus(procsSttus);
			if(procsSttus == "02"){
				target.setLogInfo("실행 중인 작업 중 지정된 조건에 일치하는 작업이 없습니다.");
			}
			
			target.setLastUpdusrId("SYSTEM");
			egovProcessMonService.updateProcessMonSttus(target);
		}
	}

	/**
	 * 이메일을 전송한다.
	 * @return
	 * 
	 * @param target
	 */
	private void sendEmail(ProcessMon target){
		
    	String subject = null;
    	String text = null;
    	String errorContents = null;
    	
    	SimpleMailMessage msg = new SimpleMailMessage(this.mntrngMessage); 
        // 수신자
        msg.setTo(target.getMngrEmailAddr());
        // 메일제목
        subject = msg.getSubject();
        subject = EgovStringUtil.replace(subject, "{모니터링종류}", "프로세스모니터링");
        msg.setSubject(subject);
        // 메일내용
        text = msg.getText();
        text = EgovStringUtil.replace(text, "{모니터링종류}", "프로세스모니터링");
        errorContents = "프로세스명 : ";
        errorContents += target.getProcessNm();
        errorContents += "\n";
        errorContents += "상태 : ";
        errorContents += target.getProcsSttus();
        errorContents += "\n";
        errorContents += "모티터링 시각 : ";
        errorContents += EgovDateUtil.convertDate(target.getCreatDt(), "", "", "");
        errorContents += "\n";
        if(target.getLogInfo() != null && !target.getLogInfo().equals("")){
        	errorContents += target.getProcessNm() + " 의 프로세스 상태가 비정상입니다.  \n로그를 확인해주세요.";
        }
        text = EgovStringUtil.replace(text, "{에러내용}", errorContents);
        msg.setText(text);
        
        this.mntrngMailSender.send(msg); 
    } 

}