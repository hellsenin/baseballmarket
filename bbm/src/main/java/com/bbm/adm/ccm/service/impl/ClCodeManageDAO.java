package com.bbm.adm.ccm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbm.adm.ccm.service.ClCode;
import com.bbm.adm.ccm.service.ClCodeVO;
import com.bbm.cmm.service.impl.EgovComAbstractDAO;

/**
 * 
 * 공통분류코드에 대한 데이터 접근 클래스를 정의한다
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
@Repository("ClCodeManageDAO")
public class ClCodeManageDAO extends EgovComAbstractDAO {

	/**
	 * 공통분류코드를 삭제한다.
	 * @param clCode
	 * @throws Exception
	 */
	public void deleteClCode(ClCode clCode) throws Exception {
		delete("ClCodeManageDAO.deleteClCode", clCode);
	}


	/**
	 * 공통분류코드를 등록한다.
	 * @param clCode
	 * @throws Exception
	 */
	public void insertClCode(ClCode clCode) throws Exception {
        insert("ClCodeManageDAO.insertClCode", clCode);
	}

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 * @param clCode
	 * @return ClCode(공통분류코드)
	 */
	public ClCode selectClCodeDetail(ClCode clCode) throws Exception {
		return (ClCode)selectByPk("ClCodeManageDAO.selectClCodeDetail", clCode);
	}


    /**
	 * 공통분류코드 목록을 조회한다.
     * @param searchVO
     * @return List(공통분류코드 목록)
     * @throws Exception
     */
    public List selectClCodeList(ClCodeVO searchVO) throws Exception {
        return list("ClCodeManageDAO.selectClCodeList", searchVO);
    }

    /**
	 * 공통분류코드 총 갯수를 조회한다.
     * @param searchVO
     * @return int(공통분류코드 총 갯수)
     */
    public int selectClCodeListTotCnt(ClCodeVO searchVO) throws Exception {
        return (Integer)getSqlMapClientTemplate().queryForObject("ClCodeManageDAO.selectClCodeListTotCnt", searchVO);
    }

	/**
	 * 공통분류코드를 수정한다.
	 * @param clCode
	 * @throws Exception
	 */
	public void updateClCode(ClCode clCode) throws Exception {
		update("ClCodeManageDAO.updateClCode", clCode);
	}
	
}
