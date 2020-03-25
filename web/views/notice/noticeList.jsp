<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.kh.notice.model.vo.Notice" %>
<%
	List<Notice> list=(List)request.getAttribute("list");
%>
<%@ include file="/views/common/header.jsp" %>
<style>
	section#notice-container{width:600px; margin:0 auto; text-align:center;}
	section#notice-container h2{margin:10px 0;}
	table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
	table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0; text-align:center;} 
</style>
<section id="notice-container">
     <h2>공지사항</h2>
     <%if(loginMember!=null && loginMember.getUserId().equals("admin")){%>
     	<button type="button" onclick="location.replace('<%=request.getContextPath()%>/notice/noticeWrite')">글쓰기</button>
     <%} %>
     <table id="tbl-notice">
     	<thead>
	         <tr>
	         
	             <th>번호</th>
	             <th>제목</th>
	             <th>작성자</th>
	             <th>첨부파일</th>
	             <th>작성일</th>
	         </tr>
         </thead>
         <tbody>
         <%for(Notice n:list){ %>
         	<%if(list.isEmpty()){ %>
         	<tr>
         		<td colspan='5'>검색된 공지사항이 없습니다</td>
         	</tr>
         	<%}else { %>
	         <tr>
	             <td><%=n.getNoticeNo() %></td>
	             <td>
	                 <a href="<%=request.getContextPath()%>/notice/noticeView?no=<%=n.getNoticeNo()%>">
	                 	<%=n.getNoticeTitle() %>
	                 </a>
	             </td>
	             <td><%=n.getNoticeWriter() %></td>
	             <td>
		            <%if(n.getFilePath()!=null){ %>
		            	<img src="<%=request.getContextPath()%>/images/file.png" width="16px">
		            <%} %>
	             </td>
	             <td><%=n.getNoticeDate() %></td>
       		  </tr>
       		  <%}
         	} %>
         </tbody>
    </table>
    <div id="pageBar">
    	<%=request.getAttribute("pageBar") %>
    </div>
</section>
<%@ include file="/views/common/footer.jsp" %>