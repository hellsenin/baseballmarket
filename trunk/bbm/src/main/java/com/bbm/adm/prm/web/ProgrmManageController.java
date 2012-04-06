package com.bbm.adm.prm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.cmm.ComDefaultVO;
import com.bbm.cmm.EgovMessageSource;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.cop.ems.service.EgovSndngMailRegistService;
import com.bbm.cop.ems.service.SndngMailVO;
import com.bbm.adm.prm.service.ProgrmManageService;
import com.bbm.adm.prm.service.ProgrmManageDtlVO;
import com.bbm.adm.prm.service.ProgrmManageVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/** 
 * 프로그램목록 관리및 변경을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *   2011.08.22  서준식          selectProgrmChangRequstProcess() 메서드 처리일자 trim 처리
 *   2011.8.26	정진오			IncludedInfo annotation 추가 
 * </pre>
 */

@Controller
public class ProgrmManageController {
	
	 
	 

	protected Log log = LogFactory.getLog(this.getClass());

	/** Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
    /** ProgrmManageService */
	@Resource(name = "progrmManageService")
    private ProgrmManageService progrmManageService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
    /** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
    private EgovSndngMailRegistService sndngMailRegistService;
	
    /**
     * 프로그램목록을 상세화면 호출 및 상세조회한다. 
     * @param tmp_progrmNm  String
     * @return 출력페이지정보 "admin/prm/ProgramListDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramListDetailSelect.do")
    public String selectProgrm(
    		@RequestParam("tmp_progrmNm") String tmp_progrmNm ,
   		    @ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
    	searchVO.setSearchKeyword(tmp_progrmNm);
    	ProgrmManageVO progrmManageVO = progrmManageService.selectProgrm(searchVO);
        model.addAttribute("progrmManageVO", progrmManageVO);
        return "bbm/admin/prm/ProgramListDetailSelectUpdt";
    }
	
    /**
     * 프로그램목록 리스트조회한다. 
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "admin/prm/ProgramListManage"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램관리",order = 1111 ,gid = 60)
    @RequestMapping(value="/admin/prm/ProgramListManageSelect.do")
    public String selectProgrmList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception { 
    	
        // 0. Spring Security 사용자권한 처리
    	
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
    	
    	
    	// 내역 조회
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
        List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);
        model.addAttribute("searchVO", searchVO);

        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
      	return "bbm/admin/prm/ProgramListManage";

    }

    /**
     * 프로그램목록 멀티 삭제한다. 
     * @param checkedProgrmFileNmForDel String
     * @return 출력페이지정보 "forward:/admin/prm/ProgramListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/sym/prm/ProgrmManageListDelete.do")
    public String deleteProgrmManageList(
            @RequestParam("checkedProgrmFileNmForDel") String checkedProgrmFileNmForDel ,
            @ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO, 
            ModelMap model)
            throws Exception {
		String sLocationUrl = null;
    	String resultMsg    = "";
    	
        // 0. Spring Security 사용자권한 처리
    	/*
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}
    	*/
    	String [] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		if (delProgrmFileNm == null || (delProgrmFileNm.length ==0)){ 
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
    		sLocationUrl = "forward:/admin/prm/ProgramListManageSelect.do";
		}else{
    	   progrmManageService.deleteProgrmManageList(checkedProgrmFileNmForDel);
    	   resultMsg = egovMessageSource.getMessage("success.common.delete");
    	   sLocationUrl ="forward:/admin/prm/ProgramListManageSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
        //status.setComplete();
        return sLocationUrl ;
    } 
    
    /**
     * 프로그램목록을 등록화면으로 이동 및 등록 한다. 
     * @param progrmManageVO ProgrmManageVO 
     * @param commandMap     Map     
     * @return 출력페이지정보 등록화면 호출시 "admin/prm/ProgramListRegist",
     *         출력페이지정보 등록처리시 "forward:/admin/prm/ProgramListManageSelect.do" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramListRegist.do")
    public String insertProgrmList(
    		Map commandMap, 
    		@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
			BindingResult bindingResult,
			ModelMap model)
            throws Exception { 
        String resultMsg = "";
        String sLocationUrl = null;
        
        
    	// 0. Spring Security 사용자권한 처리
        /*
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}
         */
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");	
        if(sCmd.equals("insert")){
	        beanValidator.validate(progrmManageVO, bindingResult);
			if (bindingResult.hasErrors()){
				sLocationUrl = "bbm/admin/prm/ProgramListRegist";
				return sLocationUrl;
			}
			if(progrmManageVO.getProgrmDc()==null || progrmManageVO.getProgrmDc().equals("")){progrmManageVO.setProgrmDc(" ");} 
	    	progrmManageService.insertProgrm(progrmManageVO);
			resultMsg = egovMessageSource.getMessage("success.common.insert");
	        sLocationUrl = "forward:/admin/prm/ProgramListManageSelect.do";
        }else{    		
            sLocationUrl = "bbm/admin/prm/ProgramListRegist";
        }
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl; 
    }    
    
    /**
     * 프로그램목록을 수정 한다. 
     * @param progrmManageVO ProgrmManageVO
     * @return 출력페이지정보 "forward:/admin/prm/ProgramListManageSelect.do" 
     * @exception Exception
     */
    /*프로그램목록수정*/
    @RequestMapping(value="/admin/prm/ProgramListDetailSelectUpdt.do")
    public String updateProgrmList(
    		@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception { 
		String resultMsg = "";
        String sLocationUrl = null;
    	// 0. Spring Security 사용자권한 처리
   	    /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/

        beanValidator.validate(progrmManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/admin/prm/ProgramListDetailSelect.do";
			return sLocationUrl;
		}
		if(progrmManageVO.getProgrmDc()==null || progrmManageVO.getProgrmDc().equals("")){progrmManageVO.setProgrmDc(" ");}
		progrmManageService.updateProgrm(progrmManageVO);
		resultMsg = egovMessageSource.getMessage("success.common.update");
        sLocationUrl = "forward:/admin/prm/ProgramListManageSelect.do";
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl; 
    }    
    
    /**
     * 프로그램목록을 삭제 한다. 
     * @param progrmManageVO ProgrmManageVO
     * @return 출력페이지정보 "forward:/admin/prm/ProgramListManageSelect.do" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramListManageDelete.do")
    public String deleteProgrmList(
    		@ModelAttribute("progrmManageVO") 
    		ProgrmManageVO progrmManageVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
        progrmManageService.deleteProgrm(progrmManageVO);
        resultMsg = egovMessageSource.getMessage("success.common.delete");
    	model.addAttribute("resultMsg", resultMsg);
        return "forward:/admin/prm/ProgramListManageSelect.do";
    } 

    /**
     * 프로그램변경요청목록 조회한다. 
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "admin/prm/ProgramChangeRequst" 
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경요청관리",order = 1112 ,gid = 60)
    @RequestMapping(value="/admin/prm/ProgramChangeRequstSelect.do")
    public String selectProgrmChangeRequstList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception { 
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
    	// 내역 조회
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    	
        List list_changerequst = progrmManageService.selectProgrmChangeRequstList(searchVO);
        model.addAttribute("list_changerequst", list_changerequst);

        int totCnt = progrmManageService.selectProgrmChangeRequstListTotCnt(searchVO);
 		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
         
         return "bbm/admin/prm/ProgramChangeRequst";
    }

    /**
     * 프로그램변경요청목록을 상세조회한다. 
     * @param progrmManageDtlVO ProgrmManageDtlVO
     * @return 출력페이지정보 "admin/prm/ProgramChangRequstDetailSelectUpdt" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChangRequstDetailSelect.do")
    public String selectProgrmChangeRequst(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
         if(progrmManageDtlVO.getProgrmFileNm()== null||progrmManageDtlVO.getProgrmFileNm().equals("")){
	    	 String FileNm = progrmManageDtlVO.getTmpProgrmNm();    	
	         progrmManageDtlVO.setProgrmFileNm(FileNm);
	         int tmpNo = progrmManageDtlVO.getTmpRqesterNo();    	
	         progrmManageDtlVO.setRqesterNo(tmpNo);
         }
         ProgrmManageDtlVO resultVO = progrmManageService.selectProgrmChangeRequst(progrmManageDtlVO);
         model.addAttribute("progrmManageDtlVO", resultVO);
         return "bbm/admin/prm/ProgramChangRequstDetailSelectUpdt";
    }
    
    /**
     * 프로그램변경요청 화면을 호출및 프로그램변경요청을 등록한다. 
     * @param progrmManageDtlVO ProgrmManageDtlVO
     * @param commandMap        Map
     * @return 출력페이지정보 등록화면 호출시 "admin/prm/ProgramChangRequstStre",
     *         출력페이지정보 등록처리시 "forward:/admin/prm/ProgramChangeRequstSelect.do" 
     * @exception Exception
     */
    /*프로그램변경요청등록*/
    @RequestMapping(value="/admin/prm/ProgramChangRequstStre.do")
    public String insertProgrmChangeRequst(
        	Map commandMap, 
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO, 
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception { 
    	String resultMsg = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
       	    return "bbm/uat/uia/LoginUsr";
    	}*/
		//로그인 객체 선언
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        String sLocationUrl = null;
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");	
        if(sCmd.equals("insert")){
    		//beanValidator 처리
	        beanValidator.validate(progrmManageDtlVO, bindingResult);
			if (bindingResult.hasErrors()){
				sLocationUrl = "bbm/admin/prm/ProgramChangRequstStre";
				return sLocationUrl;
			}
			if(progrmManageDtlVO.getChangerqesterCn()==null || progrmManageDtlVO.getChangerqesterCn().equals("")){progrmManageDtlVO.setChangerqesterCn("");}
			if(progrmManageDtlVO.getRqesterProcessCn()==null || progrmManageDtlVO.getRqesterProcessCn().equals("")){progrmManageDtlVO.setRqesterProcessCn("");}
			progrmManageService.insertProgrmChangeRequst(progrmManageDtlVO);
	    	resultMsg = egovMessageSource.getMessage("success.common.insert");
	        sLocationUrl = "forward:/admin/prm/ProgramChangeRequstSelect.do";
        }else{    	
	        /* MAX요청번호 조회 */
	    	ProgrmManageDtlVO tmp_vo = progrmManageService.selectProgrmChangeRequstNo(progrmManageDtlVO);			
			int _tmp_no = tmp_vo.getRqesterNo();
			progrmManageDtlVO.setRqesterNo(_tmp_no);
			progrmManageDtlVO.setRqesterPersonId(user.getId());
            sLocationUrl = "bbm/admin/prm/ProgramChangRequstStre";
        }
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl; 
    }

    /**
     * 프로그램변경 요청을 수정 한다. 
     * @param progrmManageDtlVO  ProgrmManageDtlVO
     * @return 출력페이지정보 "forward:/admin/prm/ProgramChangeRequstSelect.do" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChangRequstDetailSelectUpdt.do")
    public String updateProgrmChangeRequst(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO, 
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception { 
        String sLocationUrl = null;
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		//beanValidator 처리
        beanValidator.validate(progrmManageDtlVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/admin/prm/ProgramChangRequstDetailSelect.do";
			return sLocationUrl;
		}

    	if(progrmManageDtlVO.getRqesterPersonId().equals(loginVO.getId())){
			if(progrmManageDtlVO.getChangerqesterCn()==null || progrmManageDtlVO.getChangerqesterCn().equals("")){progrmManageDtlVO.setChangerqesterCn(" ");}
			if(progrmManageDtlVO.getRqesterProcessCn()==null || progrmManageDtlVO.getRqesterProcessCn().equals("")){progrmManageDtlVO.setRqesterProcessCn(" ");}
	        progrmManageService.updateProgrmChangeRequst(progrmManageDtlVO);
    		resultMsg = egovMessageSource.getMessage("success.common.update");
	        sLocationUrl = "forward:/admin/prm/ProgramChangeRequstSelect.do";
    	}else{
    		resultMsg = "수정이 실패하였습니다. 변경요청 수정은 변경요청자만 수정가능합니다.";
            progrmManageDtlVO.setTmpProgrmNm(progrmManageDtlVO.getProgrmFileNm());    	
            progrmManageDtlVO.setTmpRqesterNo(progrmManageDtlVO.getRqesterNo());    	
			sLocationUrl = "forward:/admin/prm/ProgramChangRequstDetailSelect.do";
    	}
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl; 
    }    

    /**
     * 프로그램변경 요청을 삭제 한다. 
     * @param progrmManageDtlVO  ProgrmManageDtlVO
     * @return 출력페이지정보 "forward:/admin/prm/ProgramChangeRequstSelect.do" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChangRequstDelete.do")
    public String deleteProgrmChangeRequst(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO, 
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	if(progrmManageDtlVO.getRqesterPersonId().equals(loginVO.getId())){
    	//progrmManageDtlVO.setRqesterPersonId(user.getId());
    		model.addAttribute("resultMsg", egovMessageSource.getMessage("success.common.delete"));
	        progrmManageService.deleteProgrmChangeRequst(progrmManageDtlVO);
	        sLocationUrl = "forward:/admin/prm/ProgramChangeRequstSelect.do";
    	}else{
    		model.addAttribute("resultMsg", "삭제에 실패하였습니다. 변경요청자만 삭제가능합니다.");
			sLocationUrl = "forward:/admin/prm/ProgramChangRequstDetailSelect.do";
    	}
        return sLocationUrl; 
    } 

    /**
     * 프로그램변경 요청에 대한 처리 사항을 조회한다. 
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "admin/prm/ProgramChangeRequstProcess" 
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경요청처리",order = 1113 ,gid = 60)
    @RequestMapping(value="/admin/prm/ProgramChangeRequstProcessListSelect.do")
    public String selectProgrmChangeRequstProcessList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception { 
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
     	 // 내역 조회
     	 /** EgovPropertyService.sample */
     	 searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
     	 searchVO.setPageSize(propertiesService.getInt("pageSize"));

     	 /** pageing */
     	 PaginationInfo paginationInfo = new PaginationInfo();
 		 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
 		 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
 		 paginationInfo.setPageSize(searchVO.getPageSize());
 		
 		 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
 		 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
 		 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
     	
         List list_changerequst = progrmManageService.selectChangeRequstProcessList(searchVO);
         model.addAttribute("list_changerequst", list_changerequst);

         int totCnt = progrmManageService.selectChangeRequstProcessListTotCnt(searchVO);
  		 paginationInfo.setTotalRecordCount(totCnt);
         model.addAttribute("paginationInfo", paginationInfo);
         
         return "bbm/admin/prm/ProgramChangeRequstProcess";
    }
    
    /**
     * 프로그램변경 요청에 대한 처리 사항을 상세조회한다. 
     * @param progrmManageDtlVO ProgrmManageDtlVO
     * @return 출력페이지정보 "admin/prm/ProgramChangRequstProcessDetailSelectUpdt" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChangRequstProcessDetailSelect.do")
    public String selectProgrmChangRequstProcess(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
         if(progrmManageDtlVO.getProgrmFileNm()==null){
	    	 String _FileNm = progrmManageDtlVO.getTmpProgrmNm();    	
	         progrmManageDtlVO.setProgrmFileNm(_FileNm);
	         int _Tmp_no = progrmManageDtlVO.getTmpRqesterNo();    	
	         progrmManageDtlVO.setRqesterNo(_Tmp_no);
         }
         ProgrmManageDtlVO resultVO = progrmManageService.selectProgrmChangeRequst(progrmManageDtlVO);
         if(resultVO.getProcessDe() != null){
        	 resultVO.setProcessDe(resultVO.getProcessDe().trim());//2011.08.22
         }
         
         if(resultVO.getOpetrId()== null){
	     	 LoginVO user = 
				(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	         resultVO.setOpetrId(user.getId());
         }
         model.addAttribute("progrmManageDtlVO", resultVO);
         return "bbm/admin/prm/ProgramChangRequstProcessDetailSelectUpdt";
    }    

    /**
     * 프로그램변경요청처리 내용을 수정 한다. 
     * @param progrmManageDtlVO ProgrmManageDtlVO 
     * @return 출력페이지정보 "forward:/admin/prm/ProgramChangeRequstProcessListSelect.do" 
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChangRequstProcessDetailSelectUpdt.do")
    public String updateProgrmChangRequstProcess(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO, 
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception { 
        String sLocationUrl = null;
    	boolean result = true;
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
		
        beanValidator.validate(progrmManageDtlVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/admin/prm/ProgramChangRequstProcessDetailSelect.do";
			return sLocationUrl;
		}

    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	if(progrmManageDtlVO.getOpetrId().equals(user.getId())){
			if(progrmManageDtlVO.getChangerqesterCn()==null || progrmManageDtlVO.getChangerqesterCn().equals("")){progrmManageDtlVO.setChangerqesterCn(" ");}
			if(progrmManageDtlVO.getRqesterProcessCn()==null || progrmManageDtlVO.getRqesterProcessCn().equals("")){progrmManageDtlVO.setRqesterProcessCn(" ");}
	        progrmManageService.updateProgrmChangeRequstProcess(progrmManageDtlVO);
	        model.addAttribute("resultMsg", egovMessageSource.getMessage("success.common.update"));
	        
	        ProgrmManageDtlVO vo = new ProgrmManageDtlVO();
	        vo = progrmManageService.selectRqesterEmail(progrmManageDtlVO);
	        String sTemp = null;
	        if(progrmManageDtlVO.getProcessSttus().equals("A")){
	        	sTemp = "신청중";
	        }else if(progrmManageDtlVO.getProcessSttus().equals("P")){
	        	sTemp = "진행중";	
	        }else if(progrmManageDtlVO.getProcessSttus().equals("R")){
	        	sTemp = "반려";
	        }else if(progrmManageDtlVO.getProcessSttus().equals("C")){
	        	sTemp = "처리완료";
	        }
	    	// 프로그램 변경요청 사항을 이메일로  발송한다.(메일연동솔루션 활용)
	    	SndngMailVO sndngMailVO = new SndngMailVO();
	    	sndngMailVO.setDsptchPerson(user.getId());
	    	sndngMailVO.setRecptnPerson(vo.getTmpEmail());
	    	sndngMailVO.setSj("프로그램변경요청  처리.");
	    	sndngMailVO.setEmailCn("프로그램 변경요청 사항이  "+sTemp+"(으)로 처리 되었습니다.");
	    	sndngMailVO.setAtchFileId(null); 
	    	result = sndngMailRegistService.insertSndngMail(sndngMailVO); 
	        sLocationUrl = "forward:/admin/prm/ProgramChangeRequstProcessListSelect.do";
    	}else{
    		model.addAttribute("resultMsg", "수정이 실패하였습니다. 변경요청처리 수정은 변경처리해당 담당자만 처리가능합니다.");
            progrmManageDtlVO.setTmpProgrmNm(progrmManageDtlVO.getProgrmFileNm());    	
            progrmManageDtlVO.setTmpRqesterNo(progrmManageDtlVO.getRqesterNo());   
			sLocationUrl = "forward:/admin/prm/ProgramChangRequstProcessDetailSelect.do";
    	}
		return sLocationUrl; 
    }     

    /**
     * 프로그램변경요청처리를 삭제 한다. 
     * @param progrmManageDtlVO  ProgrmManageDtlVO
     * @return 출력페이지정보 "forward:/admin/prm/ProgramChangeRequstProcessListSelect.do" 
     * @exception Exception
     */
    /*프로그램변경요청처리 삭제*/
    @RequestMapping(value="/admin/prm/ProgramChangRequstProcessDelete.do")
    public String deleteProgrmChangRequstProcess(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO, 
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
         progrmManageService.deleteProgrmChangeRequst(progrmManageDtlVO);

         return "forward:/admin/prm/ProgramChangeRequstProcessListSelect.do";
    }
    
    /**
     * 프로그램변경이력리스트를 조회한다. 
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "admin/prm/ProgramChgHst"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경이력",order = 1114 ,gid = 60)
    @RequestMapping(value="/admin/prm/ProgramChgHstListSelect.do")
    public String selectProgrmChgHstList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception { 
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
     	 // 내역 조회
     	 /** EgovPropertyService.sample */
     	 searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
     	 searchVO.setPageSize(propertiesService.getInt("pageSize"));

     	 /** pageing */
     	 PaginationInfo paginationInfo = new PaginationInfo();
 		 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
 		 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
 		 paginationInfo.setPageSize(searchVO.getPageSize());
 		
 		 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
 		 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
 		 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
     	
         List list_changerequst = progrmManageService.selectProgrmChangeRequstList(searchVO);
         model.addAttribute("list_changerequst", list_changerequst);

         int totCnt = progrmManageService.selectProgrmChangeRequstListTotCnt(searchVO);
  		 paginationInfo.setTotalRecordCount(totCnt);
         model.addAttribute("paginationInfo", paginationInfo);
         
         return "bbm/admin/prm/ProgramChgHst";
    } 

    /*프로그램변경이력상세조회*/ 
    /**
     * 프로그램변경이력을 상세조회한다. 
     * @param progrmManageDtlVO ProgrmManageDtlVO
     * @return 출력페이지정보 "admin/prm/ProgramChgHstDetail"
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramChgHstListDetailSelect.do")
    public String selectProgramChgHstListDetail(
    		@ModelAttribute("progrmManageDtlVO") ProgrmManageDtlVO progrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 /*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	 }*/
         String _FileNm = progrmManageDtlVO.getTmpProgrmNm();    	
         progrmManageDtlVO.setProgrmFileNm(_FileNm);
         int _tmp_no = progrmManageDtlVO.getTmpRqesterNo();    	
         progrmManageDtlVO.setRqesterNo(_tmp_no);

         ProgrmManageDtlVO resultVO = progrmManageService.selectProgrmChangeRequst(progrmManageDtlVO);
         model.addAttribute("resultVO", resultVO);
         return "bbm/admin/prm/ProgramChgHstDetail";
    }

    /**
     * 프로그램파일명을 조회한다. 
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovFileNmSearch"
     * @exception Exception
     */
    @RequestMapping(value="/admin/prm/ProgramListSearch.do")
    public String selectProgrmListSearch(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO, 
    		ModelMap model)
            throws Exception { 
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "bbm/uat/uia/LoginUsr";
    	}*/
    	// 내역 조회
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
        List list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);

        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
      	return "egovframework/com/sym/prm/EgovFileNmSearch";

    }
    
}