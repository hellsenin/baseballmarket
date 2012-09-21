<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>베이스볼마켓</title>
	
	
	<link rel="shortcut icon" href="/images/jq/favicon.ico" />
	<link rel="icon" href="/images/jq/favicon.ico" type="image/x-icon" />
			<link rel="stylesheet" href="/css/base.css" type="text/css" media="all" />
			<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.23/themes/base/jquery-ui.css" type="text/css" media="all" />
			<link rel="stylesheet" href="http://static.jquery.com/ui/css/demo-docs-theme/ui.theme.css" type="text/css" media="all" />
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script>
			<script src="http://code.jquery.com/ui/1.8.23/jquery-ui.min.js" type="text/javascript"></script>
			<script src="http://jquery-ui.googlecode.com/svn/tags/latest/external/jquery.bgiframe-2.1.2.js" type="text/javascript"></script>
			<script src="http://jquery-ui.googlecode.com/svn/tags/latest/ui/minified/i18n/jquery-ui-i18n.min.js" type="text/javascript"></script>
			<script src="/js/demos.js" type="text/javascript"></script>
			<script src="/js/themeswitchertool.js" type="text/javascript"></script>
		<style type="text/css">
	
	#jq-books{width:200px;float:right;margin-right:0}
	#jq-books li{line-height:1.25em;margin:1em 0 2.8em;clear:left}
	#home-content-wrapper #jq-books a.jq-bookImg{float:left;margin-right:10px;width:55px;height:70px}
	#jq-books h3{margin:0 0 .2em 0}
	#home-content-wrapper #jq-books h3 a{font-size:1em;color:black;}
	#home-content-wrapper #jq-books a.jq-buyNow{font-size:1em;color:white;display:block;background:url(http://static.jquery.com/files/rocker/images/btn_blueSheen.gif) 50% repeat-x;text-decoration:none;color:#fff;font-weight:bold;padding:.2em .8em;float:left;margin-top:.2em;}
	
	/*search box*/
	#jq-primarySearchForm {
	position: absolute;
	right: 20px;
	top: 100px;
	}
	#jq-primarySearchForm label {
	font-size: 1.3em;
	font-style: italic;
	font-weight: bold;
	color: #fff;
	line-height: 1;
	margin-right: .5em;
	}
	
	#jq-primarySearchForm input#jq-primarySearch {
	width: 200px;
	height: 19px;
	padding: 2px 5px;
	font-size: 1.4em;
	color: #333;
	background: #fff;
	border: 1px solid #aaa;
	line-height: 1;
	font-size: 1.5em;
	-moz-border-radius: 10px;
	}
	#jq-primarySearchForm button {
	background: url(/images/jq/icon_searchglass.png) 0 0 no-repeat;
	_background-image: url(/images/jq/icon_searchglass.gif);
	position: relative;
	width: 35px; 
	height: 35px;
	position: absolute;
	right: -18px;
	top: -1px;
	border: 0;
	cursor: pointer;
	font-size: 1.1em;
	}
	#jq-primarySearchForm button span {
	position: absolute;
	left: -9999999px;
	}
	#jq-primarySearchForm button:hover {
	background-position: 0 -36px;
	}
	
	</style>
</head>

<body id="demos">
<!--[if IE 5]><div id="ie5" class="ie"><![endif]-->
<!--[if IE 6]><div id="ie6" class="ie"><![endif]-->
<!--[if IE 7]><div id="ie7" class="ie"><![endif]-->
<div id="wrapper">
	<div id="banner">
		<h1 class="logo">
			<a href="/home" title="jQuery UI"><span>jQuery UI</span></a>
		</h1>
		<div id="dock">
			<div class="left"></div>
			<ul>
				<li>
					<a href="http://jquery.com"></a>
				</li>
				<li class="selected" style="padding-right: 12px;">
					<a href="http://plugins.jquery.com/">Main</a>
				</li>
				<li  style="padding-right: 12px;">
					<a href="/home">로그인</a>
				</li>
				<li style="padding-left: 12px;">
					<a href="http://blog.jqueryui.com/">회원가입</a>
				</li>
				<li>
					<a href="/about">아이디찾기</a>
				</li>
				
			</ul>
			<div class="right"></div>
		</div>
		<div id="navigation">
			<div class="left"></div>
			<ul>
				<li >
					<a href="/download">공지사항</a>
				</li>
				<li class="selected">
					<a href="/demos">이벤트</a>
				</li>
				<li >
					<a href="/themeroller">공동구매</a>
				</li>
				<li >
					<a href="/development">물품등록</a>
				</li>
			</ul>
			<div class="right"></div>
			
		</div>
		
		<form id="jq-primarySearchForm" action="/Special:Search">
			<div>
				<input type="hidden" value="1" name="ns0">
				<!-- <label for="primarySearch"> <span class="jq-jquery">물품검색</span></label> -->
				<input type="text" value="" accesskey="f" title="Search jQuery" name="search" id="jq-primarySearch">
				<button type="submit" id="jq-searchGoButton"><span>Go</span></button>
			</div>
		</form>
		
	</div>
	<div id="content-wrapper">
		<div id="content">
		<div class="content-top"></div>		<div class="content">
<script type="text/javascript">
var section = "demos/draggable";
</script>

<div class="content-body">
	<table cellspacing="0" cellpadding="0" class="layout-grid">
		<tr>
			<td>
				<!DOCTYPE html>



<table class="layout-grid" cellspacing="0" cellpadding="0">
	<tr>
		<td class="left-nav">
			
			<dl class="demos-nav">
				
					<dd>
					
					
					</dd>
				
				
									
				<dt>야구용품 중고장터</dt>
					<dd><a href="/demos/accordion">배트</a></dd>
					<dd><a href="/demos/autocomplete">글러브</a></dd>
					<dd><a href="/demos/button">스파이크, 신발</a></dd>
					<dd><a href="/demos/datepicker">배팅, 수비 장갑</a></dd>
					<dd><a href="/demos/dialog">보호장비</a></dd>
					<dd><a href="/demos/progressbar">의류, 가방</a></dd>
					<dd><a href="/demos/slider">고글, 팔찌, 목걸이</a></dd>
					<dd><a href="/demos/slider">좌투, 우타 용품</a></dd>
					<dd><a href="/demos/tabs">기타장비</a></dd>
				<dt>커뮤니티</dt>
					<dd><a href="/demos/draggable">공지사항</a></dd>
					<dd><a href="/demos/droppable">자유게시판</a></dd>
					<dd><a href="/demos/droppable">이벤트</a></dd>
					<dd><a href="/demos/droppable">사기성글 신고</a></dd>

			</dl>
		</td>
		<td class="normal">

			
						<div id="demo-header">
							<img src="/images/tmp_img/728x90_WBC13.jpg" alt="" width="700" />
						</div>
						<!-- <div style="position: absolute; right: 27px" id="switcher"></div> -->
						<div id="demo-config">
							<div id="demo-frame-wrapper">
								<p id="demo-link"><a href="/demos/draggable/default.html" target="_blank"><span class="ui-icon ui-icon-newwin"></span>New window</a></p>
								<div id="demo-frame">
									<!DOCTYPE html>


	
	
	
	
	
	
	
	
	
	<style>
	#draggable { width: 150px; height: 150px; padding: 0.5em; }
	</style>
	<script>
	$(function() {
		$( "#draggable" ).draggable();
	});
	</script>



<div class="demo">

<div id="draggable" class="ui-widget-content">
	<p>Drag me around</p>
</div>

</div><!-- End demo -->



<div class="demo-description">
<p>Enable draggable functionality on any DOM element. Move the draggable object by clicking on it with the mouse and dragging it anywhere within the viewport.</p>
</div><!-- End demo-description -->




								</div>									
							</div>

							<div id="demo-config-menu">
								<!DOCTYPE html>


	
	
	



<div class="demos-nav">
	<h4>Examples</h4>
	<ul>
		<li class="demo-config-on"><a href="/demos/draggable/default.html">Default functionality</a></li>
		<li><a href="/demos/draggable/events.html">Events</a></li>
		<li><a href="/demos/draggable/constrain-movement.html">Constrain movement</a></li>
		<li><a href="/demos/draggable/delay-start.html">Delay start</a></li>
		<li><a href="/demos/draggable/snap-to.html">Snap to element or&#160;grid</a></li>
		<li><a href="/demos/draggable/scroll.html">Auto-scroll</a></li>
		<li><a href="/demos/draggable/revert.html">Revert position</a></li>
		<li><a href="/demos/draggable/visual-feedback.html">Visual feedback</a></li>
		<li><a href="/demos/draggable/handle.html">Drag handle</a></li>
		<li><a href="/demos/draggable/cursor-style.html">Cursor style</a></li>
		<li><a href="/demos/draggable/sortable.html">Draggable + Sortable</a></li>
	</ul>
</div>




							</div>
						</div>
					
<ul class="UIAPIPlugin-toc">
<li><a href="#overview">Overview</a></li>
<li><a href="#options">Options</a></li>
<li><a href="#events">Events</a></li>
<li><a href="#methods">Methods</a></li>
<li><a href="#theming">Theming</a></li>
</ul>
<div class="UIAPIPlugin" id="widget-docs">
  <ul><li><a href="#overview">Overview</a></li><li><a href="#options">Options</a></li><li><a href="#events">Events</a></li><li><a href="#methods">Methods</a></li><li><a href="#theming">Theming</a></li></ul>
  <div id="overview">
    <h2 class="top-header">Overview</h2>
    <div id="overview-main">
        <p>The jQuery UI Draggable plugin makes selected elements draggable by mouse.</p>
<p>Draggable elements gets a class of <code>ui-draggable</code>. During drag the element also gets a class of <code>ui-draggable-dragging</code>. If you want not just drag, but drag-and-drop, see the jQuery UI Droppable plugin, which provides a drop target for draggables.</p>
<p>All callbacks (start, stop, drag) receive two arguments: The original browser event and a prepared ui object, view below for a documentation of this object (if you name your second argument 'ui'):</p>
<ul>
<li><b>ui.helper</b> - the jQuery object representing the helper that's being dragged</li>
<li><b>ui.position</b> - current position of the helper as { top, left } object, relative to the offset element</li>
<li><b>ui.offset</b> - current absolute position of the helper as { top, left } object, relative to page</li>
</ul>
<p><br />
</p>
<p>To manipulate the position of a draggable during drag, you can either <a href="http://jsbin.com/etako/edit" class="external text" title="http://jsbin.com/etako/edit">use a wrapper as the draggable helper</a> and position the wrapped element with absolute positioning, or  you can correct internal values like so: <code>$(this).data('draggable').offset.click.top -= x</code>.</p>
    </div>
    <div id="overview-dependencies">
        <h3>Dependencies</h3>
        <ul>
<li>UI Core</li>
<li>UI Widget</li>
<li>UI Mouse</li>
</ul>
    </div>
    <div id="overview-example">
        <h3>Example</h3>
        <div id="overview-example" class="example">
<ul><li><a href="#demo"><span>Demo</span></a></li><li><a href="#source"><span>View Source</span></a></li></ul>
<p><div id="demo" class="tabs-container" rel="170">
Initialize a draggable with default options.<br />
</p>
<pre>$(&quot;#draggable&quot;).draggable();
</pre>
<p></div><div id="source" class="tabs-container">
</p>
<pre>&lt;!DOCTYPE html&gt;
&lt;html&gt;
&lt;head&gt;
  &lt;link href=&quot;http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css&quot; rel=&quot;stylesheet&quot; type=&quot;text/css&quot;/&gt;
  &lt;script src=&quot;http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js&quot;&gt;&lt;/script&gt;
  &lt;script src=&quot;http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js&quot;&gt;&lt;/script&gt;
  &lt;style type=&quot;text/css&quot;&gt;
    #draggable { width: 100px; height: 70px; background: silver; }
  &lt;/style&gt;
  &lt;script&gt;
  $(document).ready(function() {
    $(&quot;#draggable&quot;).draggable();
  });
  &lt;/script&gt;
&lt;/head&gt;
&lt;body style="font-size:62.5%;"&gt;
  
&lt;div id=&quot;draggable&quot;&gt;Drag me&lt;/div&gt;

&lt;/body&gt;
&lt;/html&gt;
</pre>
<p></div>
</p><p></div>
    </div>
  </div>
  <div id="options">
    <h2 class="top-header">Options</h2>
    <ul class="options-list">
      
<li class="option" id="option-disabled">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-disabled">disabled</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Disables (true) or enables (false) the draggable. Can be set when initialising (first creating) the draggable.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>disabled</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ disabled: true });</code></pre>
</dd>

    
<dt>
  Get or set the <code>disabled</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var disabled = $( ".selector" ).draggable( "option", "disabled" );
//setter
$( ".selector" ).draggable( "option", "disabled", true );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-addClasses">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-addClasses">addClasses</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">true</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If set to false, will prevent the <code>ui-draggable</code> class from being added. This may be desired as a performance optimization when calling <code>.draggable()</code> init on many hundreds of elements.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>addClasses</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ addClasses: false });</code></pre>
</dd>

    
<dt>
  Get or set the <code>addClasses</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var addClasses = $( ".selector" ).draggable( "option", "addClasses" );
//setter
$( ".selector" ).draggable( "option", "addClasses", false );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-appendTo">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-appendTo">appendTo</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Element, Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">"parent"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>The element passed to or selected by the <code>appendTo</code> option will be used as the draggable helper's container during dragging. By default, the helper is appended to the same container as the draggable.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>appendTo</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ appendTo: "body" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>appendTo</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var appendTo = $( ".selector" ).draggable( "option", "appendTo" );
//setter
$( ".selector" ).draggable( "option", "appendTo", "body" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-axis">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-axis">axis</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">String</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Constrains dragging to either the horizontal (x) or vertical (y) axis. Possible values: 'x', 'y'.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>axis</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ axis: "x" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>axis</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var axis = $( ".selector" ).draggable( "option", "axis" );
//setter
$( ".selector" ).draggable( "option", "axis", "x" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-cancel">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-cancel">cancel</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">":input,option"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Prevents dragging from starting on specified elements.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>cancel</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ cancel: "button" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>cancel</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var cancel = $( ".selector" ).draggable( "option", "cancel" );
//setter
$( ".selector" ).draggable( "option", "cancel", "button" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-connectToSortable">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-connectToSortable">connectToSortable</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Allows the draggable to be dropped onto the specified sortables. If this option is used (<code>helper</code> must be set to 'clone' in order to work flawlessly), a draggable can be dropped onto a sortable list and then becomes part of it.
</p><p>Note: Specifying this option as an array of selectors has been removed.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>connectToSortable</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ connectToSortable: "ul#myList" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>connectToSortable</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var connectToSortable = $( ".selector" ).draggable( "option", "connectToSortable" );
//setter
$( ".selector" ).draggable( "option", "connectToSortable", "ul#myList" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-containment">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-containment">containment</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Selector, Element, String, Array</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Constrains dragging to within the bounds of the specified element or region. Possible string values: 'parent', 'document', 'window', [x1, y1, x2, y2].</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>containment</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ containment: "parent" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>containment</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var containment = $( ".selector" ).draggable( "option", "containment" );
//setter
$( ".selector" ).draggable( "option", "containment", "parent" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-cursor">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-cursor">cursor</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">String</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">"auto"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>The css cursor during the drag operation.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>cursor</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ cursor: "crosshair" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>cursor</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var cursor = $( ".selector" ).draggable( "option", "cursor" );
//setter
$( ".selector" ).draggable( "option", "cursor", "crosshair" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-cursorAt">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-cursorAt">cursorAt</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Object</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Sets the offset of the dragging helper relative to the mouse cursor. Coordinates can be given as a hash using a combination of one or two keys: <code>{ top, left, right, bottom }</code>.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>cursorAt</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ cursorAt: { left: 5 } });</code></pre>
</dd>

    
<dt>
  Get or set the <code>cursorAt</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var cursorAt = $( ".selector" ).draggable( "option", "cursorAt" );
//setter
$( ".selector" ).draggable( "option", "cursorAt", { left: 5 } );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-delay">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-delay">delay</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">0</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Time in milliseconds after mousedown until dragging should start. This option can be used to prevent unwanted drags when clicking on an element.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>delay</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ delay: 500 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>delay</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var delay = $( ".selector" ).draggable( "option", "delay" );
//setter
$( ".selector" ).draggable( "option", "delay", 500 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-distance">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-distance">distance</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">1</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Distance in pixels after mousedown the mouse must move before dragging should start. This option can be used to prevent unwanted drags when clicking on an element.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>distance</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ distance: 30 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>distance</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var distance = $( ".selector" ).draggable( "option", "distance" );
//setter
$( ".selector" ).draggable( "option", "distance", 30 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-grid">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-grid">grid</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Array</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Snaps the dragging helper to a grid, every x and y pixels. Array values: [x, y]</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>grid</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ grid: [50, 20] });</code></pre>
</dd>

    
<dt>
  Get or set the <code>grid</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var grid = $( ".selector" ).draggable( "option", "grid" );
//setter
$( ".selector" ).draggable( "option", "grid", [50, 20] );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-handle">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-handle">handle</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Element, Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If specified, restricts drag start click to the specified element(s).</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>handle</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ handle: "h2" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>handle</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var handle = $( ".selector" ).draggable( "option", "handle" );
//setter
$( ".selector" ).draggable( "option", "handle", "h2" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-helper">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-helper">helper</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">String, Function</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">"original"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Allows for a helper element to be used for dragging display. Possible values: 'original', 'clone', Function. If a function is specified, it must return a DOMElement.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>helper</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ helper: "clone" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>helper</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var helper = $( ".selector" ).draggable( "option", "helper" );
//setter
$( ".selector" ).draggable( "option", "helper", "clone" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-iframeFix">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-iframeFix">iframeFix</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean, Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Prevent iframes from capturing the mousemove events during a drag. Useful in combination with cursorAt, or in any case, if the mouse cursor is not over the helper. If set to true, transparent overlays will be placed over all iframes on the page. If a selector is supplied, the matched iframes will have an overlay placed over them.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>iframeFix</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ iframeFix: true });</code></pre>
</dd>

    
<dt>
  Get or set the <code>iframeFix</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var iframeFix = $( ".selector" ).draggable( "option", "iframeFix" );
//setter
$( ".selector" ).draggable( "option", "iframeFix", true );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-opacity">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-opacity">opacity</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Float</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Opacity for the helper while being dragged.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>opacity</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ opacity: 0.35 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>opacity</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var opacity = $( ".selector" ).draggable( "option", "opacity" );
//setter
$( ".selector" ).draggable( "option", "opacity", 0.35 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-refreshPositions">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-refreshPositions">refreshPositions</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If set to true, all droppable positions are calculated on every mousemove. Caution: This solves issues on highly dynamic pages, but dramatically decreases performance.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>refreshPositions</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ refreshPositions: true });</code></pre>
</dd>

    
<dt>
  Get or set the <code>refreshPositions</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var refreshPositions = $( ".selector" ).draggable( "option", "refreshPositions" );
//setter
$( ".selector" ).draggable( "option", "refreshPositions", true );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-revert">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-revert">revert</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean, String</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If set to true, the element will return to its start position when dragging stops. Possible string values: 'valid', 'invalid'. If set to invalid, revert will only occur if the draggable has not been dropped on a droppable. For valid, it's the other way around.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>revert</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ revert: true });</code></pre>
</dd>

    
<dt>
  Get or set the <code>revert</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var revert = $( ".selector" ).draggable( "option", "revert" );
//setter
$( ".selector" ).draggable( "option", "revert", true );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-revertDuration">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-revertDuration">revertDuration</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">500</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>The duration of the revert animation, in milliseconds. Ignored if revert is false.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>revertDuration</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ revertDuration: 1000 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>revertDuration</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var revertDuration = $( ".selector" ).draggable( "option", "revertDuration" );
//setter
$( ".selector" ).draggable( "option", "revertDuration", 1000 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-scope">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-scope">scope</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">String</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">"default"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Used to group sets of draggable and droppable items, in addition to droppable's accept option. A draggable with the same scope value as a droppable will be accepted by the droppable.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>scope</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ scope: "tasks" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>scope</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var scope = $( ".selector" ).draggable( "option", "scope" );
//setter
$( ".selector" ).draggable( "option", "scope", "tasks" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-scroll">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-scroll">scroll</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">true</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If set to true, container auto-scrolls while dragging.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>scroll</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ scroll: false });</code></pre>
</dd>

    
<dt>
  Get or set the <code>scroll</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var scroll = $( ".selector" ).draggable( "option", "scroll" );
//setter
$( ".selector" ).draggable( "option", "scroll", false );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-scrollSensitivity">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-scrollSensitivity">scrollSensitivity</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">20</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Distance in pixels from the edge of the viewport after which the viewport should scroll. Distance is relative to pointer, not the draggable.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>scrollSensitivity</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ scrollSensitivity: 40 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>scrollSensitivity</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var scrollSensitivity = $( ".selector" ).draggable( "option", "scrollSensitivity" );
//setter
$( ".selector" ).draggable( "option", "scrollSensitivity", 40 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-scrollSpeed">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-scrollSpeed">scrollSpeed</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">20</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>The speed at which the window should scroll once the mouse pointer gets within the <code>scrollSensitivity</code> distance.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>scrollSpeed</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ scrollSpeed: 40 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>scrollSpeed</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var scrollSpeed = $( ".selector" ).draggable( "option", "scrollSpeed" );
//setter
$( ".selector" ).draggable( "option", "scrollSpeed", 40 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-snap">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-snap">snap</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Boolean, Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>If set to a selector or to true (equivalent to '.ui-draggable'), the draggable will snap to the edges of the selected elements when near an edge of the element.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>snap</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ snap: true });</code></pre>
</dd>

    
<dt>
  Get or set the <code>snap</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var snap = $( ".selector" ).draggable( "option", "snap" );
//setter
$( ".selector" ).draggable( "option", "snap", true );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-snapMode">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-snapMode">snapMode</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">String</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">"both"</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Determines which edges of snap elements the draggable will snap to. Ignored if snap is false. Possible values: 'inner', 'outer', 'both'</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>snapMode</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ snapMode: "outer" });</code></pre>
</dd>

    
<dt>
  Get or set the <code>snapMode</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var snapMode = $( ".selector" ).draggable( "option", "snapMode" );
//setter
$( ".selector" ).draggable( "option", "snapMode", "outer" );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-snapTolerance">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-snapTolerance">snapTolerance</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">20</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>The distance in pixels from the snap element edges at which snapping should occur. Ignored if snap is false.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>snapTolerance</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ snapTolerance: 40 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>snapTolerance</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var snapTolerance = $( ".selector" ).draggable( "option", "snapTolerance" );
//setter
$( ".selector" ).draggable( "option", "snapTolerance", 40 );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-stack">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-stack">stack</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Selector</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>Controls the z-Index of the set of elements that match the selector, always brings to front the dragged item. Very useful in things like window managers.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>stack</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ stack: &quot;.products&quot; });</code></pre>
</dd>

    
<dt>
  Get or set the <code>stack</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var stack = $( ".selector" ).draggable( "option", "stack" );
//setter
$( ".selector" ).draggable( "option", "stack", &quot;.products&quot; );</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="option" id="option-zIndex">
  <div class="option-header">
    <h3 class="option-name"><a href="#option-zIndex">zIndex</a></h3>
    <dl>
      <dt class="option-type-label">Type:</dt>
        <dd class="option-type">Integer</dd>
      
      <dt class="option-default-label">Default:</dt>
        <dd class="option-default">false</dd>
      
    </dl>
  </div>
  <div class="option-description">
    <p>z-index for the helper while being dragged.</p>
  </div>
  <div class="option-examples">
    <h4>Code examples</h4>
    <dl class="option-examples-list">
    
<dt>
  Initialize a draggable with the <code>zIndex</code> option specified.
</dt>
<dd>
<pre><code>$( ".selector" ).draggable({ zIndex: 2700 });</code></pre>
</dd>

    
<dt>
  Get or set the <code>zIndex</code> option, after init.
</dt>
<dd>
<pre><code>//getter
var zIndex = $( ".selector" ).draggable( "option", "zIndex" );
//setter
$( ".selector" ).draggable( "option", "zIndex", 2700 );</code></pre>
</dd>

    </dl>
  </div>
</li>

    </ul>
  </div>
  <div id="events">
    <h2 class="top-header">Events</h2>
    <ul class="events-list">
      
<li class="event" id="event-create">
  <div class="event-header">
    <h3 class="event-name"><a href="#event-create">create</a></h3>
    <dl>
      <dt class="event-type-label">Type:</dt>
        <dd class="event-type">dragcreate</dd>
    </dl>
  </div>
  <div class="event-description">
    <p>This event is triggered when draggable is created.</p>
  </div>
  <div class="event-examples">
    <h4>Code examples</h4>
    <dl class="event-examples-list">
    
<dt>
  Supply a callback function to handle the <code>create</code> event as an init option.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).draggable({
   create: function(event, ui) { ... }
});</code></pre>
</dd>

    
<dt>
  Bind to the <code>create</code> event by type: <code>dragcreate</code>.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).bind( &quot;dragcreate&quot;, function(event, ui) {
  ...
});</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="event" id="event-start">
  <div class="event-header">
    <h3 class="event-name"><a href="#event-start">start</a></h3>
    <dl>
      <dt class="event-type-label">Type:</dt>
        <dd class="event-type">dragstart</dd>
    </dl>
  </div>
  <div class="event-description">
    <p>This event is triggered when dragging starts.</p>
  </div>
  <div class="event-examples">
    <h4>Code examples</h4>
    <dl class="event-examples-list">
    
<dt>
  Supply a callback function to handle the <code>start</code> event as an init option.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).draggable({
   start: function(event, ui) { ... }
});</code></pre>
</dd>

    
<dt>
  Bind to the <code>start</code> event by type: <code>dragstart</code>.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).bind( &quot;dragstart&quot;, function(event, ui) {
  ...
});</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="event" id="event-drag">
  <div class="event-header">
    <h3 class="event-name"><a href="#event-drag">drag</a></h3>
    <dl>
      <dt class="event-type-label">Type:</dt>
        <dd class="event-type">drag</dd>
    </dl>
  </div>
  <div class="event-description">
    <p>This event is triggered when the mouse is moved during the dragging.</p>
  </div>
  <div class="event-examples">
    <h4>Code examples</h4>
    <dl class="event-examples-list">
    
<dt>
  Supply a callback function to handle the <code>drag</code> event as an init option.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).draggable({
   drag: function(event, ui) { ... }
});</code></pre>
</dd>

    
<dt>
  Bind to the <code>drag</code> event by type: <code>drag</code>.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).bind( &quot;drag&quot;, function(event, ui) {
  ...
});</code></pre>
</dd>

    </dl>
  </div>
</li>


<li class="event" id="event-stop">
  <div class="event-header">
    <h3 class="event-name"><a href="#event-stop">stop</a></h3>
    <dl>
      <dt class="event-type-label">Type:</dt>
        <dd class="event-type">dragstop</dd>
    </dl>
  </div>
  <div class="event-description">
    <p>This event is triggered when dragging stops.</p>
  </div>
  <div class="event-examples">
    <h4>Code examples</h4>
    <dl class="event-examples-list">
    
<dt>
  Supply a callback function to handle the <code>stop</code> event as an init option.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).draggable({
   stop: function(event, ui) { ... }
});</code></pre>
</dd>

    
<dt>
  Bind to the <code>stop</code> event by type: <code>dragstop</code>.
</dt>
<dd>
<pre><code>$( &quot;.selector&quot; ).bind( &quot;dragstop&quot;, function(event, ui) {
  ...
});</code></pre>
</dd>

    </dl>
  </div>
</li>

    </ul>
  </div>
  <div id="methods">
    <h2 class="top-header">Methods</h2>
    <ul class="methods-list">
      
<li class="method" id="method-destroy">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-destroy">destroy</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "destroy"







)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Remove the draggable functionality completely. This will return the element back to its pre-init state.</p>
  </div>
</li>


<li class="method" id="method-disable">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-disable">disable</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "disable"







)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Disable the draggable.</p>
  </div>
</li>


<li class="method" id="method-enable">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-enable">enable</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "enable"







)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Enable the draggable.</p>
  </div>
</li>


<li class="method" id="method-option">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-option">option</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "option"

, optionName

, <span class="optional">[</span>value<span class="optional">] </span>



)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Get or set any draggable option. If no value is specified, will act as a getter.</p>
  </div>
</li>


<li class="method" id="method-option">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-option">option</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "option"

, options





)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Set multiple draggable options at once by providing an options object.</p>
  </div>
</li>


<li class="method" id="method-widget">
  <div class="method-header">
    <h3 class="method-name"><a href="#method-widget">widget</a></h3>
    <dl>
      <dt class="method-signature-label">Signature:</dt>
        <dd class="method-signature">.draggable( "widget"







)</dd>
    </dl>
  </div>
  <div class="method-description">
    <p>Returns the .ui-draggable element.</p>
  </div>
</li>


    </ul>
  </div>
  <div id="theming">
    <h2 class="top-header">Theming</h2>
    <p>The jQuery UI Draggable plugin uses the jQuery UI CSS Framework to style its look and feel, including colors and background textures. We recommend using the ThemeRoller tool to create and download custom themes that are easy to build and maintain.
</p>
  <p>If a deeper level of customization is needed, there are widget-specific classes referenced within the jquery.ui.draggable.css stylesheet that can be modified. These classes are highlighed in bold below.
</p>
    
  <h3>Sample markup with jQuery UI CSS Framework classes</h3>
  &lt;div class=&quot;<strong>ui-draggable</strong>&quot;&gt;&lt;/div&gt;
  <p class="theme-note">
    <strong>
      Note: This is a sample of markup generated by the draggable plugin, not markup you should use to create a draggable. The only markup needed for that is &lt;div&gt;&lt;/div&gt;.
    </strong>
  </p>

  </div>
</div>

</p><!-- 
Pre-expand include size: 61321 bytes
Post-expand include size: 105979 bytes
Template argument size: 58128 bytes
Maximum: 2097152 bytes
-->

<!-- Saved in parser cache with key jqdocs_docs:pcache:idhash:3768-1!1!0!!en!2 and timestamp 20120815135647 -->


		</td>
	</tr>
</table>


			</td>
		</tr>
	</table>
</div>
		</div>
		</div>
	</div>

	<div id="footer">
		<div class="bg"></div>
		<div class="inner">
			<p>
				<span class="first">Sponsored by: </span>
				<a class="block filamentgroup" href="http://www.filamentgroup.com"><span>Filamentgroup</span></a>
				<a href="http://appendto.com" class="block" style="background: url('/images/jq/icon_appendto.png'); width: 114px; height: 23px; border: 0; margin: 5px 22px 0px 3px;"><span>appendTo</span></a>
				<a href="http://browserstack.com" class="block" style="background: url('/images/jq/icon_browserstack.png'); width: 135px; height: 23px; border: 0; margin: 5px 22px 0px 3px;"><span>BrowserStack</span></a>
				<span class="first" style="float: right; padding-right: 12px;">&copy; 2012 <a href="http://jquery.org/">jQuery Foundation</a> and the <a href="/about">jQuery UI Team</a>.</span>
			</p>
		</div>
	</div>
</div>
<!--[if lte IE 7]></div><![endif]-->
<script type="text/javascript">
var _gaq = _gaq || []; _gaq.push(['_setAccount', 'UA-1076265-1']); _gaq.push(['_trackPageview']); _gaq.push(['_setDomainName', '.jqueryui.com']);
(function() {var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);})();
</script>
<script type="text/javascript">
$(document).ready(function() {
$('a').click(function(){
        this.blur();
});
});
</script>
</body>

</html>