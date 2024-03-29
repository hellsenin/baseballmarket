package com.bbm.cmm.zip.web;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.zip.service.RdnmadZipManageService;
import com.bbm.cmm.zip.service.Zip;
import com.bbm.cmm.zip.service.ZipManageService;
import com.bbm.cmm.zip.service.ZipVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 *
 * 우편번호에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
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
 *   2011.08.26	 정진오		IncludedInfo annotation 추가
 *	 2011.10.07	 이기하		보안취약점 수정(파일 업로드시 엑셀파일만 가능하도록 추가)
 *	 2011.11.21	 이기하		도로명주소 추가(rdnmadZip)
 *
 * </pre>
 */

@Controller
public class ZipManageController {

	protected Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "ZipManageService")
    private ZipManageService zipManageService;
	
	@Resource(name = "RdnmadZipService")
	private RdnmadZipManageService rdnmadZipService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Resource(name = "multipartResolver")
	CommonsMultipartResolver mailmultipartResolver;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * 우편번호 찾기 팝업 메인창을 호출한다.
	 * @param model
	 * @return "bbm/cmm/zip/ZipSearchPopup"
	 * @throws Exception
	 */
	@RequestMapping(value="/cmm/zip/popup/ZipSearchPopup.do")
 	public String callNormalCalPopup (ModelMap model
 			) throws Exception {
		return "bbm/cmm/zip/ZipSearchPopup";
	}

    /**
	 * 우편번호 찾기 목록을 조회한다.
     * @param searchVO
     * @param model
     * @return "bbm/cmm/zip/ZipSearchList"
     * @throws Exception
     */
    @RequestMapping(value="/cmm/zip/popup/ZipSearchList.do")
	public String selectZipSearchList (@ModelAttribute("searchVO") ZipVO searchVO
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
		
		String sList = "";
		
		if (searchVO.getSearchList() != null && searchVO.getSearchList() != "" ) {
			sList = searchVO.getSearchList().substring(0, 1);
		}
    	model.addAttribute("searchList", sList);
		
		if (sList.equals("1")) {
	        List CmmnCodeList = zipManageService.selectZipList(searchVO);
	        model.addAttribute("resultList", CmmnCodeList);
	
	        int totCnt = zipManageService.selectZipListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
	        model.addAttribute("paginationInfo", paginationInfo);
		} else {
			List CmmnCodeList = rdnmadZipService.selectZipList(searchVO);
			model.addAttribute("resultList", CmmnCodeList);
			
			int totCnt = rdnmadZipService.selectZipListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);
		}

        return "bbm/cmm/zip/ZipSearchList";
	}

	/**
	 * 우편번호를 삭제한다.
	 * @param loginVO
	 * @param zip
	 * @param model
	 * @return "forward:/cmm/zip/ZipList.do"
	 * @throws Exception
	 */
    @RequestMapping(value="/cmm/zip/ZipRemove.do")
	public String deleteZip (@ModelAttribute("loginVO") LoginVO loginVO
			, Zip zip, ZipVO searchVO
			, ModelMap model
			) throws Exception {
    	model.addAttribute("searchList", searchVO.getSearchList());
		if (searchVO.getSearchList().equals("1")) {
			zipManageService.deleteZip(zip);
		} else {
			rdnmadZipService.deleteZip(zip);
		}
        return "forward:/cmm/zip/ZipList.do";
	}

	/**
	 * 우편번호를 등록한다.
	 * @param loginVO
	 * @param zip
	 * @param bindingResult
	 * @param model
	 * @return "bbm/cmm/zip/ZipRegist"
	 * @throws Exception
	 */
    @RequestMapping(value="/cmm/zip/ZipRegist.do")
	public String insertZip (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("zip") Zip zip, ZipVO searchVO
			, BindingResult bindingResult
			, ModelMap model
			) throws Exception {
    	
		model.addAttribute("searchList", searchVO.getSearchList());
    	
		if   (zip.getZip() == null
    		||zip.getZip().equals("")) {

            return "bbm/cmm/zip/ZipRegist";
    	}

    	if (searchVO.getSearchList().equals("1")) {
    		beanValidator.validate(zip, bindingResult);
    		if (bindingResult.hasErrors()){
    			return "bbm/cmm/zip/ZipRegist";
    		}
    		
    		zip.setFrstRegisterId(loginVO.getUniqId());
    		zipManageService.insertZip(zip);
    	} else {
    		/*beanValidator.validate(zip, bindingResult);
    		if (bindingResult.hasErrors()){
    			return "bbm/cmm/zip/ZipRegist";
    		}*/
    		
    		zip.setFrstRegisterId(loginVO.getUniqId());
    		rdnmadZipService.insertZip(zip);
    	}
        return "forward:/cmm/zip/ZipList.do";
    }

	
	/**
	 * 우편번호 상세항목을 조회한다.
	 * @param loginVO
	 * @param zip
	 * @param model
	 * @return "bbm/cmm/zip/ZipDetail"
	 * @throws Exception
	 */
	@RequestMapping(value="/cmm/zip/ZipDetail.do")
 	public String selectZipDetail (@ModelAttribute("loginVO") LoginVO loginVO
 			, Zip zip, ZipVO searchVO
 			, ModelMap model
 			) throws Exception {
		if (searchVO.getSearchList().equals("1")) {
			Zip vo = zipManageService.selectZipDetail(zip);
			model.addAttribute("result", vo);
			model.addAttribute("searchList", searchVO.getSearchList());
		} else {
			Zip vo = rdnmadZipService.selectZipDetail(zip);
			model.addAttribute("result", vo);
			model.addAttribute("searchList", searchVO.getSearchList());
		}

		return "bbm/cmm/zip/ZipDetail";
	}

    /**
	 * 우편번호 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return "bbm/cmm/zip/ZipList"
     * @throws Exception
     */
	@IncludedInfo(name="우편번호관리", listUrl="/cmm/zip/ZipList.do", order = 1000 ,gid = 60)
    @RequestMapping(value="/cmm/zip/ZipList.do")
	public String selectZipList (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("searchVO") ZipVO searchVO
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
		
		if (searchVO.getSearchList().equals("1")) {
	        List CmmnCodeList = zipManageService.selectZipList(searchVO);
	        model.addAttribute("resultList", CmmnCodeList);
	
	        int totCnt = zipManageService.selectZipListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
	        model.addAttribute("paginationInfo", paginationInfo);
		} else {
			List CmmnCodeList = rdnmadZipService.selectZipList(searchVO);
			model.addAttribute("resultList", CmmnCodeList);
			
			int totCnt = rdnmadZipService.selectZipListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);
		}

        return "bbm/cmm/zip/ZipList";
	}

	/**
	 * 우편번호를 수정한다.
	 * @param loginVO
	 * @param zip
	 * @param bindingResult
	 * @param commandMap
	 * @param model
	 * @return "bbm/cmm/zip/ZipModify"
	 * @throws Exception
	 */
    @RequestMapping(value="/cmm/zip/ZipModify.do")
	public String updateZip (@ModelAttribute("loginVO") LoginVO loginVO
			, @ModelAttribute("zip") Zip zip, ZipVO searchVO
			, BindingResult bindingResult
			, Map commandMap
			, ModelMap model
			) throws Exception {
		String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
		model.addAttribute("searchList", searchVO.getSearchList());
    	if (sCmd.equals("")) {
    		if (searchVO.getSearchList().equals("1")) {
	    		Zip vo = zipManageService.selectZipDetail(zip);
	    		model.addAttribute("zip", vo);
    		} else {
    			Zip vo = rdnmadZipService.selectZipDetail(zip);
    			model.addAttribute("zip", vo);
    		}
    		return "bbm/cmm/zip/ZipModify";
    	} else if (sCmd.equals("Modify")) {
			if (searchVO.getSearchList().equals("1")) {
				beanValidator.validate(zip, bindingResult);
				if (bindingResult.hasErrors()){
					return "bbm/cmm/zip/ZipModify";
				}
				
				zip.setLastUpdusrId(loginVO.getUniqId());
				zipManageService.updateZip(zip);
			} else {
				/*beanValidator.validate(zip, bindingResult);
				if (bindingResult.hasErrors()){
					return "bbm/cmm/zip/ZipModify";
				}*/
				
				zip.setLastUpdusrId(loginVO.getUniqId());
				rdnmadZipService.updateZip(zip);
			}

	    	return "forward:/cmm/zip/ZipList.do";
    	} else {
	    	return "forward:/cmm/zip/ZipList.do";
    	}
    }
}