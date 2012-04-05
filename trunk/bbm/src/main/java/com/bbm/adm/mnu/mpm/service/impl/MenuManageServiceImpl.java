package com.bbm.adm.mnu.mpm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.bbm.adm.mnu.mpm.service.MenuManageService;
import com.bbm.adm.mnu.mpm.service.MenuManageVO;
import com.bbm.adm.prm.service.ProgrmManageVO;
import com.bbm.adm.prm.service.impl.ProgrmManageDAO;
import com.bbm.cmm.ComDefaultVO;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/** 
 * 메뉴목록관리, 생성, 사이트맵을 처리하는 비즈니스 구현 클래스를 정의한다.
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
 *   2011.07.01  서준식			자기 메뉴 정보를 상위메뉴 정보로 참조하는 메뉴정보가 있는지 조회하는 
 *   							selectUpperMenuNoByPk() 메서드 추가   
 *
 * </pre>
 */

@Service("meunManageService")
public class MenuManageServiceImpl extends AbstractServiceImpl implements MenuManageService{

	protected Log log = LogFactory.getLog(this.getClass());
	
	@Resource(name="menuManageDAO")
    private MenuManageDAO menuManageDAO;
	@Resource(name="progrmManageDAO")
    private ProgrmManageDAO progrmManageDAO;
	
	@Resource(name = "multipartResolver")
	CommonsMultipartResolver mailmultipartResolver;
	/**
	 * 메뉴 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return MenuManageVO
	 * @exception Exception
	 */
	public MenuManageVO selectMenuManage(ComDefaultVO vo) throws Exception{
        return menuManageDAO.selectMenuManage(vo);
	}

	/**
	 * 메뉴 목록을 조회
	 * @param vo ComDefaultVO 
	 * @return List
	 * @exception Exception
	 */
	public List selectMenuManageList(ComDefaultVO vo) throws Exception {
   		return menuManageDAO.selectMenuManageList(vo);
	}

	/**
	 * 메뉴목록 총건수를 조회한다.
	 * @param vo ComDefaultVO 
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuManageListTotCnt(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectMenuManageListTotCnt(vo);
	}
    
	/**
	 * 메뉴번호를 상위메뉴로 참조하고 있는 메뉴 존재여부를 조회
	 * @param vo ComDefaultVO 
	 * @return int
	 * @exception Exception
	 */
    public int selectUpperMenuNoByPk(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectUpperMenuNoByPk(vo);
	}
    
    
    /**
	 * 메뉴번호 존재 여부를 조회한다.
	 * @param vo ComDefaultVO 
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuNoByPk(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectMenuNoByPk(vo);
	}
    
	/**
	 * 메뉴 정보를 등록
	 * @param vo MenuManageVO
	 * @exception Exception
	 */
	public void insertMenuManage(MenuManageVO vo) throws Exception {
		menuManageDAO.insertMenuManage(vo);
	}

	/**
	 * 메뉴 정보를 수정
	 * @param vo MenuManageVO 
	 * @exception Exception
	 */
	public void updateMenuManage(MenuManageVO vo) throws Exception {
		menuManageDAO.updateMenuManage(vo);
	}

	/**
	 * 메뉴 정보를 삭제
	 * @param vo MenuManageVO
	 * @exception Exception
	 */
	public void deleteMenuManage(MenuManageVO vo) throws Exception {
		menuManageDAO.deleteMenuManage(vo);
	}

	/**
	 * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
	 * @param checkedMenuNoForDel String
	 * @exception Exception
	 */
	public void deleteMenuManageList(String checkedMenuNoForDel) throws Exception {
		MenuManageVO vo = null;

		String [] delMenuNo = checkedMenuNoForDel.split(",");

		if (delMenuNo == null || (delMenuNo.length ==0)){ throw new java.lang.Exception("String Split Error!"); }
        for (int i=0; i<delMenuNo.length ; i++){
			vo = new MenuManageVO();
			vo.setMenuNo(Integer.parseInt(delMenuNo[i]));
			menuManageDAO.deleteMenuManage(vo);
		}
	}

	
	/*  메뉴 생성 관리  */
	
	/**
	 * 메뉴 목록을 조회
	 * @return List
	 * @exception Exception
	 */
	public List selectMenuList() throws Exception {
   		return menuManageDAO.selectMenuList();
	}



	
    
	/*### 메뉴관련 프로세스 ###*/
	/**
	 * MainMenu Head Menu 조회
	 * @param vo MenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List selectMainMenuHead(MenuManageVO vo) throws Exception {
   		return menuManageDAO.selectMainMenuHead(vo);
	}	

	/**
	 * MainMenu Head Left 조회
	 * @param vo MenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List selectMainMenuLeft(MenuManageVO vo) throws Exception {
   		return menuManageDAO.selectMainMenuLeft(vo);
	}

	/**
	 * MainMenu Head MenuURL 조회
	 * @param  iMenuNo  int
	 * @param  sUniqId  String
	 * @return String
	 * @exception Exception
	 */
	public String selectLastMenuURL(int iMenuNo, String sUniqId) throws Exception {
		MenuManageVO vo = new MenuManageVO();
		vo.setMenuNo(selectLastMenuNo(iMenuNo, sUniqId)) ;
   		return menuManageDAO.selectLastMenuURL(vo);
	}
	
	/**
	 * MainMenu Head Menu MenuNo 조회
	 * @param  iMenuNo  int
	 * @param  sUniqId  String
	 * @return String
	 * @exception Exception
	 */
	private int selectLastMenuNo(int iMenuNo, String sUniqId) throws Exception {
		int chkMenuNo = iMenuNo;
		int cntMenuNo = 0;
		for(;chkMenuNo > -1;){
			chkMenuNo = selectLastMenuNoChk(chkMenuNo, sUniqId);
			if(chkMenuNo > 0){
				cntMenuNo = chkMenuNo;
			}
		}
   		return cntMenuNo;
	}
	
	/**
	 * MainMenu Head Menu Last MenuNo 조회
	 * @param  iMenuNo  int
	 * @param  sUniqId  String
	 * @return String
	 * @exception Exception
	 */
	private int selectLastMenuNoChk(int iMenuNo, String sUniqId) throws Exception {
		MenuManageVO vo = new MenuManageVO();
		vo.setMenuNo(iMenuNo);
		vo.setTempValue(sUniqId) ;
		int chkMenuNo = 0;
		int cntMenuNo = 0;
		cntMenuNo = menuManageDAO.selectLastMenuNoCnt(vo);
		if(cntMenuNo>0){
			chkMenuNo = menuManageDAO.selectLastMenuNo(vo);
		}else{
			chkMenuNo = -1; 
		}
		return  chkMenuNo;
	}

/*### 일괄처리 프로세스 ###*/
	/**
	 * 메뉴일괄초기화 프로세스 메뉴목록테이블, 프로그램 목록테이블 전체 삭제
	 * @return boolean
	 * @exception Exception
	 */
	public boolean menuBndeAllDelete() throws Exception {
       	if(!deleteAllProgrmDtls()){return false;} // 프로그램변경요청 테이블
       	if(!deleteAllMenuList()){return false;}   // 메뉴정보 테이블
       	if(!deleteAllProgrm()){return false;}     // 프로그램목록 테이블
       	return true;
	}
	
	

	/**
	 * 메뉴정보 전체데이타 초기화
	 * @return boolean
	 * @exception Exception
	 */
	private boolean deleteAllMenuList() throws Exception {
		return menuManageDAO.deleteAllMenuList();
	}

	/**
	 * 프로그램 정보를 등록
	 * @param  vo ProgrmManageVO
	 * @return boolean
	 * @exception Exception
	 */
	private boolean insertProgrm(ProgrmManageVO vo) throws Exception {
		progrmManageDAO.insertProgrm(vo);
    	return true;
	}

	/**
	 * 메뉴정보를 일괄 등록
	 * @param  vo MenuManageVO 
	 * @return boolean
	 * @exception Exception
	 */
	private boolean insertMenuManageBind(MenuManageVO vo) throws Exception {
		menuManageDAO.insertMenuManage(vo);
    	return true;
	}
	
	/**
	 * 프로그램 정보 전체데이타 초기화
	 * @return boolean
	 * @exception Exception
	 */
	private boolean deleteAllProgrm() throws Exception {
		progrmManageDAO.deleteAllProgrm();
		return true;
	}
	
	/**
	 * 프로그램변경내역 정보 전체데이타 초기화
	 * @return boolean
	 * @exception Exception
	 */
	private boolean deleteAllProgrmDtls() throws Exception {
		progrmManageDAO.deleteAllProgrmDtls();
		return  true;
	}
}