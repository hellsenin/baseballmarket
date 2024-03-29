package com.bbm.util.sys.srm.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.bbm.util.fcc.service.EgovDateUtil;
import com.bbm.util.fcc.service.EgovStringUtil;


/**
 * 개요
 * - 서버자원모니터링 Service Interface를 invoke 할 수 있는 클래스를 정의한다.
 *
 * 상세내용
 * - 서버자원모니터링 정보 결과를 확인할 수 있는 함수를 호출할 수 있는 기능을 제공한다.
 * @author lee.m.j
 * @version 1.0
 * @created 06-9-2010 오전 11:23:59
 */

@Service("egovServerResrceMntrngScheduling")
public class EgovServerResrceMntrngScheduling {




	@Resource(name="egovServerResrceMntrngService")
	private EgovServerResrceMntrngService egovServerResrceMntrngService;

	//@Resource(name="EgovSmsInfoService")
	//private EgovSmsInfoService egovSmsInfoService;

    @Resource(name="mntrngMessage")
    private SimpleMailMessage mntrngMessage;

	@Resource(name="mntrngMailSender")
    private MailSender mntrngMailSender;

	Process process;
    JMXServiceURL address = null;
    JMXConnector connector = null;
    MBeanServerConnection mbs = null;
    ObjectName name = null;
    MBeanInfo mBeanInfo = null;
    MBeanAttributeInfo[] attrInfos = null;
    ServerResrceMntrng serverResrceMntrng = null;
    ServerResrceMntrngVO serverResrceMntrngVO = null;

	/**
	 * 서버자원 모니터링를 수행한다.
	 * @param
	 * @return
	 */

    public void init(ServerResrceMntrngVO serverResrceMntrngVO) throws Exception {

    	String serverId = serverResrceMntrngVO.getServerId();
    	String serverEqpmnId = serverResrceMntrngVO.getServerEqpmnId();
    	String serverNm = serverResrceMntrngVO.getServerNm();
    	String serverEqpmnIp = serverResrceMntrngVO.getServerEqpmnIp();
    	String mngrEamilAddr = serverResrceMntrngVO.getMngrEamilAddr();

    	serverResrceMntrng = new ServerResrceMntrng();
    	serverResrceMntrng.setServerId(serverId);
        serverResrceMntrng.setServerEqpmnId(serverEqpmnId);
        serverResrceMntrng.setServerNm(serverNm);
        serverResrceMntrng.setServerEqpmnIp(serverEqpmnIp);
        serverResrceMntrng.setMngrEamilAddr(mngrEamilAddr);

    	try {
    		address = new JMXServiceURL("service:jmx:rmi://"+serverEqpmnIp+":9999/jndi/rmi://"+serverEqpmnIp+":9999/server");
    		connector = JMXConnectorFactory.connect(address);

            mbs = connector.getMBeanServerConnection();

            name = new ObjectName("com.bbm.util.sys.srm.service:type=EgovServerResrceMntrng");

            mBeanInfo = mbs.getMBeanInfo(name);
            attrInfos = mBeanInfo.getAttributes();

            for (MBeanAttributeInfo attrInfo : attrInfos) {
            	if(attrInfo.getName().equals("CpuUsage")) serverResrceMntrng.setCpuUseRt(mbs.getAttribute(name, attrInfo.getName()).toString());
            	else if(attrInfo.getName().equals("MemoryUsage")) serverResrceMntrng.setMoryUseRt(mbs.getAttribute(name, attrInfo.getName()).toString());
            	System.out.println(attrInfo.getName() + " = " + mbs.getAttribute(name, attrInfo.getName()));
            }
            serverResrceMntrng.setSvcSttus("01");
            serverResrceMntrng.setFrstRegisterId(InetAddress.getLocalHost().getHostAddress());
            serverResrceMntrng.setLastUpdusrId("SYSTEM");

            if(Double.parseDouble(serverResrceMntrng.getCpuUseRt()) > 90 || Double.parseDouble(serverResrceMntrng.getMoryUseRt()) > 90) {
            	serverResrceMntrng.setSvcSttus("02");
            	serverResrceMntrng.setLogInfo("적정수치를 초과하였습니다.");
            	sendEmail(serverResrceMntrng);
            	// sendSMS(egovServerResrceMntrngService.selectServerResrceMntrng(serverResrceMntrngVO));
            }
            egovServerResrceMntrngService.insertServerResrceMntrng(serverResrceMntrng);

    	} catch (Exception e) {

    		serverResrceMntrng.setSvcSttus("02");

    		ByteArrayOutputStream out = new ByteArrayOutputStream();
    		PrintStream printStream = new PrintStream (out);
    		//e.printStackTrace(printStream);
		    System.out.println(e);	// 2011.10.10 보안점검 후속조치

    		String logInfo = out.toString();
    		byte[] btLogInfo = logInfo.getBytes();

    		if(btLogInfo.length > 2000)
    			logInfo = new String(btLogInfo, 0, 2000);

    		serverResrceMntrng.setLogInfo(logInfo);
            serverResrceMntrng.setFrstRegisterId(InetAddress.getLocalHost().getHostAddress());
            serverResrceMntrng.setLastUpdusrId("SYSTEM");

    		try {
    			egovServerResrceMntrngService.insertServerResrceMntrng(serverResrceMntrng);
    		} catch(Exception ex) {
    			new Exception(ex);
    		}

    	} finally {
            if (connector != null)
            	try { connector.close();
            	} catch(IOException ie) {
            		System.out.println(ie);	// 2011.10.10 보안점검 후속조치
            	}
        }
    }

	/**
	 * 서버자원 모니터링를 수행한다.
	 * @param
	 * @return
	 */
	public void monitorServerResrce() {

		try {
			List<ServerResrceMntrngVO> result = egovServerResrceMntrngService.selectMntrngServerList(serverResrceMntrngVO);
  		    Iterator iter = result.iterator();

  		    while(iter.hasNext()) {
  		    	ServerResrceMntrngVO serverResrceMntrngVO = (ServerResrceMntrngVO)iter.next();
  		    	init(serverResrceMntrngVO);
  		    }

		} catch(Exception e) {
		    //e.printStackTrace();
		    System.out.println(e);	// 2011.10.10 보안점검 후속조치
		}
	}

	/**
	 * 이메일을 전송한다.
	 * @param serverResrceMntrngVO - 서버자원모니터링 Vo
	 * @return
	 *
	 * @param serverResrceMntrngVO
	 */
	public void sendEmail(ServerResrceMntrng serverResrceMntrng) {
    	String subject = null;
    	String text = null;
    	String errorContents = null;

    	SimpleMailMessage msg = new SimpleMailMessage(this.mntrngMessage);
        // 수신자
        msg.setTo(serverResrceMntrng.getMngrEamilAddr());
        // 메일제목
        subject = msg.getSubject();
        subject = EgovStringUtil.replace(subject, "{모니터링종류}", "서버자원서비스모니터링");
        msg.setSubject(subject);
        // 메일내용
        text = msg.getText();
        text = EgovStringUtil.replace(text, "{모니터링종류}", "서버자원서비스모니터링");
        errorContents = "서버명 : ";
        errorContents += serverResrceMntrngVO.getServerNm();
        errorContents += "\n";
        errorContents += "서버IP : ";
        errorContents += serverResrceMntrngVO.getServerEqpmnIp();
        errorContents += "\n";
        errorContents += "CPU사용률 : ";
        errorContents += serverResrceMntrngVO.getCpuUseRt();
        errorContents += "\n";
        errorContents += "메모리사용률 : ";
        errorContents += serverResrceMntrngVO.getMoryUseRt();
        errorContents += "\n";
        errorContents += "서비스상태 : 비정상";
     // errorContents += serverResrceMntrngVO.getSvcSttusNm();
        errorContents += "\n";
        errorContents += "내용 : ";
        errorContents += serverResrceMntrngVO.getLogInfo();
        errorContents += "\n";
        errorContents += "생성일시 : ";
        errorContents += EgovDateUtil.convertDate(serverResrceMntrngVO.getCreatDt(), "", "", "");
        errorContents += "\n";
        errorContents += serverResrceMntrngVO.getServerNm() + " 의 서버자원 서비스 상태가 비정상입니다. \n로그를 확인해주세요.";
        text = EgovStringUtil.replace(text, "{에러내용}", errorContents);
        msg.setText(text);

        this.mntrngMailSender.send(msg);
	}

	public void sendSMS(ServerResrceMntrng serverResrceMntrng) throws Exception {
		/*String[] receiveTelno = {"010-6802-0886"};
		Sms sms = new Sms();
		sms.setTrnsmitTelno("000-000-0000"); // 발신자
		sms.setRecptnTelno(receiveTelno); // 수신자
		sms.setTrnsmitCn("테스트 입니다");

		egovSmsInfoService.insertSmsInf(sms);*/
	}

}