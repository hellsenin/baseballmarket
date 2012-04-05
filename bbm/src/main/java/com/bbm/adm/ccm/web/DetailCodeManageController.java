package com.bbm.adm.ccm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.adm.ccm.service.ClCodeManageService;
import com.bbm.adm.ccm.service.ClCodeVO;
import com.bbm.adm.ccm.service.Code;
import com.bbm.adm.ccm.service.CodeManageService;
import com.bbm.adm.ccm.service.CodeVO;
import com.bbm.adm.ccm.service.DetailCode;
import com.bbm.adm.ccm.service.DetailCodeManageService;
import com.bbm.adm.ccm.service.DetailCodeVO;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 
 * 공통상세코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
 * @author 공통서비스 개발팀 이중호
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.01  이중호          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * </pre>
 */

@Controller
public class DetailCodeManageController {

	@Resource(name = "DetailCodeManageService")
    private DetailCodeManageService detailCodeManageService;

	@Resource(name = "ClCodeManageService")
    private ClCodeManageService clCodeManageService;

	@Resource(name = "CodeManageService")
    private CodeManageService cmmnCodeManageService;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;
    
	/**
	 * 공통상세코드를 삭제한다.
	 * @param loginVO
	 * @param detailCode
	 * @param model
	 * @return "forward:/admin/ccm/DetailCodeList.do"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/DetailCodeRemove.do")
	public String deleteDetailCode (@ModelAttribute("loginVO") LoginVO loginVO
			, DetailCode detailCode
			, ModelMap model
			) throws Exception {
    	detailCodeManageService.deleteDetailCode(detailCode);
        return "forward:/admin/ccm/DetailCodeList.do";
	}

	/**
	 * 공통상세코드를 등록한다.
	 * @param loginVO
	 * @param detailCode
	 * @param cmmnCode
	 * @param bindingResult
	 * @param model
	 * @return "bbm/admin/ccm/DetailCodeRegist"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/DetailCodeRegist.do")
	public String insertDetailCode	(@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("detailCode") DetailCode detailCode
			, @ModelAttribute("cmmnCode") Code cmmnCode
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			)	throws Exception {

		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
    	if   (detailCode.getCodeId() == null
        		||detailCode.getCodeId().equals("")
        		||detailCode.getCode() == null
        		||detailCode.getCode().equals("")
        		||sCmd.equals("")) {
        		
        		ClCodeVO searchClCodeVO;
        		searchClCodeVO = new ClCodeVO();
        		searchClCodeVO.setRecordCountPerPage(999999);
        		searchClCodeVO.setFirstIndex(0);
        		searchClCodeVO.setSearchCondition("CodeList");
                List ClCodeList = (List)clCodeManageService.selectClCodeList(searchClCodeVO);
                model.addAttribute("clCodeList", ClCodeList);
        		
                CodeVO searchCodeVO;
                searchCodeVO = new CodeVO();
                searchCodeVO.setRecordCountPerPage(999999);
                searchCodeVO.setFirstIndex(0);
                searchCodeVO.setSearchCondition("clCode");
                if (cmmnCode.getClCode().equals("")) {
                	EgovMap emp = (EgovMap)ClCodeList.get(0);
                	cmmnCode.setClCode(emp.get("clCode").toString());
                }
                searchCodeVO.setSearchKeyword(cmmnCode.getClCode());
        		
                List CodeList = cmmnCodeManageService.selectCodeList(searchCodeVO);
                model.addAttribute("cmmnCodeList", CodeList);

                return "bbm/admin/ccm/DetailCodeRegist";
    	} else if (sCmd.equals("Regist")) {

	        beanValidator.validate(detailCode, bindingResult);
			if (bindingResult.hasErrors()){
	    		ClCodeVO searchClCodeVO;
	    		searchClCodeVO = new ClCodeVO();
	    		searchClCodeVO.setRecordCountPerPage(999999);
	    		searchClCodeVO.setFirstIndex(0);
	            List ClCodeList = (List)clCodeManageService.selectClCodeList(searchClCodeVO);
	            model.addAttribute("clCodeList", ClCodeList);
	    		
	            CodeVO searchCodeVO;
	            searchCodeVO = new CodeVO();
	            searchCodeVO.setRecordCountPerPage(999999);
	            searchCodeVO.setFirstIndex(0);
	            searchCodeVO.setSearchCondition("clCode");
	            if (cmmnCode.getClCode().equals("")) {
	            	EgovMap emp = (EgovMap)ClCodeList.get(0);
	            	cmmnCode.setClCode(emp.get("clCode").toString());
	            }
	            searchCodeVO.setSearchKeyword(cmmnCode.getClCode());
	    		
	            List CodeList = cmmnCodeManageService.selectCodeList(searchCodeVO);
	            model.addAttribute("cmmnCodeList", CodeList);
	            
	            return "bbm/admin/ccm/DetailCodeRegist";
			}
			
			DetailCode vo = detailCodeManageService.selectDetailCodeDetail(detailCode);
	    	if(vo != null){
	    		model.addAttribute("message", "이미 등록된 코드가 존재합니다.");
	    		
	    		ClCodeVO searchClCodeVO;
	    		searchClCodeVO = new ClCodeVO();
	    		searchClCodeVO.setRecordCountPerPage(999999);
	    		searchClCodeVO.setFirstIndex(0);
	            List ClCodeList = (List)clCodeManageService.selectClCodeList(searchClCodeVO);
	            model.addAttribute("clCodeList", ClCodeList);
	    		
	            CodeVO searchCodeVO;
	            searchCodeVO = new CodeVO();
	            searchCodeVO.setRecordCountPerPage(999999);
	            searchCodeVO.setFirstIndex(0);
	            searchCodeVO.setSearchCondition("clCode");
	            if (cmmnCode.getClCode().equals("")) {
	            	EgovMap emp = (EgovMap)ClCodeList.get(0);
	            	cmmnCode.setClCode(emp.get("clCode").toString());
	            }
	            searchCodeVO.setSearchKeyword(cmmnCode.getClCode());
	    		
	            List CodeList = cmmnCodeManageService.selectCodeList(searchCodeVO);
	            model.addAttribute("cmmnCodeList", CodeList);
	    		
	    		return "bbm/admin/ccm/DetailCodeRegist";
	    	}
	    	detailCode.setFrstRegisterId(loginVO.getUniqId());
	    	detailCodeManageService.insertDetailCode(detailCode);
	    	return "forward:/admin/ccm/DetailCodeList.do";
    	}  else {
    		return "forward:/admin/ccm/DetailCodeList.do";
    	}
    }

	/**
	 * 공통상세코드 상세항목을 조회한다.
	 * @param loginVO
	 * @param detailCode
	 * @param model
	 * @return "egovframework/com/cmm/sym/ccm/DetailCodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/ccm/DetailCodeDetail.do")
 	public String selectDetailCodeDetail (@ModelAttribute("loginVO") LoginVO loginVO
 			, DetailCode detailCode
 			,	ModelMap model
 			)	throws Exception {
    	DetailCode vo = detailCodeManageService.selectDetailCodeDetail(detailCode);
		model.addAttribute("result", vo);
		
		return "bbm/admin/ccm/DetailCodeDetail";
	}

    /**
	 * 공통상세코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return "bbm/admin/ccm/DetailCodeList"
     * @throws Exception
     */
	@IncludedInfo(name="공통상세코드", listUrl="/admin/ccm/DetailCodeList.do", order = 970 ,gid = 60)
    @RequestMapping(value="/admin/ccm/DetailCodeList.do")
	public String selectDetailCodeList (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("searchVO") DetailCodeVO searchVO
			, ModelMap model
			) throws Exception {
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
		
        List CodeList = detailCodeManageService.selectDetailCodeList(searchVO);
        model.addAttribute("resultList", CodeList);
        
        int totCnt = detailCodeManageService.selectDetailCodeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "bbm/admin/ccm/DetailCodeList";
	}

	/**
	 * 공통상세코드를 수정한다.
	 * @param loginVO
	 * @param detailCode
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "bbm/admin/ccm/DetailCodeModify"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/DetailCodeModify.do")
	public String updateDetailCode (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("detailCode") DetailCode detailCode
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			) throws Exception {
		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
    	if (sCmd.equals("")) {
    		DetailCode vo = detailCodeManageService.selectDetailCodeDetail(detailCode);
    		model.addAttribute("detailCode", vo);

    		return "bbm/admin/ccm/DetailCodeModify";
    	} else if (sCmd.equals("Modify")) {
            beanValidator.validate(detailCode, bindingResult);
    		if (bindingResult.hasErrors()){
        		DetailCode vo = detailCodeManageService.selectDetailCodeDetail(detailCode);
        		model.addAttribute("detailCode", vo);

        		return "bbm/admin/ccm/DetailCodeModify";
    		}

    		detailCode.setLastUpdusrId(loginVO.getUniqId());
	    	detailCodeManageService.updateDetailCode(detailCode);
	        return "forward:/admin/ccm/DetailCodeList.do";
    	} else {
    		return "forward:/admin/ccm/DetailCodeList.do";
    	}
    }

}