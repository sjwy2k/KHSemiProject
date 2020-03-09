<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드변경</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>
<style>
	div#updatePassword-container{
	    background:red;
	}
	div#updatePassword-container table {
	    margin:0 auto;
	    border-spacing: 20px;
	}
	div#updatePassword-container table tr:last-of-type td {
	    text-align:center;
}
</style> 
</head>
<body>


<div id="updatePassword-container">
	<form name="updatePwdFrm" action="<%=request.getContextPath()%>/updatePasswordEnd" method="post" >
		<table>
			<tr>
				<th>변경할 비밀번호</th>
				<td>
					<input type="password" name="password_new" id="password_new" required>
				</td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td>	
					<input type="password" id="password_chk" required><br>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" onclick="return password_validate();" value="변경" />&nbsp;
					<input type="button" onclick="self.close();" value="닫기" />						
				</td>
			</tr>
		</table>
	<!-- 파라미터로 보냈던 userId를 request에서 꺼내온다 -->
	<input type="hidden" name="userId" value="<%=request.getParameter("userId")%>" />
	</form>
</div>
<script>
function password_validate(){
	//사실 let보다는 const가 더 좋다. 수정마다 새로 생성되기 때문에 메모리낭비..
	let pwNew=$("#password_new").val().trim();
	let pwChk=$("#password_chk").val().trim();
	if(pwNew!=pwChk){
		alert("입력한 비밀번호가 일치하지 않습니다.");
		$("#password_new").select();
		return false;
	}
	return true;
}
</script>
</body>
</html>