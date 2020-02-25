<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	boolean isUsable=(boolean)request.getAttribute("isUsable");
	String userId=(String)request.getAttribute("userId");
	System.out.println(isUsable);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복확인</title>
</head>
<body>
	<div id="checkid-container">
		<%if(isUsable){%>
			[<span><%=userId %></span>]는 사용이 가능합니다.
			<br><br>
			<button type="button" onclick="setUserId();">닫기</button>
		<%} else { %>
			[<span><%=userId %></span>]는 이미 사용중입니다.
			<br><br>
			<input type="text" id="userId" placeholder="아이디를 입력하세요">
			&nbsp;&nbsp;<button type="button" onclick="checkIdDuplicate();">중복검사</button>
			<!-- 별도의 공간이므로 userId사용가능 -->
		<%} %>
	</div>
	<script>
		function setUserId(){
			/* 검색한 id값을 부모 창 userId_로 전송하는 로직 */
			//넣은 후 창 닿기
			/*jsp에 변수로 있는 것을 javascript로 보내기는 당장 불가능*/
			//부모 창은 opener
			opener.document.getElementById("userId_").value='<%=userId%>';
			//jsp변수를 String으로 바꿔서 넣어야 javascript가 인식 가능.
			//넣은 후 창 닫기
			self.close();
		}
		function checkIdDuplicate(){
			//재검색을 위한 로직 구성
			const userId=document.getElementById("userId").value;
			location.href="<%=request.getContextPath()%>/checkIdDuplicate?userId="+userId;
		}
	</script>
</body>
</html>