package com.bbm.adm.mnu.stm.service.impl;

import org.springframework.stereotype.Repository;

import com.bbm.cmm.ComDefaultVO;
import com.bbm.adm.mnu.stm.service.SiteMapngVO;
import com.bbm.cmm.service.impl.EgovComAbstractDAO;
/**
 * 사이트맵 조회에 대한 DAO 클래스를 정의한다.
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
@Repository("siteMapngDAO")
public class SiteMapngDAO extends EgovComAbstractDAO{

	/**
	 * 사이트맵 조회
	 * @param vo ComDefaultVO 
	 * @return SiteMapngVO
	 * @exception Exception 
	 */
	public SiteMapngVO selectSiteMapng(ComDefaultVO vo)throws Exception{
		return (SiteMapngVO)selectByPk("siteMapngDAO.selectSiteMapng_D", vo); 
	}

	/**
	 * MapCreatId 조회
	 * @param vo ComDefaultVO
	 * @return String
	 * @exception Exception 
	 */
	public String selectSiteMapngByMapCreatID(ComDefaultVO vo)throws Exception{
		return (String)selectByPk("siteMapngDAO.selectSiteMapngByMapCreatID", vo); 
	}	
	
}