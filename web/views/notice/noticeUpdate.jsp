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
    span#fname{
        position: absolute;
        left: 86px;
        top: 9px;
        width: 285px;
        background: #f5f5f5;
    }
</style>
<section>
<form action="<%=request.getContextPath() %>/notice/noticeFormUpdateEnd" method="post" enctype="multipart/form-data">
<input type="hidden" name="no" value="<%=n.getNoticeNo()%>">
<div id="notice-container">
	<h2>공지사항 상세화면</h2>
    <table id="tbl-notice">
	    <tr>
	        <th>제 목</th>
	        <td><input type="text" name="title" value="<%=n.getNoticeTitle() %>" required></td>
	    </tr>
	    <tr>
	        <th>작성자</th>
<%-- 	        <td><input type="text" name="writer" value="<%=n.getNoticeWriter() %>" style="background-color:silver;" readonly required></td> --%>
	        <td><input type="hidden" name="writer" value="<%=n.getNoticeWriter() %>" style="background-color:silver;" readonly required><%=n.getNoticeWriter() %></td>
	    </tr>
	    <tr>
	        <th>첨부파일</th>
	        <td style="position:relative">
	        	<input type="file" name="upfile">
	        	<%if(n.getFilePath()!=null){ %>
		        	<span id="fname"><%=n.getFilePath() %></span>
		        	<input type="hidden" name="oriFile" value="<%=n.getFilePath() %>">
	        	<%} %>
	        </td>
	    </tr>
	    <tr>
            <th>내 용</th>
            <td><textarea cols=46 rows=5 name="content" style="resize:none;"><%=n.getNoticeContent() %></textarea></td>
        </tr>
        <tr>
            <th colspan="2">
         		<input type="submit" value="등록">
            </th>
        </tr>
	</table>
</div>
</form>
<script>
	/* window.onload = function(){
		var filePath=document.getElementsByNames("filePath").
		var fname=document.getElementById("fname");
		fname.addEventListener()
	} */
	$(function(){
		$("input[name='upfile']").change(function(){
			if($(this).val()==""){
				$("#fname").show();
			}else{
				$("#fname").hide();
			}
		});
	})
</script>
</section>
<%@ include file="/views/common/footer.jsp" %>