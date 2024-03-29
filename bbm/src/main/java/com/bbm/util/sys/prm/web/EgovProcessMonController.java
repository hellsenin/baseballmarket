package com.bbm.util.sys.prm.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.cmm.ComDefaultCodeVO;
import com.bbm.cmm.EgovMessageSource;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.util.sys.prm.service.EgovProcessMonService;
import com.bbm.util.sys.prm.service.ProcessMon;
import com.bbm.util.sys.prm.service.ProcessMonChecker;
import com.bbm.util.sys.prm.service.ProcessMonLogVO;
import com.bbm.util.sys.prm.service.ProcessMonVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 개요
 * - PROCESS모니터링에 대한 controller 클래스를 정의한다.
 * 
 * 상세내용
 * - PROCESS모니터링에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - PROCESS모니터링에 조회기능은 목록조회, 상세조회로 구분된다.
 * @author 박종선
 * @version 1.0
 * @created 08-9-2010 오후 3:54:45
 *  <pre>
 * == 개정이력(Modification Information) ==
 * 
 *   수정일       수정자           수정내용
 *  -------     --------    ---------------------------
 *  2010.9.8   박종선     최초 생성
 *  2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */

@Controller
public class EgovProcessMonController {
	 
	 
	
	
	@Resource(name="EgovProcessMonService")
    protected EgovProcessMonService processMonService;
	
	@Resource(name="propertiesService")
    protected EgovPropertyService propertyService;
	
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Autowired
    private DefaultBeanValidator beanValidator;
    
    Logger log = Logger.getLogger(this.getClass());	

	/**
	 * 등록된 PROCESS모니터링 정보를 조회 한다.
	 * @param processMonVO- PROCESS모니터링 VO
	 * @return String - 리턴 Url
	 * 
	 * @param processMonVO
	 */
    @IncludedInfo(name="프로세스모니터링", order = 2110 ,gid = 90)
    @RequestMapping("/utl/sys/prm/EgovComUtlProcessMonList.do")    
	public String selectProcessMonList(
			@ModelAttribute("searchVO")ProcessMonVO processMonVO
			, ModelMap model
			) throws Exception {
    	
    	//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		processMonVO.setPageUnit(propertyService.getInt("pageUnit"));
		processMonVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(processMonVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(processMonVO.getPageUnit());
		paginationInfo.setPageSize(processMonVO.getPageSize());

		processMonVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		processMonVO.setLastIndex(paginationInfo.getLastRecordIndex());
		processMonVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
        List ProcessMonList = processMonService.selectProcessMonList(processMonVO);
        model.addAttribute("resultList", ProcessMonList);
        
        int totCnt = processMonService.selectProcessMonTotCnt(processMonVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);		

		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonList";
	}

	/**
	 * PROCESS모니터링상세 정보를 조회 한다.
	 * @param ProcessMonVO - PROCESS모니터링 VO
	 * @return String - 리턴 Url
	 * 
	 * @param processMonVO
	 */
    @RequestMapping("/utl/sys/prm/EgovComUtlProcessMon.do")
	public String selectProcessMon(
			@ModelAttribute("processMonVO") ProcessMonVO processMonVO
			, ModelMap model
			) throws Exception {
    	
    	ProcessMon vo = processMonService.selectProcessMon(processMonVO);
    	model.addAttribute("result", vo);
    	//model.addAttribute("processNm", ProcessMonChecker.getProcessId(vo.getProcessNm()));       	
    	
		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonDetail";    	
	}   

	/**
	 * PROCESS모니터링 정보를 신규로 등록한다.
	 * @param processNm - PROCESS모니터링 model
	 * @return String - 리턴 Url
	 * 
	 * @param processNm
	 */
    @RequestMapping(value="/utl/sys/prm/EgovComUtlProcessMonRegist.do")    
	public String insertProcessMon(
			@ModelAttribute("processMonVO") ProcessMonVO processMonVO
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}
    	
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
    	if (processMonVO.getProcessNm() == null
            ||processMonVO.getProcessNm().equals("")) {
        	return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonRegist";
        }		
		
		//서버  validate 체크
        beanValidator.validate(processMonVO, bindingResult);
		if(bindingResult.hasErrors()){
    		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonRegist";			
		}
		
		//아이디 설정
		processMonVO.setFrstRegisterId((String)loginVO.getUniqId());
		processMonVO.setLastUpdusrId((String)loginVO.getUniqId());
		
		processMonService.insertProcessMon(processMonVO);
        return "forward:/utl/sys/prm/EgovComUtlProcessMonList.do";
        
	}

	/**
	 * 기 등록 된 PROCESS모니터링 정보를 수정 한다.
	 * @param processNm - PROCESS모니터링 model
	 * @return String - 리턴 Url
	 * 
	 * @param processNm
	 */
    @RequestMapping(value="/utl/sys/prm/EgovComUtlProcessMonModify.do")    
	public String updateProcessMon(
			@ModelAttribute("processMonVO") ProcessMonVO processMonVO
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			) throws Exception {
    	
    	//로그인 객체 선언
    	LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();		
    	
    	String sCmd = commandMap.get("cmd") == null ? "": (String)commandMap.get("cmd");
    	if (sCmd.equals("")) {
    		ProcessMonVO vo = processMonService.selectProcessMon(processMonVO);
    		model.addAttribute("processMonVO", vo);
    		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonModify";
    	} else if (sCmd.equals("Modify")) {
    		beanValidator.validate(processMonVO, bindingResult);
    		if (bindingResult.hasErrors()){
    			ProcessMonVO vo = processMonService.selectProcessMon(processMonVO);
    			model.addAttribute("processMonVO", vo);
    			return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonModify";
    		}
    		
        	// 아이디 설정
    		processMonVO.setFrstRegisterId((String)loginVO.getUniqId());
    		processMonVO.setLastUpdusrId((String)loginVO.getUniqId());
    		
    		processMonService.updateProcessMon(processMonVO);
    		return "forward:/utl/sys/prm/EgovComUtlProcessMonList.do";
    	} else {
    		return "forward:/utl/sys/prm/EgovComUtlProcessMonList.do";
    	}
    }  

	/**
	 * 기 등록된 PROCESS모니터링 정보를 삭제한다.
	 * @param processNm - PROCESS모니터링 model
	 * @return String - 리턴 Url
	 * 
	 * @param processNm
	 */
    @RequestMapping(value="/utl/sys/prm/EgovComUtlProcessMonRemove.do")        
	public String deleteProcessMon(
			@ModelAttribute("processMonVO") ProcessMonVO processMonVO
			, ModelMap model
			) throws Exception {
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}
    	
    	processMonService.deleteProcessMon(processMonVO);
		return "forward:/utl/sys/prm/EgovComUtlProcessMonList.do";
	}
    
	/**
	 * 프로세스의 상태를 조회한다.
	 * @param processMon
	 * @return  String
	 * 
	 * @param processSttus
	 */
    @RequestMapping("/utl/sys/prm/selectProcessSttus.do")
	public String selectProcessSttus(
			@ModelAttribute("processMonVO") ProcessMonVO processMonVO
			, ModelMap model
			) throws Exception {
    	
		//System.out.println("FileSysNm" + fileSysMntrngVO.getFileSysNm());  	
    	model.addAttribute("processSttus", ProcessMonChecker.getProcessId(processMonVO.getProcessNm()));
    	model.addAttribute("processMonVO", processMonVO);
    	
  
		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonRegist";
	}
    
    /**
	 * 프로세스 모니터링로그 정보에 대한 목록을 조회한다.
	 * @param ProcessMonVO - 프로세스 모니터링로그 VO
	 * @return  String - 리턴 URL
	 * 
	 * @param processMonVO
	 */
    @RequestMapping("/utl/sys/prm/EgovComUtlProcessMonLogList.do")
	public String selectProcessMonLogList(
			@ModelAttribute("searchVO") ProcessMonLogVO processMonLogVO
			, ModelMap model
			) throws Exception {
    	
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		processMonLogVO.setPageUnit(propertyService.getInt("pageUnit"));
		processMonLogVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(processMonLogVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(processMonLogVO.getPageUnit());
		paginationInfo.setPageSize(processMonLogVO.getPageSize());

		processMonLogVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		processMonLogVO.setLastIndex(paginationInfo.getLastRecordIndex());
		processMonLogVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		// 조회기간설정
		if(processMonLogVO.getSearchBgnDe() != null && processMonLogVO.getSearchEndDe() != null){
			if(!processMonLogVO.getSearchBgnDe().equals("") && !processMonLogVO.getSearchEndDe().equals("")){
				processMonLogVO.setSearchBgnDt(processMonLogVO.getSearchBgnDe() + " " + processMonLogVO.getSearchBgnHour());
				processMonLogVO.setSearchEndDt(processMonLogVO.getSearchEndDe() + " " + processMonLogVO.getSearchEndHour());
			}
		}
		
		Map<String, Object> map = processMonService.selectProcessMonLogList(processMonLogVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		paginationInfo.setTotalRecordCount(totCnt);
		
		// 조회시작시
    	model.addAttribute("searchBgnHour", (List<ComDefaultCodeVO>)getTimeHH());
    	// 조회종료시
    	model.addAttribute("searchEndHour", (List<ComDefaultCodeVO>)getTimeHH());
    	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonLogList";
	}

	/**
	 * 프로세스 모니터링로그 상세정보를 조회한다.
	 * @param FileSysMntrngLogVO - 프로세스 모니터링로그 VO
	 * @return  String - 리턴 URL
	 * 
	 * @param fileSysMntrngLogVO
	 */
    @RequestMapping("/utl/sys/prm/EgovComUtlProcessMonLog.do")	
	public String selectProcessMonLog(
			@ModelAttribute("processMonLogVO") ProcessMonLogVO processMonLogVO
			, ModelMap model
			) throws Exception {
    	
		ProcessMonLogVO vo = processMonService.selectProcessMonLog(processMonLogVO);
		
		if(vo.getCreatDt() != null && !vo.getCreatDt().equals("")){
			if(vo.getCreatDt().length() > 18){
				vo.setCreatDt(vo.getCreatDt().substring(0, 19));
			}
		}
		
    	model.addAttribute("result", vo);
    	
		return "egovframework/com/utl/sys/prm/EgovComUtlProcessMonLogDetail";
	}
	
	/**
	 * 시간의 LIST를 반환한다.
	 * @return  List
	 * @throws 
	 */
	private List<ComDefaultCodeVO> getTimeHH (){
    	ArrayList<ComDefaultCodeVO> listHH = new ArrayList<ComDefaultCodeVO>();
    	//HashMap hmHHMM;
    	for(int i=0;i < 24; i++){
    		String sHH = "";
    		String strI = String.valueOf(i);
    		if(i<10){
    			sHH = "0" + strI;
    		}else{
    			sHH = strI;
    		}
    		
    		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
    		codeVO.setCode(sHH);
    		codeVO.setCodeNm(sHH + ":00");
    		
    		listHH.add(codeVO);
    	}
    	
    	return listHH;
	}    

}