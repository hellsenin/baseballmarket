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

import com.bbm.adm.ccm.service.ClCode;
import com.bbm.adm.ccm.service.ClCodeManageService;
import com.bbm.adm.ccm.service.ClCodeVO;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/**
 * 
 * 공통분류코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
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
public class ClCodeManageController {
	@Resource(name = "ClCodeManageService")
    private ClCodeManageService clCodeManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;
    
	/**
	 * 공통분류코드를 삭제한다.
	 * @param loginVO
	 * @param clCode
	 * @param model
	 * @return "forward:/admin/ccm/ClCodeList.do"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/ClCodeRemove.do")
	public String deleteClCode (@ModelAttribute("loginVO") LoginVO loginVO
			, ClCode clCode
			, ModelMap model
			) throws Exception {
    	 clCodeManageService.deleteClCode(clCode);
        return "forward:/admin/ccm/ClCodeList.do";
	}

	/**
	 * 공통분류코드를 등록한다.
	 * @param loginVO
	 * @param clCode
	 * @param bindingResult
	 * @return "bbm/admin/ccm/ClCodeRegist"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/ClCodeRegist.do")
	public String insertClCode (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("clCode") ClCode clCode
			, BindingResult bindingResult , ModelMap model
			) throws Exception {    
    	if   (clCode.getClCode() == null
    		||clCode.getClCode().equals("")) {
    		return "bbm/admin/ccm/ClCodeRegist";
    	}

        beanValidator.validate(clCode, bindingResult);
		if (bindingResult.hasErrors()){
    		return "bbm/admin/ccm/ClCodeRegist";
		}
		
		if(clCode.getClCode() != null){
			ClCode vo = clCodeManageService.selectClCodeDetail(clCode);
			if(vo != null){
				model.addAttribute("message", "이미 등록된 분류코드가 존재합니다.");
				return "bbm/admin/ccm/ClCodeRegist";
			}
		}
		
		clCode.setFrstRegisterId(loginVO.getUniqId());
		clCodeManageService.insertClCode(clCode);
		return "forward:/admin/ccm/ClCodeList.do";
    }

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 * @param loginVO
	 * @param clCode
	 * @param model
	 * @return "bbm/admin/ccm/ClCodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/ccm/ClCodeDetail.do")
 	public String selectClCodeDetail (@ModelAttribute("loginVO") LoginVO loginVO
 			, ClCode clCode
 			, ModelMap model
 			) throws Exception {
		ClCode vo = clCodeManageService.selectClCodeDetail(clCode);
		model.addAttribute("result", vo);
		
		return "bbm/admin/ccm/ClCodeDetail";
	}

    /**
	 * 공통분류코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return "bbm/admin/ccm/ClCodeList"
     * @throws Exception
     */
	@IncludedInfo(name="공통분류코드", listUrl="/admin/ccm/ClCodeList.do", order = 960 ,gid = 60)
    @RequestMapping(value="/admin/ccm/ClCodeList.do")
	public String selectClCodeList (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("searchVO") ClCodeVO searchVO
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
		
        List CodeList = clCodeManageService.selectClCodeList(searchVO);
        model.addAttribute("resultList", CodeList);
        
        int totCnt = clCodeManageService.selectClCodeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "bbm/admin/ccm/ClCodeList";
	}

	/**
	 * 공통분류코드를 수정한다.
	 * @param loginVO
	 * @param clCode
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "bbm/admin/ccm/ClCodeModify"
	 * @throws Exception
	 */
    @RequestMapping(value="/admin/ccm/ClCodeModify.do")
	public String updateClCode (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("administCode") ClCode clCode
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			) throws Exception {
		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
    	if (sCmd.equals("")) {
    		ClCode vo = clCodeManageService.selectClCodeDetail(clCode);
    		model.addAttribute("clCode", vo);

    		return "bbm/admin/ccm/ClCodeModify";
    	} else if (sCmd.equals("Modify")) {
            beanValidator.validate(clCode, bindingResult);
    		if (bindingResult.hasErrors()){
        		ClCode vo = clCodeManageService.selectClCodeDetail(clCode);
        		model.addAttribute("clCode", vo);

        		return "bbm/admin/ccm/ClCodeModify";
    		}
    		clCode.setLastUpdusrId(loginVO.getUniqId());
	    	clCodeManageService.updateClCode(clCode);
	        return "forward:/admin/ccm/ClCodeList.do";
    	} else {
    		return "forward:/admin/ccm/ClCodeList.do";
    	}
    }
	
}