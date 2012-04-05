package com.bbm.adm.ccm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbm.adm.ccm.service.Code;
import com.bbm.adm.ccm.service.CodeManageService;
import com.bbm.adm.ccm.service.CodeVO;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;



/**
 * 
 * 공통코드에 대한 서비스 구현클래스를 정의한다
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
@Service("CodeManageService")
public class CodeManageServiceImpl extends AbstractServiceImpl implements CodeManageService {

    @Resource(name="CodeManageDAO")
    private CodeManageDAO cmmnCodeManageDAO;
    
	/**
	 * 공통코드를 삭제한다.
	 */
	public void deleteCode(Code cmmnCode) throws Exception {
		cmmnCodeManageDAO.deleteCode(cmmnCode);
	}

	/**
	 * 공통코드를 등록한다.
	 */
	public void insertCode(Code cmmnCode) throws Exception {
    	cmmnCodeManageDAO.insertCode(cmmnCode);    	
	}

	/**
	 * 공통코드 상세항목을 조회한다.
	 */
	public Code selectCodeDetail(Code cmmnCode) throws Exception {
    	Code ret = (Code)cmmnCodeManageDAO.selectCodeDetail(cmmnCode);
    	return ret;
	}

	/**
	 * 공통코드 목록을 조회한다.
	 */
	public List selectCodeList(CodeVO searchVO) throws Exception {
        return cmmnCodeManageDAO.selectCodeList(searchVO);
	}

	/**
	 * 공통코드 총 갯수를 조회한다.
	 */
	public int selectCodeListTotCnt(CodeVO searchVO) throws Exception {
        return cmmnCodeManageDAO.selectCodeListTotCnt(searchVO);
	}

	/**
	 * 공통코드를 수정한다.
	 */
	public void updateCode(Code cmmnCode) throws Exception {
		cmmnCodeManageDAO.updateCode(cmmnCode);
	}

}
