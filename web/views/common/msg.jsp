<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//에러발생시 500에러
	String msg=(String)request.getAttribute("msg");
	String loc=(String)request.getAttribute("loc");
	String script=(String)request.getAttribute("script");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		alert('<%=msg%>'); <%--매개변수 인식되지 않도록 single quotation에 주의--%>
		<%=script!=null?script:""%>//self.close()
		location.replace("<%=request.getContextPath()%><%=loc%>"); 
	</script>
</body>
</html>