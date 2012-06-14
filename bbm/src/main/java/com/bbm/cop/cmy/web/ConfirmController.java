package com.bbm.cop.cmy.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.bbm.cmm.ComDefaultCodeVO;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.service.EgovCmmUseService;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.cop.cmy.service.ConfirmHistoryVO;
import com.bbm.cop.cmy.service.ConfirmManageService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 승인정보 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.4.10  이삼섭          최초 생성
 *   2011.07.21 안민정          커뮤니티 관련 메소드 분리 (-> EgovCmyConfirmController)
 *
 * </pre>
 */
@Controller
public class ConfirmController {
	
	 
	 

    @Resource(name = "ConfirmManageService")
    private ConfirmManageService confmService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
    
    //Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 승인(탈퇴)요청에 대한 목록을 조회한다.
     * 
     * @param historyvO
     * @param sessionVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/com/selectConfirmRequest.do")
    public String selectConfirmRequest(@ModelAttribute("searchVO") ConfirmHistoryVO historyVO, SessionStatus status, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	@SuppressWarnings("unused")
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	historyVO.setPageUnit(propertyService.getInt("pageUnit"));
	historyVO.setPageSize(propertyService.getInt("pageSize"));

	PaginationInfo paginationInfo = new PaginationInfo();
	
	paginationInfo.setCurrentPageNo(historyVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(historyVO.getPageUnit());
	paginationInfo.setPageSize(historyVO.getPageSize());

	historyVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	historyVO.setLastIndex(paginationInfo.getLastRecordIndex());
	historyVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	historyVO.setConfmerId(user.getUniqId());

	Map<String, Object> map = confmService.selectConfirmRequest(historyVO);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
	paginationInfo.setTotalRecordCount(totCnt);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));	
	model.addAttribute("paginationInfo", paginationInfo);

	return "bbm/cop/com/EgovConfirmList";
    }

    /**
     * 승인(탈퇴)요청 확인 처리를 위해 수정페이지로 이동한다.
     * 
     * @param historyVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/cop/com/forUpdateConfirmRequest.do")
    public String forUpdateConfirmRequest(@ModelAttribute("searchVO") ConfirmHistoryVO historyVO, SessionStatus status, ModelMap model)
	    throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	historyVO.setConfmerId(user.getUniqId());
	
	if (isAuthenticated) {
	    ConfirmHistoryVO vo = confmService.selectSingleConfirmRequest(historyVO);
	    model.addAttribute("historyVO", vo);
	}
	
	ComDefaultCodeVO vo = new ComDefaultCodeVO();
	
	vo.setCodeId("COM007");
	
	List codeResult = cmmUseService.selectCmmCodeDetail(vo);
	
	model.addAttribute("typeList", codeResult);

	return "bbm/cop/com/EgovConfmInfUpdt";
    }
	
}
