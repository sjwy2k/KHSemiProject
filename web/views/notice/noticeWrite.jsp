<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ include file="/views/common/header.jsp" %>
<style>
	section div#notice-container{width:600px; margin:0 auto; text-align:center;}
	section div#notice-container h2{margin:10px 0;}
	table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
	table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
<section>
<div id="notice-container">
	<h2>공지사항 작성</h2>
    <form action="<%=request.getContextPath() %>/notice/noticeFormEnd" method="post" enctype="multipart/form-data">
    <%-- <form action="<%=request.getContextPath() %>/notice/noticeFormEnd" method="post"> --%>
    	<!-- 
    		파일업로드를 할 때는 method방식을 반드시 post로 설정하고 form의 속성값을  enctype으로 설정해야함.
    		=> multipart/form-data
   		 -->
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><input type="text" name="title" required></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%=loginMember.getUserId() %><input type="hidden" name="writer" value="<%=loginMember.getUserId() %>" readonly required></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><input type="file" name="upfile"></td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><textarea cols=46 rows=5 name="content" style="resize:none;"></textarea></td>
        </tr>
        <tr>
            <th colspan="2">
         		<input type="submit" value="등록">
            </th>
        </tr>
    </table>
    </form>
    </div>
</section>

<%@ include file="/views/common/footer.jsp" %>
