package com.bbm.adm.mnu.mcm.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.cmm.ComDefaultVO;
import com.bbm.cmm.EgovMessageSource;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.service.EgovProperties;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.adm.mnu.mcm.service.MenuCreateManageService;
import com.bbm.adm.mnu.mcm.service.MenuCreatVO;
import com.bbm.adm.mnu.mcm.service.MenuSiteMapVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
 * 
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 * 	 2011.07.29	 서준식          사이트맵 저장경로 수정
 *	2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */

@Controller
public class MenuCreateManageController {

	protected Log log = LogFactory.getLog(this.getClass());
	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** MenuManageService */
	@Resource(name = "meunCreateManageService")
	private MenuCreateManageService menuCreateManageService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/*********** 메뉴 생성 관리 ***************/

	/**
	 * *메뉴생성목록을 조회한다.
	 * 
	 * @param searchVO
	 *            ComDefaultVO
	 * @return 출력페이지정보 "sym/mnu/mcm/MenuCreatManage"
	 * @exception Exception
	 */
	@IncludedInfo(name="메뉴생성관리", order = 1100 ,gid = 60)
	@RequestMapping(value = "/sym/mnu/mcm/MenuCreatManageSelect.do")
	public String selectMenuCreatManagList(
			@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model)
			throws Exception {
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
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
		if (searchVO.getSearchKeyword() != null
				&& !searchVO.getSearchKeyword().equals("")) {
			int IDcnt = menuCreateManageService.selectUsrByPk(searchVO);
			if (IDcnt == 0) {
				resultMsg = egovMessageSource.getMessage("info.nodata.msg");
			} else {
				/* AuthorCode 검색 */
				MenuCreatVO vo = new MenuCreatVO();
				vo = menuCreateManageService.selectAuthorByUsr(searchVO);
				searchVO.setSearchKeyword(vo.getAuthorCode());
			}
		}
		List list_menumanage = menuCreateManageService
				.selectMenuCreatManagList(searchVO);
		model.addAttribute("list_menumanage", list_menumanage);

		int totCnt = menuCreateManageService
				.selectMenuCreatManagTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultMsg", resultMsg);
		return "bbm/admin/mnu/mcm/MenuCreatManage";
	}

	/* 메뉴생성 세부조회 */
	/**
	 * 메뉴생성 세부화면을 조회한다.
	 * 
	 * @param menuCreatVO
	 *            MenuCreatVO
	 * @return 출력페이지정보 "sym/mnu/mcm/MenuCreat"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mcm/MenuCreatSelect.do")
	public String selectMenuCreatList(
			@ModelAttribute("menuCreatVO") MenuCreatVO menuCreatVO,
			ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
			return "bbm/uat/uia/LoginUsr";
		}*/
		
		List list_menulist = menuCreateManageService
				.selectMenuCreatList(menuCreatVO);
		model.addAttribute("list_menulist", list_menulist);
		model.addAttribute("resultVO", menuCreatVO);

		return "bbm/admin/mnu/mcm/MenuCreat";
	}

	/**
	 * 메뉴생성처리 및 메뉴생성내역을 등록한다.
	 * 
	 * @param checkedAuthorForInsert
	 *            String
	 * @param checkedMenuNoForInsert
	 *            String
	 * @return 출력페이지정보 등록처리시 "forward:/sym/mnu/mcm/MenuCreatSelect.do"
	 * @exception Exception
	 */
	@RequestMapping("/sym/mnu/mcm/MenuCreatInsert.do")
	public String insertMenuCreatList(
			@RequestParam("checkedAuthorForInsert") String checkedAuthorForInsert,
			@RequestParam("checkedMenuNoForInsert") String checkedMenuNoForInsert,
			@ModelAttribute("menuCreatVO") MenuCreatVO menuCreatVO,
			ModelMap model) throws Exception {
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
			return "bbm/uat/uia/LoginUsr";
		}*/
		String[] insertMenuNo = checkedMenuNoForInsert.split(",");
		if (insertMenuNo == null || (insertMenuNo.length == 0)) {
			resultMsg = egovMessageSource.getMessage("fail.common.insert");
		} else {
			menuCreateManageService.insertMenuCreatList(checkedAuthorForInsert,
					checkedMenuNoForInsert);
			resultMsg = egovMessageSource.getMessage("success.common.insert");
		}
		model.addAttribute("resultMsg", resultMsg);
		return "forward:/sym/mnu/mcm/MenuCreatSelect.do";
	}

	/* 메뉴사이트맵 생성조회 */
	/**
	 * 메뉴사이트맵을 생성할 내용을 조회한다.
	 * 
	 * @param menuSiteMapVO
	 *            MenuSiteMapVO
	 * @return 출력페이지정보 등록처리시 "sym/mnu/mcm/MenuCreatSiteMap"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mcm/MenuCreatSiteMapSelect.do")
	public String selectMenuCreatSiteMap(
			@ModelAttribute("menuSiteMapVO") MenuSiteMapVO menuSiteMapVO,
			ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
			return "bbm/uat/uia/LoginUsr";
		}*/
		List list_menulist = menuCreateManageService
				.selectMenuCreatSiteMapList(menuSiteMapVO);
		model.addAttribute("list_menulist", list_menulist);
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		menuSiteMapVO.setCreatPersonId(user.getId());
		model.addAttribute("resultVO", menuSiteMapVO);
		return "bbm/admin/mnu/mcm/MenuCreatSiteMap";
	}

	/**
	 * 메뉴사이트맵 생성처리 및 사이트맵을 등록한다.
	 * 
	 * @param menuSiteMapVO
	 *            MenuSiteMapVO
	 * @param valueHtml
	 *            String
	 * @return 출력페이지정보 "sym/mnu/mcm/MenuCreatSiteMap"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mcm/MenuCreatSiteMapInsert.do")
	public String selectMenuCreatSiteMapInsert(
			@ModelAttribute("menuSiteMapVO") MenuSiteMapVO menuSiteMapVO,
			@RequestParam("valueHtml") String valueHtml, ModelMap model)
			throws Exception {
		boolean chkCreat = false;
		String resultMsg = "";
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
			return "bbm/uat/uia/LoginUsr";
		}*/

		
		menuSiteMapVO.setTmpRootPath(EgovProperties.RELATIVE_PATH_PREFIX
				+ ".." + System.getProperty("file.separator") + ".."
				+ System.getProperty("file.separator") + "..");
		menuSiteMapVO.setBndeFilePath("/html/bbm/admin/mnu/mcm/");

		/*
		 * 사이트맵 파일 생성 위치 지정 if ("WINDOWS".equals(Globals.OS_TYPE)) {
		 * menuSiteMapVO
		 * .setTmp_rootPath("D:/egovframework/workspace/egovcmm/src/main/webapp"
		 * ); }else{menuSiteMapVO.setTmp_rootPath(
		 * "/product/jeus/webhome/was_com/egovframework-com-1_0/egovframework-com-1_0_war___"
		 * ); }
		 */
		chkCreat = menuCreateManageService.creatSiteMap(menuSiteMapVO,
				valueHtml);
		if (!chkCreat) {
			resultMsg = egovMessageSource.getMessage("fail.common.insert");
		} else {
			resultMsg = egovMessageSource.getMessage("success.common.insert");
		}
		List list_menulist = menuCreateManageService
				.selectMenuCreatSiteMapList(menuSiteMapVO);
		model.addAttribute("list_menulist", list_menulist);
		model.addAttribute("resultVO", menuSiteMapVO);
		model.addAttribute("resultMsg", resultMsg);
		return "bbm/admin/mnu/mcm/MenuCreatSiteMap";
	}

	/* 메뉴사이트맵 생성조회 */
	/**
	 * 메뉴사이트맵을 생성할 내용을 조회한다.
	 * 
	 * @param menuSiteMapVO
	 *            MenuSiteMapVO
	 * @return 출력페이지정보 등록처리시 "sym/mnu/mcm/MenuCreatSiteMap"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/mnu/mcm/SiteMap.do")
	public String selectSiteMap(
			@ModelAttribute("menuCreatVO") MenuSiteMapVO menuSiteMapVO,
			ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		/*
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource
					.getMessage("fail.common.login"));
			return "bbm/uat/uia/LoginUsr";
		}*/

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		menuSiteMapVO.setCreatPersonId(user.getId());

		List list_menulist = menuCreateManageService
				.selectSiteMapByUser(menuSiteMapVO);
		model.addAttribute("list_menulist", list_menulist);

		model.addAttribute("resultVO", menuSiteMapVO);
		return "bbm/admin/mnu/mcm/SiteMap";
	}

}
