package com.bbm.adm.ccm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbm.adm.ccm.service.ClCode;
import com.bbm.adm.ccm.service.ClCodeVO;
import com.bbm.adm.ccm.service.ClCodeManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통분류코드에 대한 서비스 구현클래스를 정의한다
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
@Service("ClCodeManageService")
public class ClCodeManageServiceImpl extends AbstractServiceImpl implements ClCodeManageService {

    @Resource(name="ClCodeManageDAO")
    private ClCodeManageDAO clCodeManageDAO;
    
	/**
	 * 공통분류코드를 삭제한다.
	 */
	public void deleteClCode(ClCode clCode) throws Exception {
		clCodeManageDAO.deleteClCode(clCode);
	}

	/**
	 * 공통분류코드를 등록한다.
	 */
	public void insertClCode(ClCode clCode) throws Exception {
    	clCodeManageDAO.insertClCode(clCode);    	
	}

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 */
	public ClCode selectClCodeDetail(ClCode clCode) throws Exception {
    	ClCode ret = (ClCode)clCodeManageDAO.selectClCodeDetail(clCode);
    	return ret;
	}

	/**
	 * 공통분류코드 목록을 조회한다.
	 */
	public List selectClCodeList(ClCodeVO searchVO) throws Exception {
        return clCodeManageDAO.selectClCodeList(searchVO);
	}

	/**
	 * 공통분류코드 총 갯수를 조회한다.
	 */
	public int selectClCodeListTotCnt(ClCodeVO searchVO) throws Exception {
        return clCodeManageDAO.selectClCodeListTotCnt(searchVO);
	}

	/**
	 * 공통분류코드를 수정한다.
	 */
	public void updateClCode(ClCode clCode) throws Exception {
		clCodeManageDAO.updateClCode(clCode);
	}

}
