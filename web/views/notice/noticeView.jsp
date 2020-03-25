<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
<%
	Notice n=(Notice)request.getAttribute("notice");
%>
<%@ include file="/views/common/header.jsp" %>

<style>
    section div#notice-container{width:600px; margin:0 auto; text-align:center;}
    section div#notice-container h2{margin:10px 0; text-align:center;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
<section>
<div id="notice-container">
	<h2>공지사항 상세화면</h2>
    <table id="tbl-notice">
	    <tr>
	        <th>제 목</th>
	        <td><%=n.getNoticeTitle() %></td>
	    </tr>
	    <tr>
	        <th>작성자</th>
	        <td><%=n.getNoticeWriter() %></td>
	    </tr>
	    <tr>
	        <th>첨부파일</th>
	        <td>
	        	<%if(n.getFilePath()!=null){ %>
	        	<a href="<%=request.getContextPath()%>/notice/noticeFileDownload?filePath=<%=n.getFilePath()%>">
	            	<img src="<%=request.getContextPath()%>/images/file.png" width="16px">
	        	</a>
	            	<span><%=n.getFilePath() %></span>
	            <%} %>
	        </td>
	    </tr>
	    <tr>
	        <th>내 용</th>
	        <td><%=n.getNoticeContent() %></td>
	    </tr>
		<%if(loginMember!=null && loginMember.getUserId().equals("admin")){ %>
	    <tr>
	        <th colspan="2">
	        	<form action="<%=request.getContextPath() %>/notice/noticeUpdate">
	        		<input type="hidden" value="<%=n%>">
		            <input type="button" value="수정하기" onclick="fn_uploadNotice();">
		            <input type="button" value="삭제하기" onclick="">
	        	</form>
	        </th>
	    </tr>
	    <%} %>
	</table>
</div>
<script>
	function fn_uploadNotice(){
		location.replace("<%=request.getContextPath()%>/notice/updateNotice2?no=<%=n.getNoticeNo()%>")
	}
</script>
</section>
<%@ include file="/views/common/footer.jsp" %>