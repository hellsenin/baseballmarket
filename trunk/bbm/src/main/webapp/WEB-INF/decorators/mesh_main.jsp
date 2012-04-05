<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />

	<title><decorator:title default="베이스볼마켓" /></title>
	<decorator:head/>
	<script type="text/javascript" src="/js/common/jquery/layout/jquery-latest.js"></script>
	<script type="text/javascript" src="/js/common/jquery/layout/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="/js/common/jquery/layout/jquery.layout.js"></script>
	<script type="text/javascript" src="/js/common/jquery/jquery.treeview.js" ></script>
	
	
	<script type="text/javascript">
	
	function toggleLiveResizing () {
		$.each('north,south,west,east'.split(','), function (i, pane) {
			var opts = myLayout.options[ pane ];
			opts.resizeWhileDragging = !opts.resizeWhileDragging;
		});
	};

	

	function toggleStateManagement ( skipAlert ) {
		var enable = !myLayout.options.useStateCookie; // OPPOSITE of current setting
		myLayout.options.useStateCookie = enable; // toggle option
		if (!enable) { // if disabling state management...
			myLayout.deleteCookie(); // ...clear cookie so will NOT be found on next refresh
			if (!skipAlert)
				alert( 'This layout will reload as options specify \nwhen the page is refreshed.' );
		}
		else if (!skipAlert)
			alert( 'This layout will save & restore its last state \nwhen the page is refreshed.' );
		// update text on button
		var $Btn = $('#btnToggleState'), text = $Btn.html();
		if (enable)
			$Btn.html( text.replace(/Enable/i, "Disable") );
		else
			$Btn.html( text.replace(/Disable/i, "Enable") );
	};



	// set EVERY 'state' here so will undo ALL layout changes

	// used by the 'Reset State' button: myLayout.loadState( stateResetSettings )

	var stateResetSettings = {
			north__size:		"auto"
		,	north__initClosed:	false
		,	north__initHidden:	false
		,	south__size:		"auto"
		,	south__initClosed:	false
		,	south__initHidden:	false
		,	west__size:			300
		,	west__initClosed:	false
		,	west__initHidden:	false
		,	east__size:			300
		,	east__initClosed:	false
		,	east__initHidden:	false
	};

	var myLayout;
	$(document).ready(function () {
		$("#left_menu").treeview();
		$("#right_menu").treeview();
		// this layout could be created with NO OPTIONS - but showing some here just as a sample...
		// myLayout = $('body').layout(); -- syntax with No Options
		myLayout = $('body').layout({
		//	enable showOverflow on west-pane so CSS popups will overlap north pane
			west__showOverflowOnHover: true
		//	reference only - these options are NOT required because 'true' is the default
		,	closable:				true	// pane can open & close
		,	resizable:				true	// when open, pane can be resized 
		,	slidable:				true	// when closed, pane can 'slide' open over other panes - closes on mouse-out
		//	some resizing/toggling settings
		,	north__slidable:		false	// OVERRIDE the pane-default of 'slidable=true'
		,	north__togglerLength_closed: '100%'	// toggle-button is full-width of resizer-bar
		,	north__spacing_closed:	20		// big resizer-bar when open (zero height)
		,	south__resizable:		false	// OVERRIDE the pane-default of 'resizable=true'
		,	south__spacing_open:	0		// no resizer-bar when open (zero height)
		,	south__spacing_closed:	20		// big resizer-bar when open (zero height)
		//	some pane-size settings
		,	west__size:				300
		,	west__minSize:			100
		,	east__size:				300
		,	east__minSize:			200
		,	east__maxSize:			Math.floor(screen.availWidth / 2) // 1/2 screen width
		,	center__minWidth:		100
		,	useStateCookie:			false
		});

		// if there is no state-cookie, then DISABLE state management initially

		var cookieExists = true;
		for (var key in myLayout.getCookie()) {
			cookieExists = true;
			break
		}

		if (!cookieExists) toggleStateManagement( true );
		// add event to the 'Close' button in the East pane dynamically...
		myLayout.addCloseBtn('#btnCloseEast', 'east');
		// add event to the 'Toggle South' buttons in Center AND South panes dynamically...
		myLayout.addToggleBtn('.south-toggler', 'south');
		// add MULTIPLE events to the 'Open All Panes' button in the Center pane dynamically...
		myLayout.addOpenBtn('#openAllPanes', 'north');
		myLayout.addOpenBtn('#openAllPanes', 'south');
		myLayout.addOpenBtn('#openAllPanes', 'west');
		myLayout.addOpenBtn('#openAllPanes', 'east');
		// 'Reset State' button requires updated functionality in rc29.15
		if ($.layout.revision && $.layout.revision >= 0.032915)
			$('#btnReset').show();
 	});
	</script>

	<link type="text/css" rel="stylesheet" href="/css/common/jquery/layout-default-latest.css" />
	<link type="text/css" rel="stylesheet" href="/css/common/jquery/jquery.treeview.css"/>
	<style type="text/css">
	/**
	 *	Basic Layout Theme
	 * 
	 *	This theme uses the default layout class-names for all classes
	 *	Add any 'custom class-names', from options: paneClass, resizerClass, togglerClass
	 */

	.ui-layout-pane { /* all 'panes' */ 
		background: #FFF; 
		border: 1px solid #BBB; 
		padding: 10px; 
		overflow: auto;
	} 
	.ui-layout-resizer { /* all 'resizer-bars' */ 
		background: #DDD; 
	} 
	.ui-layout-toggler { /* all 'toggler-buttons' */ 
		background: #AAA; 
	} 
	/**
	 *	ALL CSS below is only for cosmetic and demo purposes
	 *	Nothing here affects the appearance of the layout
	 */
	body {
		font-family: Arial, sans-serif;
		font-size: 0.85em;
	}
	p {
		margin: 1em 0;
	}
	/*
	 *	Rules below are for simulated drop-down/pop-up lists
	 */

	</style>
</head>
<body>
	
<!-- 상단영역 -->
<div class="ui-layout-north" onmouseover="myLayout.allowOverflow('north')" onmouseout="myLayout.resetOverflow(this)">
	 <form:form commandName="loginVO" name="loginForm"  method="post">
	<div id="login" align="left">
		아이디:<input name="userId" type="text" value="" size="15" maxlength="20">&nbsp;
		비밀번호:<input name="password" type="password" value="" size="15" maxlength="20">&nbsp;
		<button onClick="actionLogin()">로그인</button>
	<input name="devLoginAt" type="hidden" value="Y" >&nbsp;
	</div>
	</form:form>
	<ul>
		<li>배너 / 통합검색 / 인기검색어 / 추천검색어</li>
		<li>로그인/회원가입/아이디,비밀번호찾기</li>
	</ul>
	<button onclick="myLayout.toggle('north')">상단감추기</button>
</div>

<!-- 좌측영역 -->
<div class="ui-layout-west">
	<ul id="left_menu">
	 	<li><span class="folder">베이스볼 마켓</span>
			<ul>
				<li><span class="folder">시스템관리</span>
					<ul>
						<li><span class="file"><a href="#" >사용자관리</a></span></li>
						<li><span class="file"><a href="#" >그룹관리</a></span></li>
						<li><span class="file"><a href="#" >권한관리</a></span></li>
						<li><span class="file"><a href="#" >프로그램관리</a></span></li>
						<li><span class="file"><a href="#" >메뉴관리</a></span></li>
						<li><span class="file"><a href="#" >코드관리</a></span></li>
						<li><span class="file"><a href="#" >게시판관리/게시물관리</a></span></li>
						<li><span class="file"><a href="#" >카테고리관리(품목분류)</a></span></li>
						<li><span class="file"><a href="#" >배너관리/팝업관리</a></span></li>
						<li><span class="file"><a href="#" >이력관리</a></span></li>
					</ul>
				</li>
				
				<li><span class="folder">외부컨텐츠</span>
					<ul>
						<li><span class="file"><a href="#" >회원가입</a></span></li>
						<li><span class="file"><a href="#" >로그인</a></span></li>
						<li><span class="file"><a href="#" >정보수정</a></span></li>
						<li><span class="file"><a href="#" >아이디, 비밀번호찾기</a></span></li>
						<li><span class="file"><a href="#" >메인화면</a></span></li>
						<li><span class="file"><a href="#" >거래게시판(팝니다/삽니다/교환)</a></span></li>
						<li><span class="folder">커뮤니티</span>
							<ul>
								 <li><span class="file"><a href="#" >이용안내</a></span></li>
								 <li><span class="file"><a href="#" >FAQ</a></span></li>
								 <li><span class="file"><a href="#" >공지사항</a></span></li>
								 <li><span class="file"><a href="#" >자유게시판</a></span></li>
								 <li><span class="file"><a href="#" >사기신고</a></span></li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
		</li>
	</ul>

	<p><button onclick="myLayout.close('west')">좌측감추기</button></p>

</div>

<!-- 우측영역 -->
<div class="ui-layout-east">
	<ul>
		<li>현재접속자</li>
		<li>최근등록물품</li>
		<li>최근댓글</li>
		<li>배너및광고</li>
		<li>...</li>
	</ul>
	
	<p>
	==============================
	<p>
	 <h3>* 개발샘플</h3>
	<ul id="right_menu">
	 	<li><span class="folder">개발 샘플</span>
			<ul>
				<li><span class="folder">템플릿</span>
					<ul>
						 <li><span class="file"><a href="/sample/validator_help.do" >validate 템플릿</a></span></li>
						 <!-- 
						 <li><span class="file"><a href="/sample/tem/selectTemplateList.do" >게시판 템플릿</a></span></li>
						 <li><span class="file"><a href="/sample/tem/registerTemplateFile.do" >파일 업로드 템플릿1</a></span></li>
	 					 <li><span class="file"><a href="/sample/tem/selectTemplateJqGridList.do" >파일첨부 유형3(jqGrid)</a></span></li>					
						 <li><span class="file"><a href="/sample/tem/registerTemplateTable.do" >파일첨부 유형2</a></span></li> 
						 <li><span class="file"><a href="/sample/tem/selectTemplateList2.do" >동적 콤보박스 설정</a></span></li>					 
						 <li><span class="file"><a href="/sample/tem/dragndropSample.do" >Drag&Drop </a></span></li>
						  -->
					</ul>
				</li>
				
				
				
				<li><span class="folder">공통코드관리</span>
					<ul>
						<li><span class="file"><a href="/admin/ccm/ClCodeList.do" >공통분류코드</a></span></li>
						<li><span class="file"><a href="/admin/ccm/CodeList.do" >공통코드</a></span></li>
						<li><span class="file"><a href="/admin/ccm/DetailCodeList.do" >공통상세코드</a></span></li>
					</ul>
				</li>
				
				<li><span class="folder">메뉴관리</span>
					<ul>
						<li><span class="file"><a href="/admin/prm/ProgramListManageSelect.do" >프로그램관리</a></span></li>
						<li><span class="file"><a href="/admin/mnu/mpm/MenuListSelect.do" >메뉴관리</a></span></li>
						<li><span class="file"><a href="" ></a></span></li>
					</ul>
				</li>
				
				
			</ul>
		</li>
		
		
		
	</ul>
	
</div>

	<!-- 바디영역 -->
	<div class="ui-layout-center">
	<decorator:body/>
	</div>
	<!-- 바디영역 -->
	
<!-- 하단영역 -->
<div class="ui-layout-south">
	<ul>
		<li>회사/사이트소개 / 광고안내 / 제휴문의 / 배너및광고 / 상품카테고리 / copyright</li>
		<li>...</li>
	</ul>
	
</div>
	

	
</body>
</html>