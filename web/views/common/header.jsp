<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.vo.Member" %>
<%@ page import="com.kh.common.listener.SessionCheckListener" %>
<%
	/* Member m=(Member)request.getAttribute("loginedMember"); */
	Member loginMember=(Member)session.getAttribute("loginedMember");
	//cookie값 받아오기
	Cookie[] cookies=request.getCookies();
	String saveId=null;
	if(cookies!=null){
		for(Cookie c:cookies){
			String key=c.getName();
			String value=c.getValue();
			if(key.equals("saveId")){
				saveId=value;
			}
		}
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HelloMVC</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" type="text/css">
<!-- <script src="http://code.jquery.com/jquery-3.4.1.js"></script> -->
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			<div class="login-container">
				<!-- 헤더 메뉴 추가 - 로그인 메뉴-->
				<%if(loginMember==null) {%>
				<form id="loginFrm" action="<%=request.getContextPath() %>/login" method="post" 
				onsubmit="return fn_login_validate();">
					<table>
						<tr>
							<td>
								<input type="text" name="userId" id="userId" placeholder="아이디" 
								value="<%=saveId!=null?saveId:""%>">
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
								<input type="password" name="password" id="password" placeholder="비밀번호">
							</td>
							<td><input type="submit" value="로그인"></td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" name="saveId" id="saveId"
								<%=saveId!=null?"checked":"" %>>
								<label for="saveId">아이디저장&nbsp;</label>
								<input type="button" value="회원가입" onclick="location.replace('<%=request.getContextPath()%>/memberEnroll')">
							</td>
						</tr>
					</table>
				</form>
				<%} else {%>
				<table id="logged-in">
					<tr>
						<td>
							<%=loginMember.getUsername() %>님 환영합니다.<br>
							<b>현재 접속자 : <%=SessionCheckListener.getSessionCount() %></b>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="내 정보 보기" 
							onclick='location.href="<%=request.getContextPath()%>/member/memberView?userId=<%=loginMember.getUserId()%>"'>
							<input type="submit" value="로그아웃" onclick="location.replace('<%=request.getContextPath()%>/logout.do')">							
						</td>
					</tr>
				</table>
				<%} %>
			</div>
			<!-- 로그인메뉴 끝 -->
			<!-- 메인메뉴 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%=request.getContextPath() %>">Home</a></li>
					<li id="notice"><a href="#">공지사항</a></li>
					<li id="board"><a href="#">게시판</a></li>
				</ul>			
			</nav>
		</header>
		<script>
			function fn_login_validate(){
				var id=$("#userId").val().trim();
				var pw=$("#password").val().trim();

				if(id.length==0){
					alert('아이디를 입력하세요');
					return false;
				}
				if(pw.length==0){
					alert('비밀번호를 입력하세요');
					return false;
				}
				return true;
			}
		</script>