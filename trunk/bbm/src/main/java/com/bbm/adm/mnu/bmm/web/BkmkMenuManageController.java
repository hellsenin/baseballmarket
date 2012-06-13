package com.bbm.adm.mnu.bmm.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.adm.mnu.bmm.service.BkmkMenuManage;
import com.bbm.adm.mnu.bmm.service.BkmkMenuManageVO;
import com.bbm.adm.mnu.bmm.service.BkmkMenuManageservice;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 바로가기메뉴관리 정보를 관리하기 위한 컨트롤러 클래스
 * @author 공통컴포넌트팀 윤성록
 * @since 2009.09.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.9.25  윤성록          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * </pre>
 */

@Controller
public class BkmkMenuManageController {
          
    @Resource(name = "bkmkMenuManageservice")
    private BkmkMenuManageservice bkmkMenuManageService;    

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    private static final Logger LOG = Logger.getLogger(BkmkMenuManageController.class.getName());          
      
    /**
     * 바로가기메뉴관리 정보에 대한 목록을 조회한다.
     *      
     * @param BkmkMenuManageVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */    
    @IncludedInfo(name="바로가기메뉴관리", order = 1110 ,gid = 60)
    @RequestMapping("/sym/mnu/bmm/selectBkmkMenuManageList.do")
    public String selectBkmkMenuManageList(@ModelAttribute("searchVO") BkmkMenuManageVO bkmkMenuManageVO, SessionStatus status, ModelMap model) throws Exception {
           
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        
        @SuppressWarnings("unused")
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
        if(!isAuthenticated) {
            return "bbm/uat/uia/LoginUsr";
        } 

        bkmkMenuManageVO.setPageUnit(propertyService.getInt("pageUnit"));
        bkmkMenuManageVO.setPageSize(propertyService.getInt("pageSize"));
        bkmkMenuManageVO.setUserId(user.getId());

        PaginationInfo paginationInfo = new PaginationInfo();
            
        paginationInfo.setCurrentPageNo(bkmkMenuManageVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(bkmkMenuManageVO.getPageUnit());
        paginationInfo.setPageSize(bkmkMenuManageVO.getPageSize());        
          
        bkmkMenuManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        bkmkMenuManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
        bkmkMenuManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        bkmkMenuManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        bkmkMenuManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
        bkmkMenuManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
         
        Map<String, Object> map = bkmkMenuManageService.selectBkmkMenuManageList(bkmkMenuManageVO);
            
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));
            
        paginationInfo.setTotalRecordCount(totCnt);
            
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("uniqId", user.getUniqId());
        model.addAttribute("paginationInfo", paginationInfo);
       
        return "bbm/admin/mnu/bmm/BkmkMenuManageList";
        
    }
    
    /**
     * 바로가기메뉴관리 정보를 삭제한다.
     *      
     * @param checkMenuIds
     * @param bkmkMenuManageVO
     * @param model
     * @return
     * @throws Exception
     */    
    @RequestMapping("/sym/mnu/bmm/BkmkMenuManageDelete.do")
    public String deleteMenuManageList(
            @RequestParam("checkMenuIds") String checkMenuIds ,
            @ModelAttribute("bkmkMenuManageVO") BkmkMenuManageVO bkmkMenuManageVO, 
            ModelMap model)
            throws Exception {        
        
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        
        @SuppressWarnings("unused")
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        
        if(!isAuthenticated) {
            //    model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "bbm/uat/uia/LoginUsr";
        }
               
        String [] temp = checkMenuIds.split(",");
        
        for(int i =0; i < temp.length; i++){
            BkmkMenuManage bkmk = new BkmkMenuManage();
            bkmk.setMenuId(temp[i]);
            bkmk.setUserId(user.getId());
            bkmkMenuManageService.deleteBkmkMenuManage(bkmk);
        }
                
        return "forward:/sym/mnu/bmm/selectBkmkMenuManageList.do";          
    }
    
    /**
     * 바로가기메뉴관리 등록화면으로 이동한다.
     *      
     * @param BkmkMenuManage
     * @param status
     * @param model
     * @return
     * @throws Exception
     */    
    @RequestMapping("/sym/mnu/bmm/addBkmkInf.do")
    public String addBkmkMenuManage( @ModelAttribute("bkmkMenuManage") BkmkMenuManage bkmkMenuManage, SessionStatus status, ModelMap model) throws Exception {
               
        if(!bkmkMenuManage.getMenuId().equals("")){          
           
            bkmkMenuManage.setProgrmStrePath(bkmkMenuManageService.selectUrl(bkmkMenuManage));
        }
        
        return "bbm/admin/mnu/bmm/BkmkMenuManageRegist";
    }   
    
    /**
     * 메뉴정보 목록팝업 화면으로 이동한다.
     *      
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */ 
    @RequestMapping("/sym/mnu/bmm/openPopup.do")
    public String openPopupWindow(Map<String, Object> commandMap, ModelMap model) throws Exception {
          
        String requestUrl = (String)commandMap.get("requestUrl");
        requestUrl = requestUrl.replaceAll("&", "&amp;");
        String width = (String)commandMap.get("width");
        String height = (String)commandMap.get("height");
        model.addAttribute("requestUrl", requestUrl + "?" + "&amp;PopFlag=Y");        
        model.addAttribute("width", width);
        model.addAttribute("height", height);           
       
        return "bbm/admin/mnu/bmm/EgovModalPopupFrame";
    }   
    
    /**
     * 메뉴정보 목록을 조회한다.
     *      
     * @param BkmkMenuManageVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */ 
    @RequestMapping("/sym/mnu/bmm/selectMenuList.do") 
    public String selectMenuList(@ModelAttribute("bkmkMenuManageVO") BkmkMenuManageVO bkmkMenuManageVO, Map<String, Object> commandMap, ModelMap model) throws Exception {
        String popFlag = (String)commandMap.get("PopFlag");
             
        
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        
        bkmkMenuManageVO.setPageUnit(propertyService.getInt("pageUnit"));
        bkmkMenuManageVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();
      
        paginationInfo.setCurrentPageNo(bkmkMenuManageVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(bkmkMenuManageVO.getPageUnit());
        paginationInfo.setPageSize(bkmkMenuManageVO.getPageSize());

        
        bkmkMenuManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        bkmkMenuManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
        bkmkMenuManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        bkmkMenuManageVO.setUserId(user.getId());
  
        Map<String, Object> map = bkmkMenuManageService.selectMenuList(bkmkMenuManageVO); 
        
        int totCnt = Integer.parseInt((String)map.get("resultCnt"));      
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);
                
        return "bbm/admin/mnu/bmm/BkmkMenuPopup";
    }
  
    /**
     * 바로가기메뉴관리 정보를 등록한다.
     *      
     * @param BkmkMenuManage
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/sym/mnu/bmm/registBkmkInf.do") 
    public String registBkmkInf(@ModelAttribute("bkmkMenuManage") BkmkMenuManage bkmkMenuManage, 
            BindingResult bindingResult, SessionStatus status, ModelMap model) throws Exception {
        
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();   
        
        if(!isAuthenticated) {
            return "bbm/uat/uia/LoginUsr";
        } 
        
        beanValidator.validate(bkmkMenuManage, bindingResult);
        if (bindingResult.hasErrors()) {
            return "bbm/admin/mnu/bmm/BkmkMenuManageRegist";
        }            
     
        bkmkMenuManage.setUserId(user.getId());                    
        if (isAuthenticated) {
            bkmkMenuManageService.insertBkmkMenuManage(bkmkMenuManage);
        }

        return "forward:/sym/mnu/bmm/selectBkmkMenuManageList.do";
    }
    
    /**
     * 바로가기메뉴관리 미리보기 화면으로 이동한다.
     *      
     * @param BkmkMenuManageVO
     * @param model
     * @return
     * @throws Exception
     */    
    @RequestMapping(value="/sym/mnu/bmm/previewBkmkInf.do")
    public String previewBkmkInf(@ModelAttribute("searchVO") BkmkMenuManageVO bkmkMenuManageVO,ModelMap model)
            throws Exception { 
        String resultMsg    = "";
        // 0. Spring Security 사용자권한 처리
        
        /*LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
       
        if(!isAuthenticated) {
               return "bbm/uat/uia/LoginUsr";
        }       */ 
    
        bkmkMenuManageVO.setFirstIndex(0);
        bkmkMenuManageVO.setLastIndex(10);
        bkmkMenuManageVO.setRecordCountPerPage(10);
        
        //bkmkMenuManageVO.setUserId(user.getId());

        Map<String, Object> map = bkmkMenuManageService.selectBkmkMenuManageList(bkmkMenuManageVO);
               
        model.addAttribute("list_menulist",  map.get("resultList"));
        model.addAttribute("resultMsg", resultMsg);
        
        return  "bbm/admin/mnu/bmm/EgovBookMarkMenuPopup"; 
    }
}