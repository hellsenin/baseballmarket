package com.bbm.adm.ccm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbm.adm.ccm.service.DetailCode;
import com.bbm.adm.ccm.service.DetailCodeManageService;
import com.bbm.adm.ccm.service.DetailCodeVO;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통상세코드에 대한 서비스 구현클래스를 정의한다
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
 *
 * </pre>
 */
@Service("DetailCodeManageService")
public class DetailCodeManageServiceImpl extends AbstractServiceImpl implements DetailCodeManageService {

    @Resource(name="DetailCodeManageDAO")
    private DetailCodeManageDAO detailCodeManageDAO;
    
	/**
	 * 공통상세코드를 삭제한다.
	 */
	public void deleteDetailCode(DetailCode detailCode) throws Exception {
		detailCodeManageDAO.deleteDetailCode(detailCode);
	}

	/**
	 * 공통상세코드를 등록한다.
	 */
	public void insertDetailCode(DetailCode detailCode) throws Exception {
    	detailCodeManageDAO.insertDetailCode(detailCode);    	
	}

	/**
	 * 공통상세코드 상세항목을 조회한다.
	 */
	public DetailCode selectDetailCodeDetail(DetailCode detailCode) throws Exception {
    	DetailCode ret = (DetailCode)detailCodeManageDAO.selectDetailCodeDetail(detailCode);
    	return ret;
	}

	/**
	 * 공통상세코드 목록을 조회한다.
	 */
	public List selectDetailCodeList(DetailCodeVO searchVO) throws Exception {
        return detailCodeManageDAO.selectDetailCodeList(searchVO);
	}

	/**
	 * 공통상세코드 총 갯수를 조회한다.
	 */
	public int selectDetailCodeListTotCnt(DetailCodeVO searchVO) throws Exception {
        return detailCodeManageDAO.selectDetailCodeListTotCnt(searchVO);
	}

	/**
	 * 공통상세코드를 수정한다.
	 */
	public void updateDetailCode(DetailCode detailCode) throws Exception {
		detailCodeManageDAO.updateDetailCode(detailCode);
	}

}
