package com.bbm.adm.mnu.stm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbm.cmm.ComDefaultVO;
import com.bbm.adm.mnu.stm.service.EgovSiteMapngService;
import com.bbm.adm.mnu.stm.service.SiteMapngVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/** 
 * 사이트맵 조회를 처리하는 비즈니스 구현 클래스를 정의한다.
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
 *
 * </pre>
 */
@Service("siteMapngService")
public class EgovSiteMapngServiceImpl extends AbstractServiceImpl implements EgovSiteMapngService{

	@Resource(name="siteMapngDAO")
    private SiteMapngDAO siteMapngDAO;

	/**
	 * 사이트맵 조회
	 * @param vo ComDefaultVO  
	 * @return SiteMapngVO
	 * @exception Exception
	 */

	public SiteMapngVO selectSiteMapng(ComDefaultVO vo) throws Exception{
		String sMapCreatID = null;

		SiteMapngVO sitemapngvo = new SiteMapngVO();
		sMapCreatID = siteMapngDAO.selectSiteMapngByMapCreatID(vo);
		vo.setSearchKeyword(sMapCreatID);
        return siteMapngDAO.selectSiteMapng(vo);
	}
}