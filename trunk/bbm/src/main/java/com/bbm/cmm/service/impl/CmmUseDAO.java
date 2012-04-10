package com.bbm.cmm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbm.adm.ccm.service.DetailCode;
import com.bbm.cmm.ComDefaultCodeVO;

/**
 * @Class Name : CmmUseDAO.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 데이터 접근 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Repository("cmmUseDAO")
public class CmmUseDAO extends EgovComAbstractDAO {

    /**
     * 주어진 조건에 따른 공통코드를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<DetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception {
	return list("CmmUseDAO.selectCmmCodeDetail", vo);
    }



    /**
     * 공통코드로 사용할그룹정보를 를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<DetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception {
	return list("CmmUseDAO.selectGroupIdDetail", vo);
    }
}
