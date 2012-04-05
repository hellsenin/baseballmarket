package com.bbm.sec.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bbm.cmm.EgovMessageSource;
import com.bbm.cmm.LoginVO;
import com.bbm.cmm.util.EgovUserDetailsHelper;
import com.bbm.uat.uia.service.EgovLoginService;


/**
 * 
 * @author 공통서비스 개발팀 서준식
 * @since 2011. 8. 29.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information) 
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011. 8. 29.    서준식        최초생성
 *  
 *  </pre>
 */

public class EgovSpringSecurityLoginFilter implements Filter{
private FilterConfig config;
	
	protected final static Log LOG = LogFactory.getLog(EgovSpringSecurityLoginFilter.class);

	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//로그인 URL
		String loginURL = config.getInitParameter("loginURL");
		loginURL = loginURL.replaceAll("\r", "").replaceAll("\n", "");	// 2011.10.25 보안점검 후속조치
		
		ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		EgovLoginService loginService = (EgovLoginService) act.getBean("loginService");
		EgovMessageSource egovMessageSource = (EgovMessageSource)act.getBean("egovMessageSource");
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = httpRequest.getSession();
		//String isLocallyAuthenticated = (String)session.getAttribute("isLocallyAuthenticated");
		String isRemotelyAuthenticated = (String)session.getAttribute("isRemotelyAuthenticated");
		
		String requestURL = ((HttpServletRequest)request).getRequestURI();
		
		
		
		
		//스프링 시큐리티 인증이 처리 되었는지 EgovUserDetailsHelper.getAuthenticatedUser() 메서드를 통해 확인한다.
		//context-common.xml 빈 설정에 egovUserDetailsSecurityService를 등록 해서 사용해야 정상적으로 동작한다.
		if(EgovUserDetailsHelper.getAuthenticatedUser() == null){
			
			if(isRemotelyAuthenticated != null && isRemotelyAuthenticated.equals("true")){
				try{
					//세션 토큰 정보를 가지고 DB로부터 사용자 정보를 가져옴
					LoginVO loginVO = (LoginVO)session.getAttribute("loginVOForDBAuthentication");				
					loginVO = loginService.actionLoginByEsntlId(loginVO);
					
					if(loginVO != null && loginVO.getId() != null && !loginVO.getId().equals("")){				
						//세션 로그인 
						session.setAttribute("loginVO", loginVO);
						
						//로컬 인증결과 세션에 저장
						session.setAttribute("isLocallyAuthenticated", "true");
			
						//스프링 시큐리티 로그인 
						httpResponse.sendRedirect(httpRequest.getContextPath() + "/j_spring_security_check?j_username=" + loginVO.getUserSe() + loginVO.getId() + "&j_password=" + loginVO.getUniqId());	
					
						return;
					}
					
				}catch(Exception ex){
					//DB인증 예외가 발생할 경우 로그를 남기고 로컬인증을 시키지 않고 그대로 진행함.
					LOG.debug("Local authentication Fail : " + ex.getMessage());
				}
				
			}else if(isRemotelyAuthenticated == null){
				if(requestURL.contains("/uat/uia/actionLogin")){
	
					String password = httpRequest.getParameter("password");
					//2011.10.11 보안점검 후속 조치(Password 검증)
					if(password == null || password.equals("") || password.length() < 8 || password.length() > 20){
						httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.password"));
						RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
						dispatcher.forward(httpRequest, httpResponse);
						
						chain.doFilter(request, response);
	
						return;
					}
					
					LoginVO loginVO = new LoginVO();
					
					loginVO.setId(httpRequest.getParameter("id"));
					loginVO.setPassword(password);
					loginVO.setUserSe(httpRequest.getParameter("userSe"));
					
					try {
						
						//사용자 입력 id, password로 DB 인증을 실행함
						loginVO = loginService.actionLogin(loginVO);
						
						if(loginVO != null && loginVO.getId() != null && !loginVO.getId().equals("")){
							//세션 로그인 
							session.setAttribute("loginVO", loginVO);
							
							//로컬 인증결과 세션에 저장
							session.setAttribute("isLocallyAuthenticated", "true");
				
							//스프링 시큐리티 로그인 
							httpResponse.sendRedirect(httpRequest.getContextPath() + "/j_spring_security_check?j_username=" + loginVO.getUserSe() + loginVO.getId() + "&j_password=" + loginVO.getUniqId());
						}else{
							//사용자 정보가 없는 경우 로그인 화면으로 redirect 시킴
							httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login"));
							RequestDispatcher dispatcher = httpRequest.getRequestDispatcher( loginURL);
							dispatcher.forward(httpRequest, httpResponse);
							chain.doFilter(request, response);
							
							return;
							
						}
	
					} catch (Exception ex) {
						//DB인증 예외가 발생할 경우 로그인 화면으로 redirect 시킴
						LOG.error("Login Exception : " + ex.getCause());
						httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login"));
						RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
						dispatcher.forward(httpRequest, httpResponse);
						chain.doFilter(request, response);
						
						return;
						
					}
					return;		
				}

			}
		}
		
			
		chain.doFilter(request, response);
		
		
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {

		this.config = filterConfig;

		
	}
}
