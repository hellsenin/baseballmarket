package com.bbm.adm.mnu.mpm.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.adm.mnu.mpm.service.MenuManageService;
import com.bbm.adm.mnu.mpm.service.MenuManageVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/** 
 * 메인메뉴 해당링크 처리를 하는 비즈니스 구현 클래스
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
 *   2011.09.07  서준식          사용자 구분 오류 수정
 * </pre>
 */

@Controller
public class MainMenuManageController {
	
	protected Log log = LogFactory.getLog(this.getClass());

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** MenuManageService */
	@Resource(name = "meunManageService")
    private MenuManageService menuManageService;

    /** EgovFileMngService */
	//@Resource(name="EgovFileMngService")
	//private EgovFileMngService fileMngService;	
	
    /** EgovFileMngUtil */
	//@Resource(name="EgovFileMngUtil")
	//private EgovFileMngUtil fileUtil;
	
    /*### 메인작업 ###*/
    /*Main Index 조회*/
    /**
     * Main메뉴의 Index를 조회한다. 
     * @param menuNo  String
     * @param chkURL  String
     * @return 출력페이지정보 "menu_index"
     * @exception Exception
     */
    @RequestMapping(value="/admin/mnu/mpm/MainMenuIndex.do")
    public String selectMainMenuIndex(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		@RequestParam("menuNo") String menuNo,
    		@RequestParam("chkURL") String chkURL,
    		ModelMap model)
            throws Exception { 

    	int iMenuNo = Integer.parseInt(menuNo);
    	menuManageVO.setMenuNo(iMenuNo);
    	//menuManageVO.setTempValue(chkURL);
        model.addAttribute("resultVO", menuManageVO);
        
        return "egovframework/com/menu_index";  
    }
    
    /**
     * Head메뉴를 조회한다. 
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "main_headG", "main_head"
     * @exception Exception
     */
    @RequestMapping(value="/admin/mnu/mpm/MainMenu.do")
    public String selectMainMenu(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception { 
    	
    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
    	menuManageVO.setTmpId(user.getId());
    	menuManageVO.setTmpPassword(user.getPassword());
    	menuManageVO.setTmpUserSe(user.getUserSe());
    	menuManageVO.setTmpName(user.getName());
    	menuManageVO.setTmpEmail(user.getEmail());
    	//menuManageVO.setTmpOrgnztId(user.getOrgnztId());
    	menuManageVO.setTmpUniqId(user.getUniqId());

    	List list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
        model.addAttribute("list_headmenu", list_headmenu);
		if (!user.getId().equals("")) {
        	// 메인 페이지 이동
			// G일반 / E기업 / U업무
			if (user.getUserSe().equals("USR")) {
	    		return "egovframework/com/MainView";
	    	} else {
	    		return "egovframework/com/MainViewG";
        	}
        } else {
        	// 오류 페이지 이동
        	return "egovframework/com/cmm/error/egovError";
        }
    }

    /**
     * Head메뉴를 조회한다. 
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "main_headG", "main_head"
     * @exception Exception
     */
    @RequestMapping(value="/admin/mnu/mpm/MainMenuHead.do")
    public String selectMainMenuHead(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception { 
    	
    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
    	menuManageVO.setTmpId(user.getId());
    	menuManageVO.setTmpPassword(user.getPassword());
    	menuManageVO.setTmpUserSe(user.getUserSe());
    	menuManageVO.setTmpName(user.getName());
    	menuManageVO.setTmpEmail(user.getEmail());
    	//menuManageVO.setTmpOrgnztId(user.getOrgnztId());
    	menuManageVO.setTmpUniqId(user.getUniqId());

    	List list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
        model.addAttribute("list_headmenu", list_headmenu);
		if (!user.getId().equals("")) {
        	// 메인 페이지 이동
			// G일반 / E기업 / U업무
			if (user.getUserSe().equals("USR")) {
        		return "egovframework/com/main_head"; //"MainViewG"; 일반사용자
        	} else {
        		return "egovframework/com/main_headG"; 
        	}
        } else {
        	// 오류 페이지 이동
        	return "egovframework/com/cmm/error/egovError";
        }
    }
    
    
    /**
     * 좌측메뉴를 조회한다. 
     * @param menuManageVO MenuManageVO
     * @param vStartP      String
     * @return 출력페이지정보 "main_left"
     * @exception Exception
     */
    @RequestMapping(value="/admin/mnu/mpm/MainMenuLeft.do")
    public String selectMainMenuLeft(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		@RequestParam("vStartP") String vStartP,
    		ModelMap model)
            throws Exception { 
    	int iMenuNo = Integer.parseInt(vStartP);
    	menuManageVO.setTempInt(iMenuNo);
        model.addAttribute("resultVO", menuManageVO);

    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
    	menuManageVO.setTmpId(user.getId());
    	menuManageVO.setTmpPassword(user.getPassword());
    	menuManageVO.setTmpUserSe(user.getUserSe());
    	menuManageVO.setTmpName(user.getName());
    	menuManageVO.setTmpEmail(user.getEmail());
    	//menuManageVO.setTmpOrgnztId(user.getOrgnztId());
    	menuManageVO.setTmpUniqId(user.getUniqId());
    	
    	List list_menulist = menuManageService.selectMainMenuLeft(menuManageVO);
        model.addAttribute("list_menulist", list_menulist);
      	return "egovframework/com/main_left"; 
    }

    /**
     * 우측화면을 조회한다. 
     * @param menuManageVO MenuManageVO
     * @param vStartP      String
     * @return 출력페이지정보 해당URL
     * @exception Exception
     */
    /*Right Menu 조회*/
    @RequestMapping(value="/admin/mnu/mpm/MainMenuRight.do")
    public String selectMainMenuRight(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		@RequestParam("vStartP") String vStartP,
    		ModelMap model)
            throws Exception { 
    	int iMenuNo = Integer.parseInt(vStartP);
    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

    	String forwardURL = null;
    	forwardURL = menuManageService.selectLastMenuURL(iMenuNo, user.getUniqId());
      	return "forward:"+forwardURL; 
    }

    /**
     * HOME 메인화면 조회한다. 
     * @param menuManageVO  MenuManageVO
     * @return 출력페이지정보 "MainView","MainViewG"
     * @exception Exception
     */
    @IncludedInfo(name="포털(예제) 메인화면", order = 1, gid = 0)
    @RequestMapping(value="/admin/mnu/mpm/MainMenuHome.do")
    public String selectMainMenuHome(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception { 

    	LoginVO user = 
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
    	menuManageVO.setTmpId(user.getId());
    	menuManageVO.setTmpPassword(user.getPassword());
    	menuManageVO.setTmpUserSe(user.getUserSe());
    	menuManageVO.setTmpName(user.getName());
    	menuManageVO.setTmpEmail(user.getEmail());
    	//menuManageVO.setTmpOrgnztId(user.getOrgnztId());
    	menuManageVO.setTmpUniqId(user.getUniqId());
    	
		List list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);
		
		log.debug("## selectMainMenuHome ## getSUserSe 1: "+user.getUserSe());
		log.debug("## selectMainMenuHome ## getSUserId 2: "+user.getId());
		log.debug("## selectMainMenuHome ## getUniqId  2: "+user.getUniqId());
		
		if (!user.getId().equals("")) {
        	// 메인 페이지 이동
			// G일반 / E기업 / U업무
        	if (user.getUserSe().equals("GNR")) {//2011.09.07
        		return "egovframework/com/MainViewG"; //"MainViewG"; 일반사용자
        		
        	}else if(user.getUserSe().equals("USR")){//2011.09.07
        		//return "egovframework/com/EgovIndvdlpgeDetail";
        		return "egovframework/com/MainView";
        	}
        	else {
        		//return "egovframework/com/MainView";//1차 사업 메인화면 
        		return "egovframework/com/MainView2";//2차 사업 메인화면
        	}
        } else {
        	// 오류 페이지 이동
        	return "egovframework/com/cmm/error/egovError";
        }
    }
}