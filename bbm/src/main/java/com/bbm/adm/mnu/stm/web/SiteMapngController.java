package com.bbm.adm.mnu.stm.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbm.cmm.ComDefaultVO;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.adm.mnu.stm.service.SiteMapngService;
import com.bbm.adm.mnu.stm.service.SiteMapngVO;
import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * 사이트맵 조회 처리를 하는 비즈니스 구현 클래스
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
 *   2011.07.29  서준식 		사이트 맵 생성 안했을 때 발생하는 오류 수정
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */

@Controller
public class SiteMapngController {




	protected Log log = LogFactory.getLog(this.getClass());

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** SiteMapngService */
	@Resource(name = "siteMapngService")
    private SiteMapngService siteMapngService;

	/*사이트맵조회*/
    /**
     * 사이트맵 화면을 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/stm/SiteMapng"
     * @exception Exception
     */
	@IncludedInfo(name="사이트맵", order = 1101 ,gid = 60)
    @RequestMapping(value="/sym/mnu/stm/SiteMapng.do")
    public String selectSiteMapng(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	LoginVO user =
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	searchVO.setSearchKeyword(user.getUniqId());
    	SiteMapngVO  resultVO = siteMapngService.selectSiteMapng(searchVO);
    	if(resultVO == null){
    		model.addAttribute("resultMsg", "사이트맵을 생성해 주세요.");
    		return "bbm/admin/mnu/stm/SiteMapng";
    	}
    	log.debug(resultVO.getBndeFileNm());
        model.addAttribute("resultVO", resultVO);

        return "bbm/admin/mnu/stm/SiteMapng";
    }
}