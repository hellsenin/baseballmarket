package com.bbm.adm.ccm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbm.adm.ccm.service.Code;
import com.bbm.adm.ccm.service.CodeVO;
import com.bbm.cmm.service.impl.EgovComAbstractDAO;

/**
 * 
 * 공통코드에 대한 데이터 접근 클래스를 정의한다
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
@Repository("CodeManageDAO")
public class CodeManageDAO extends EgovComAbstractDAO {

	/**
	 * 공통코드를 삭제한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	public void deleteCode(Code cmmnCode) throws Exception {
		delete("CodeManageDAO.deleteCode", cmmnCode);
	}


	/**
	 * 공통코드를 등록한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	public void insertCode(Code cmmnCode) throws Exception {
        insert("CodeManageDAO.insertCode", cmmnCode);
	}

	/**
	 * 공통코드 상세항목을 조회한다.
	 * @param cmmnCode
	 * @return Code(공통코드)
	 */
	public Code selectCodeDetail(Code cmmnCode) throws Exception {
		return (Code)selectByPk("CodeManageDAO.selectCodeDetail", cmmnCode);
	}


    /**
	 * 공통코드 목록을 조회한다.
     * @param searchVO
     * @return List(공통코드 목록)
     * @throws Exception
     */
    public List selectCodeList(CodeVO searchVO) throws Exception {
        return list("CodeManageDAO.selectCodeList", searchVO);
    }

    /**
	 * 공통코드 총 갯수를 조회한다.
     * @param searchVO
     * @return int(공통코드 총 갯수)
     */
    public int selectCodeListTotCnt(CodeVO searchVO) throws Exception {
        return (Integer)getSqlMapClientTemplate().queryForObject("CodeManageDAO.selectCodeListTotCnt", searchVO);
    }

	/**
	 * 공통코드를 수정한다.
	 * @param cmmnCode
	 * @throws Exception
	 */
	public void updateCode(Code cmmnCode) throws Exception {
		update("CodeManageDAO.updateCode", cmmnCode);
	}
	
}
