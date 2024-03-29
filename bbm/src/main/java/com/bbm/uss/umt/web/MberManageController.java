package com.bbm.uss.umt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.bbm.cmm.ComDefaultCodeVO;
import com.bbm.cmm.annotation.IncludedInfo;
import com.bbm.cmm.service.EgovCmmUseService;
import com.bbm.uss.umt.service.MberManageService;
import com.bbm.uss.umt.service.MberManageVO;
import com.bbm.uss.umt.service.UserDefaultVO;
import com.bbm.util.sim.service.EgovFileScrty;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;



/**
 * 일반회원관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * </pre>
 */

@Controller
public class MberManageController {

    /** mberManageService */
    @Resource(name = "mberManageService")
    private MberManageService mberManageService;
    
    /** cmmUseService */
    @Resource(name="EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** Log Info */
    protected Log log = LogFactory.getLog(this.getClass());
    
    /** DefaultBeanValidator beanValidator */
    @Autowired
	private DefaultBeanValidator beanValidator;
    
    /**
     * 일반회원목록을 조회한다. (pageing)
     * @param userSearchVO 검색조건정보
     * @param model 화면모델
     * @return uss/umt/MberManage
     * @throws Exception
     */
    @IncludedInfo(name="일반회원관리", order = 470 ,gid = 50)
    @RequestMapping(value="/uss/umt/MberManage.do")
    public String selectMberList(
    		@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
            ModelMap model
            )throws Exception {
        /** EgovPropertyService.sample */
    	userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	userSearchVO.setPageSize(propertiesService.getInt("pageSize"));
        
        /** pageing */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
        paginationInfo.setPageSize(userSearchVO.getPageSize());
        
        userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        List mberList = mberManageService.selectMberList(userSearchVO);
        model.addAttribute("resultList", mberList);
        
        int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        //일반회원 상태코드를 코드정보로부터 조회
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM013");
        List mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
        model.addAttribute("entrprsMberSttus_result",mberSttus_result);//기업회원상태코드목록
        
        return "bbm/uss/umt/MberManage";
    } 
    
    /**
     * 일반회원등록화면으로 이동한다.
     * @param userSearchVO 검색조건정보
     * @param mberManageVO 일반회원초기화정보
     * @param model 화면모델
     * @return uss/umt/MberInsert
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberInsertView.do")
    public String insertMberView(
            @ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
            @ModelAttribute("mberManageVO") MberManageVO mberManageVO,
            Model model
            )throws Exception {
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        
        //패스워드힌트목록을 코드정보로부터 조회
        vo.setCodeId("COM022");
        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
        //성별구분코드를 코드정보로부터 조회
        vo.setCodeId("COM014");
        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
        //사용자상태코드를 코드정보로부터 조회
        vo.setCodeId("COM013");
        List mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);        
        //그룹정보를 조회 - GROUP_ID정보
        vo.setTableNm("COMTNORGNZTINFO");
        List groupId_result = cmmUseService.selectGroupIdDetail(vo);
        
        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
        model.addAttribute("mberSttus_result",  mberSttus_result);  //사용자상태코드목록
        model.addAttribute("groupId_result",         groupId_result);        //그룹정보 목록
        
 
        
        return "bbm/uss/umt/MberInsert";
    }
    
    /**
     * 일반회원등록처리후 목록화면으로 이동한다.
     * @param mberManageVO 일반회원등록정보
     * @param bindingResult 입력값검증용 bindingResult 
     * @param model 화면모델
     * @return forward:/uss/umt/MberManage.do
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberInsert.do")
    public String insertMber(
            @ModelAttribute("entrprsManageVO") MberManageVO mberManageVO, 
            BindingResult bindingResult,
            Model model
            )throws Exception {
    	
        beanValidator.validate(mberManageVO, bindingResult);
    	if (bindingResult.hasErrors()){
    		return "bbm/uss/umt/MberInsert";
		}else{
			if(mberManageVO.getGroupId().equals("")){
				mberManageVO.setGroupId(null);
			}
			mberManageService.insertMber(mberManageVO);
	        //Exception 없이 진행시 등록 성공메시지
	        model.addAttribute("resultMsg", "success.common.insert");
		}
    	return "forward:/uss/umt/MberManage.do";
    }
   
    /**
     * 일반회원정보 수정을 위해 일반회원정보를 상세조회한다.
     * @param mberId 상세조회대상 일반회원아이디
     * @param userSearchVO 검색조건
     * @param model 화면모델
     * @return uss/umt/MberSelectUpdt
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberSelectUpdtView.do")
    public String updateMberView(
            @RequestParam("selectedId") String mberId ,
            @ModelAttribute("searchVO") UserDefaultVO userSearchVO, 
            Model model
            )throws Exception {
        
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        
        //패스워드힌트목록을 코드정보로부터 조회
        vo.setCodeId("COM022");
        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
        
        //성별구분코드를 코드정보로부터 조회
        vo.setCodeId("COM014");
        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
        
        //사용자상태코드를 코드정보로부터 조회
        vo.setCodeId("COM013");
        List mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);     

        //그룹정보를 조회 - GROUP_ID정보
        vo.setTableNm("COMTNORGNZTINFO");
        List groupId_result = cmmUseService.selectGroupIdDetail(vo);
        
        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
        model.addAttribute("mberSttus_result",  mberSttus_result);  //사용자상태코드목록
        model.addAttribute("groupId_result",         groupId_result);        //그룹정보 목록
        
        MberManageVO mberManageVO = mberManageService.selectMber(mberId);
        model.addAttribute("mberManageVO", mberManageVO);
        model.addAttribute("userSearchVO", userSearchVO);
        
        return "bbm/uss/umt/MberSelectUpdt";
    }
    
    /**
     * 일반회원정보 수정후 목록조회 화면으로 이동한다.
     * @param mberManageVO 일반회원수정정보
     * @param bindingResult 입력값검증용 bindingResult
     * @param model 화면모델
     * @return forward:/uss/umt/MberManage.do
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberSelectUpdt.do")
    public String updateMber(
            @ModelAttribute("mberManageVO") MberManageVO mberManageVO,
            BindingResult bindingResult,
            Model model
            )throws Exception {
        
        beanValidator.validate(mberManageVO, bindingResult);
    	if (bindingResult.hasErrors()){
    		return "bbm/uss/umt/EgovEntrprsMberSelectUpdt";
		}else{
			if(mberManageVO.getGroupId().equals("")){
				mberManageVO.setGroupId(null);
			}
			mberManageService.updateMber(mberManageVO);
	        //Exception 없이 진행시 수정성공메시지
	        model.addAttribute("resultMsg", "success.common.update");
	        return "forward:/uss/umt/MberManage.do";
		}
    }
    
    /**
     * 일반회원정보삭제후 목록조회 화면으로 이동한다.
     * @param checkedIdForDel 삭제대상 아이디 정보
     * @param userSearchVO 검색조건정보
     * @param model 화면모델
     * @return forward:/uss/umt/MberManage.do
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberDelete.do")
    public String deleteMber(
            @RequestParam("checkedIdForDel") String checkedIdForDel ,
            @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
            Model model
            )throws Exception {
        mberManageService.deleteMber(checkedIdForDel);
        //Exception 없이 진행시 삭제성공메시지
        model.addAttribute("resultMsg", "success.common.delete");
        return "forward:/uss/umt/MberManage.do";
    }
    
    /**
     * 일반회원가입신청 등록화면으로 이동한다.
     * @param userSearchVO 검색조건
     * @param mberManageVO 일반회원가입신청정보
     * @param commandMap 파라메터전달용 commandMap
     * @param model 화면모델
     * @return uss/umt/MberSbscrb
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberSbscrbView.do")
    public String sbscrbMberView(
            @ModelAttribute("userSearchVO") UserDefaultVO userSearchVO,
            @ModelAttribute("mberManageVO") MberManageVO mberManageVO,
            Map<String, Object> commandMap, 
            Model model
            )throws Exception {
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        
        //패스워드힌트목록을 코드정보로부터 조회
        vo.setCodeId("COM022");
        List passwordHint_result = cmmUseService.selectCmmCodeDetail(vo);
        //성별구분코드를 코드정보로부터 조회
        vo.setCodeId("COM014");
        List sexdstnCode_result = cmmUseService.selectCmmCodeDetail(vo);
                
        model.addAttribute("passwordHint_result",      passwordHint_result);     //패스워트힌트목록
        model.addAttribute("sexdstnCode_result",       sexdstnCode_result);      //성별구분코드목록
        if(!"".equals((String)commandMap.get("realname"))){
        	model.addAttribute("mberNm",   (String)commandMap.get("realname"));    //실명인증된 이름 - 주민번호 인증
            model.addAttribute("ihidnum",   (String)commandMap.get("ihidnum"));      //실명인증된 주민등록번호 - 주민번호 인증
        }
        if(!"".equals((String)commandMap.get("realName"))){
        	model.addAttribute("mberNm",   (String)commandMap.get("realName"));      //실명인증된 이름 - ipin인증
        }
        
        //mberManageVO.setGroupId("DEFAULT");
        mberManageVO.setMberSttus("DEFAULT");
        
       
        
        return "bbm/uss/umt/MberSbscrb";
    }
    
    /**
     * 일반회원가입신청등록처리후로그인화면으로 이동한다.
     * @param userManageVO - 신규일반회원정보 , 검색조건정보
     * @param status - 세션상태정보
     * @return "forward:/uss/umt/EgovUserManage.do"
     * @exception Exception
     */    
    
    /**
     * 일반회원가입신청등록처리후로그인화면으로 이동한다.
     * @param mberManageVO 일반회원가입신청정보
     * @return forward:/uat/uia/egovLoginUsr.do
     * @throws Exception
     */
    @RequestMapping("/uss/umt/MberSbscrb.do")
    public String sbscrbMber(
            @ModelAttribute("mberManageVO") MberManageVO mberManageVO)
            throws Exception {
    	
    	//가입상태 초기화
    	mberManageVO.setMberSttus("A");
    	//그룹정보 초기화
    	//mberManageVO.setGroupId("1");
    	//일반회원가입신청 등록시 일반회원등록기능을 사용하여 등록한다. 
    	mberManageService.insertMber(mberManageVO);
        return "forward:/uat/uia/egovLoginUsr.do";
    }
    
    /**
     * 일반회원 약관확인
     * @param model 화면모델
     * @return uss/umt/EgovStplatCnfirm
     * @throws Exception
     */
    @RequestMapping("/uss/umt/EgovStplatCnfirmMber.do")
    public String sbscrbEntrprsMber(Model model)
            throws Exception {
        //일반회원용 약관 아이디 설정
        String stplatId = "STPLAT_0000000000001";
        //회원가입유형 설정-일반회원
        String sbscrbTy = "USR01";
        //약관정보 조회 
        List stplatList = mberManageService.selectStplat(stplatId);
        model.addAttribute("stplatList",      stplatList);   //약관정보 포함
        model.addAttribute("sbscrbTy"  ,      sbscrbTy);     //회원가입유형 포함
        
        return "bbm/uss/umt/EgovStplatCnfirm";
    }
    
    /**
     * @param model 화면모델
     * @param commandMap 파라메터전달용 commandMap
     * @param userSearchVO 검색조건
     * @param mberManageVO 일반회원수정정보(비밀번호)
     * @return uss/umt/MberPasswordUpdt
     * @throws Exception
     */
    @RequestMapping(value="/uss/umt/MberPasswordUpdt.do")
    public String updatePassword(ModelMap model, 
    		  					 Map<String, Object> commandMap,
    		  					 @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
    		  					 @ModelAttribute("mberManageVO") MberManageVO mberManageVO) 
    							throws Exception {
    	String oldPassword = (String)commandMap.get("oldPassword");
        String newPassword = (String)commandMap.get("newPassword");
        String newPassword2 = (String)commandMap.get("newPassword2");
        String uniqId = (String)commandMap.get("uniqId");
        
        boolean isCorrectPassword=false;
        MberManageVO resultVO = new MberManageVO();
        mberManageVO.setPassword(newPassword);
        mberManageVO.setOldPassword(oldPassword);
        mberManageVO.setUniqId(uniqId);
        
    	String resultMsg = "";
    	resultVO = mberManageService.selectPassword(mberManageVO);
    	//패스워드 암호화
		String encryptPass = EgovFileScrty.encryptPassword(oldPassword);
    	if (encryptPass.equals(resultVO.getPassword())){
    		if (newPassword.equals(newPassword2)){
        		isCorrectPassword = true;
        	}else{
        		isCorrectPassword = false;
        		resultMsg="fail.user.passwordUpdate2";
        	}
    	}else{
    		isCorrectPassword = false;
    		resultMsg="fail.user.passwordUpdate1";
    	}
    	
    	if (isCorrectPassword){
    		mberManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword));
    		mberManageService.updatePassword(mberManageVO);
            model.addAttribute("mberManageVO", mberManageVO);      
            resultMsg = "success.common.update";
        }else{
        	model.addAttribute("mberManageVO", mberManageVO);      
        }
    	model.addAttribute("userSearchVO", userSearchVO); 
    	model.addAttribute("resultMsg", resultMsg);
        
        return "bbm/uss/umt/MberPasswordUpdt";
    }
    
    /**
     * 일반회원 암호 수정 화면 이동
     * @param model 화면모델
     * @param commandMap 파라메터전달용 commandMap
     * @param userSearchVO 검색조건
     * @param mberManageVO 일반회원수정정보(비밀번호)
     * @return uss/umt/MberPasswordUpdt
     * @throws Exception
     */
    @RequestMapping(value="/uss/umt/MberPasswordUpdtView.do")
    public String updatePasswordView(ModelMap model, 
    								Map<String, Object> commandMap,
    								@ModelAttribute("searchVO") UserDefaultVO userSearchVO,
    								@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {
    	String userTyForPassword = (String)commandMap.get("userTyForPassword");
    	mberManageVO.setUserTy(userTyForPassword);
    	
        model.addAttribute("userSearchVO", userSearchVO);
        model.addAttribute("mberManageVO", mberManageVO);
    	return "bbm/uss/umt/MberPasswordUpdt";
    }
        
}