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
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 
 * 공통코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
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
public class CodeManageController {

	@Resource(name = "CodeManageService")
    private CodeManageService cmmnCodeManageService;

	@Resource(name = "ClCodeManageService")
    private ClCodeManageService clCodeManageService;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;
    
	/**
	 * 공통코드를 삭제한다.
	 * @param loginVO
	 * @param cmmnCode
	 * @param model
	 * @return "forward:/admin/ccm/CodeList.do"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/CodeRemove.do")
	public String deleteCode (@ModelAttribute("loginVO") LoginVO loginVO
			, Code cmmnCode
			, ModelMap model
			) throws Exception {
    	cmmnCodeManageService.deleteCode(cmmnCode);
        return "forward:/admin/ccm/CodeList.do";
	}

	/**
	 * 공통코드를 등록한다.
	 * @param loginVO
	 * @param cmmnCode
	 * @param bindingResult
	 * @param model
	 * @return "bbm/admin/ccm/CodeRegist"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/CodeRegist.do")
	public String insertCode (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("cmmnCode") Code cmmnCode
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {    
    	if   (cmmnCode.getClCode() == null
    		||cmmnCode.getClCode().equals("")) {

    		ClCodeVO searchVO;
    		searchVO = new ClCodeVO();
    		searchVO.setRecordCountPerPage(999999);
    		searchVO.setFirstIndex(0);
    		searchVO.setSearchCondition("CodeList");
            List CodeList = clCodeManageService.selectClCodeList(searchVO);
            model.addAttribute("clCode", CodeList);
            
    		return "bbm/admin/ccm/CodeRegist";
    	}

        beanValidator.validate(cmmnCode, bindingResult);
		if (bindingResult.hasErrors()){
    		ClCodeVO searchVO;
    		searchVO = new ClCodeVO();
    		searchVO.setRecordCountPerPage(999999);
    		searchVO.setFirstIndex(0);
    		searchVO.setSearchCondition("CodeList");
            List CodeList = clCodeManageService.selectClCodeList(searchVO);
            model.addAttribute("clCode", CodeList);

            return "bbm/admin/ccm/CodeRegist";
		}

    	cmmnCode.setFrstRegisterId(loginVO.getUniqId());
    	cmmnCodeManageService.insertCode(cmmnCode);
        return "forward:/admin/ccm/CodeList.do";
    }

	/**
	 * 공통코드 상세항목을 조회한다.
	 * @param loginVO
	 * @param cmmnCode
	 * @param model
	 * @return "bbm/admin/ccm/CodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/ccm/CodeDetail.do")
 	public String selectCodeDetail (@ModelAttribute("loginVO") LoginVO loginVO
 			, Code cmmnCode
 			, ModelMap model
 			) throws Exception {
		Code vo =cmmnCodeManageService.selectCodeDetail(cmmnCode);
		model.addAttribute("result", vo);
		
		return "bbm/admin/ccm/CodeDetail";
	}

    /**
	 * 공통코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return "bbm/admin/ccm/CodeList"
     * @throws Exception
     */
	@IncludedInfo(name="공통코드", listUrl="/admin/ccm/CodeList.do", order = 980 ,gid = 60)
    @RequestMapping(value="/admin/ccm/CodeList.do")
	public String selectCodeList (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("searchVO") CodeVO searchVO
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
		
        List CodeList =cmmnCodeManageService.selectCodeList(searchVO);
        model.addAttribute("resultList", CodeList);
        
        int totCnt =cmmnCodeManageService.selectCodeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "bbm/admin/ccm/CodeList";
	}

	/**
	 * 공통코드를 수정한다.
	 * @param loginVO
	 * @param cmmnCode
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "bbm/admin/ccm/CodeModify"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/CodeModify.do")
	public String updateCode (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("cmmnCode") Code cmmnCode
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			) throws Exception {
		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
    	if (sCmd.equals("")) {
    		Code vo =cmmnCodeManageService.selectCodeDetail(cmmnCode);
    		model.addAttribute("cmmnCode", vo);

    		return "bbm/admin/ccm/CodeModify";
    	} else if (sCmd.equals("Modify")) {
            beanValidator.validate(cmmnCode, bindingResult);
    		if (bindingResult.hasErrors()){
        		Code vo =cmmnCodeManageService.selectCodeDetail(cmmnCode);
        		model.addAttribute("cmmnCode", vo);

        		return "bbm/admin/ccm/CodeModify";
    		}
    		
    		cmmnCode.setLastUpdusrId(loginVO.getUniqId());
	    	cmmnCodeManageService.updateCode(cmmnCode);
	        return "forward:/admin/ccm/CodeList.do";
    	} else {
    		return "forward:/admin/ccm/CodeList.do";
    	}
    }
	
}