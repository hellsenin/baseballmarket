package com.bbm.adm.mnu.mpm.web;

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

import com.bbm.adm.mnu.mpm.service.EgovMenuManageService;
import com.bbm.adm.mnu.mpm.service.MenuManageVO;
import com.bbm.adm.prm.service.ProgrmManageService;
import com.bbm.cmm.ComDefaultVO;
import com.bbm.cmm.EgovMessageSource;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
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
 *	 2011.07.01	 서준식	   	메뉴정보 삭제시 참조되고 있는 하위 메뉴가 있는지 체크하는 로직 추가
 *	 2011.07.27	 서준식	   	deleteMenuManageList() 메서드에서 메뉴 멀티 삭제 버그 수정
 *	 2011.08.26	 정진오		IncludedInfo annotation 추가
 *	 2011.10.07	 이기하		보안취약점 수정(파일 업로드시 엑셀파일만 가능하도록 추가)
 * </pre>
 */

@Controller
public class EgovMenuManageController {

	protected Log log = LogFactory.getLog(this.getClass());

	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** EgovMenuManageService */
	@Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    /** EgovMenuManageService */
	@Resource(name = "progrmManageService")
	private ProgrmManageService progrmManageService;

    /** EgovFileMngService */
//	@Resource(name="EgovFileMngService")
//	private EgovFileMngService fileMngService;

    /** EgovFileMngUtil */
//	@Resource(name="EgovFileMngUtil")
//	private EgovFileMngUtil fileUtil;

//	@Resource(name = "excelZipService")
//    private EgovExcelService excelZipService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;


    /**
     * 메뉴정보목록을 상세화면 호출 및 상세조회한다.
     * @param req_menuNo  String
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuManageListDetailSelect.do")
    public String selectMenuManage(
    		@RequestParam("req_menuNo") String req_menuNo ,
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	/*
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
    	searchVO.setSearchKeyword(req_menuNo);

    	MenuManageVO resultVO = menuManageService.selectMenuManage(searchVO);
        model.addAttribute("menuManageVO", resultVO);

        return "egovframework/com/sym/mnu/mpm/EgovMenuDetailSelectUpdt";
    }

    /**
     * 메뉴목록 리스트조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuManage"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴관리리스트", order = 1091 ,gid = 60)
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuManageSelect.do")
    public String selectMenuManageList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
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

		List list_menumanage = menuManageService.selectMenuManageList(searchVO);
		model.addAttribute("list_menumanage", list_menumanage);

        int totCnt = menuManageService.selectMenuManageListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "egovframework/com/sym/mnu/mpm/EgovMenuManage";
    }

    /**
     * 메뉴목록 멀티 삭제한다.
     * @param checkedMenuNoForDel  String
     * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/sym/mnu/mpm/EgovMenuManageListDelete.do")
    public String deleteMenuManageList(
            @RequestParam("checkedMenuNoForDel") String checkedMenuNoForDel ,
            @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
            ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
		String sLocationUrl = null;
    	String resultMsg    = "";

		String [] delMenuNo = checkedMenuNoForDel.split(",");
		menuManageVO.setMenuNo(Integer.parseInt(delMenuNo[0]));

		if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
    		sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}else if (delMenuNo == null || (delMenuNo.length ==0)){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
    		sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}else{
			menuManageService.deleteMenuManageList(checkedMenuNoForDel);
			resultMsg = egovMessageSource.getMessage("success.common.delete");
			sLocationUrl ="forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
        return sLocationUrl;
    }

    /**
     * 메뉴정보를 등록화면으로 이동 및 등록 한다.
     * @param menuManageVO    MenuManageVO
     * @param commandMap      Map
     * @return 출력페이지정보 등록화면 호출시 "sym/mnu/mpm/EgovMenuRegist",
     *         출력페이지정보 등록처리시 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuRegistInsert.do")
    public String insertMenuManage(
    		Map commandMap,
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
		String sLocationUrl = null;
    	String resultMsg    = "";
        // 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
		if(sCmd.equals("insert")){
            beanValidator.validate(menuManageVO, bindingResult);
    		if (bindingResult.hasErrors()){
    			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuRegist";
    			return sLocationUrl;
    		}
    		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
    			ComDefaultVO searchVO = new ComDefaultVO();
    			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
    			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    	    		resultMsg = egovMessageSource.getMessage("fail.common.insert");
    		        sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuRegist";
    			}else{
    	        	menuManageService.insertMenuManage(menuManageVO);
            		resultMsg = egovMessageSource.getMessage("success.common.insert");
    		        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
    			}
    		}else{
        		resultMsg = egovMessageSource.getMessage("common.isExist.msg");
        		sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuRegist";
    		}
    		model.addAttribute("resultMsg", resultMsg);
		}else{
            sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuRegist";
        }
		return sLocationUrl;
    }

    /**
     * 메뉴정보를 수정 한다.
     * @param menuManageVO  MenuManageVO
     * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuDetailSelectUpdt.do")
    public String updateMenuManage(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageListDetailSelect.do";
			return sLocationUrl;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    		resultMsg = egovMessageSource.getMessage("fail.common.update");
	        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageListDetailSelect.do";
		}else{
			menuManageService.updateMenuManage(menuManageVO);
	    	resultMsg = egovMessageSource.getMessage("success.common.update");
	       	sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 메뉴정보를 삭제 한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "forward:/sym/mnu/mpm/EgovMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuManageDelete.do")
    public String deleteMenuManage(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
    	if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
    		model.addAttribute("resultMsg", resultMsg);
    		return "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
		}

    	menuManageService.deleteMenuManage(menuManageVO);
    	resultMsg = egovMessageSource.getMessage("success.common.delete");
    	String _MenuNm = "%";
    	menuManageVO.setMenuNm(_MenuNm);
    	model.addAttribute("resultMsg", resultMsg);
      	return "forward:/sym/mnu/mpm/EgovMenuManageSelect.do";
    }

    /**
     * 메뉴리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴리스트관리", order = 1090 ,gid = 60)
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListSelect.do")
    public String selectMenuList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/
    	List list_menulist = menuManageService.selectMenuList();
    	resultMsg = egovMessageSource.getMessage("success.common.select");
        model.addAttribute("list_menulist", list_menulist);
        model.addAttribute("resultMsg", resultMsg);
      	return  "egovframework/com/sym/mnu/mpm/EgovMenuList";
    }

    /**
     * 메뉴리스트의 메뉴정보를 등록한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListInsert.do")
    public String insertMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuList";
			return sLocationUrl;
		}

		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
			ComDefaultVO searchVO = new ComDefaultVO();
			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
	    		resultMsg = egovMessageSource.getMessage("fail.common.insert");
		        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
			}else{
	        	menuManageService.insertMenuManage(menuManageVO);
	    		resultMsg = egovMessageSource.getMessage("success.common.insert");
		        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
			}
		}else{
    		resultMsg = egovMessageSource.getMessage("common.isExist.msg");
    		sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 수정한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListUpdt.do")
    public String updateMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
			return sLocationUrl;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    		resultMsg = egovMessageSource.getMessage("fail.common.update");
	        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
		}else{
			menuManageService.updateMenuManage(menuManageVO);
			resultMsg = egovMessageSource.getMessage("success.common.update");
	        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 삭제한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListDelete.do")
    public String deleteMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuList";
			return sLocationUrl;
		}
		menuManageService.deleteMenuManage(menuManageVO);
		resultMsg = egovMessageSource.getMessage("success.common.delete");
        sLocationUrl = "forward:/sym/mnu/mpm/EgovMenuListSelect.do";
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 이동 메뉴목록을 조회한다.
     * @param searchVO  ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuMvmn"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListSelectMvmn.do")
    public String selectMenuListMvmn(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	/*Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}*/

    	List list_menulist = menuManageService.selectMenuList();
        model.addAttribute("list_menulist", list_menulist);
      	return  "egovframework/com/sym/mnu/mpm/EgovMenuMvmn";
    }


}