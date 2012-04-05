package com.bbm.adm.ccm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbm.adm.ccm.service.DetailCode;
import com.bbm.adm.ccm.service.DetailCodeVO;
import com.bbm.cmm.service.impl.EgovComAbstractDAO;

/**
 * 
 * 공통상세코드에 대한 데이터 접근 클래스를 정의한다
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
@Repository("DetailCodeManageDAO")
public class DetailCodeManageDAO extends EgovComAbstractDAO {

	/**
	 * 공통상세코드를 삭제한다.
	 * @param detailCode
	 * @throws Exception
	 */
	public void deleteDetailCode(DetailCode detailCode) throws Exception {
		delete("DetailCodeManageDAO.deleteDetailCode", detailCode);
	}


	/**
	 * 공통상세코드를 등록한다.
	 * @param detailCode
	 * @throws Exception
	 */
	public void insertDetailCode(DetailCode detailCode) throws Exception {
        insert("DetailCodeManageDAO.insertDetailCode", detailCode);
	}

	/**
	 * 공통상세코드 상세항목을 조회한다.
	 * @param detailCode
	 * @return DetailCode(공통상세코드)
	 */
	public DetailCode selectDetailCodeDetail(DetailCode detailCode) throws Exception {
		return (DetailCode) selectByPk("DetailCodeManageDAO.selectDetailCodeDetail", detailCode);
	}


    /**
	 * 공통상세코드 목록을 조회한다.
     * @param searchVO
     * @return List(공통상세코드 목록)
     * @throws Exception
     */
    public List selectDetailCodeList(DetailCodeVO searchVO) throws Exception {
        return list("DetailCodeManageDAO.selectDetailCodeList", searchVO);
    }

    /**
	 * 공통상세코드 총 갯수를 조회한다.
     * @param searchVO
     * @return int(공통상세코드 총 갯수)
     */
    public int selectDetailCodeListTotCnt(DetailCodeVO searchVO) throws Exception {
        return (Integer)getSqlMapClientTemplate().queryForObject("DetailCodeManageDAO.selectDetailCodeListTotCnt", searchVO);
    }

	/**
	 * 공통상세코드를 수정한다.
	 * @param detailCode
	 * @throws Exception
	 */
	public void updateDetailCode(DetailCode detailCode) throws Exception {
		update("DetailCodeManageDAO.updateDetailCode", detailCode);
	}
	
}
