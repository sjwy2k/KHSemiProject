<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" type="text/css">
<!-- <script src="http://code.jquery.com/jquery-3.4.1.js"></script> -->
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			
			<h2>하하 깃 테스트! 내가 수정한 내용!! 마경호!</h2>
			
			<!-- 헤더 메뉴 추가 - 로그인 메뉴-->
			<div class="login-container">
				<form id="loginFrm" action="<%=request.getContextPath() %>/login" method="post" onsubmit="return fn_login_validate();">
					<table>
						<tr>
							<td>
								<input type="text" name="userId" id="userId" placeholder="아이디">
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
								<input type="checkbox" name="saveId" id="saveId">
								<label for="saveId">아이디저장&nbsp;</label>
								<input type="button" value="회원가입" onclick="">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- 로그인메뉴 -->
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