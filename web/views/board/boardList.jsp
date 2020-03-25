<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ include file="/views/common/header.jsp" %>
<%@ page import="java.util.List, com.kh.board.model.vo.Board" %>
<%
	List<Board> list=(List)request.getAttribute("list");
%>

<style>
	section#board-container{width:600px; margin:0 auto; text-align:center;}
	section#board-container h2{margin:10px 0;}
	table#tbl-board{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-board th, table#tbl-board td {border:1px solid; padding: 5px 0; text-align:center;} 
	/*글쓰기버튼*/
	input#btn-add{float:right; margin: 0 0 15px;}
	/*페이지바*/
	div#pageBar{margin-top:10px; text-align:center; background-color:rgba(0, 188, 212, 0.3);}
	div#pageBar span.cPage{color: #0066ff;}
	td span.link{
		cursor:pointer;
	}
</style>
<section id="board-container">
	<h2>게시판 </h2>
     <%if(loginMember!=null){%>
     	<%-- <button type="button" onclick="location.replace('<%=request.getContextPath()%>/board/boardWrite')">글쓰기</button> --%>
     	<input type="button" value="글쓰기" id="btn-add" onclick="fn_writeBoard()">
     <%} %>
	<table id="tbl-board">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th>
			<th>조회수</th>
		</tr>
		<%for(Board b:list){ %>
			<%if(list.isEmpty()){ %>
			<tr>
				<td colspan="6">검색된 게시글이 없습니다.</td>
			</tr>
			<%} else {%>
			<tr>
				<td><%=b.getBoardNo() %></td>
				<td>
					<%-- <a href="<%=request.getContextPath() %>/board/boardView?no=<%=b.getBoardNo() %>">
						<%=b.getBoardTitle() %>
					</a> --%>
					<span class="link" onclick="fn_link(<%=b.getBoardNo() %>)">
						<%=b.getBoardTitle() %>
						<form name="" action="<%=request.getContextPath() %>/board/boardView?no=<%=b.getBoardNo() %>">
							<input type="hidden" name="no" value="<%=b.getBoardNo()%>">
						</form>
					</span>
				</td>
				<td><%=b.getBoardWriter() %></td>
				<td><%=b.getBoardDate() %></td>
				<td>
					<%if(b.getBoardOriginalFileName()!=null){ %>
		            	<img src="<%=request.getContextPath()%>/images/file.png" width="16px">
		            <%} %>
				</td>
				<td><%=b.getBoardReadCount() %></td>
			</tr>
			<%} %>
		<%} %>
	</table>
	<form action="<%=request.getContextPath()%>/board/boardView" id="linkF">
		<input type="hidden" name="no">
	</form>
	<script>
		function fn_link(no){
			$("#linkF>input").val(no);
			$("#linkF").submit();
		}
		/* $(".link").click(function(){
			$(this).find("form").submit();
		}) */
		function fn_writeBoard(){
			location.replace("<%=request.getContextPath()%>/board/boardForm");
		}
	</script>
	<div id='pageBar'>
    	<%=request.getAttribute("pageBar") %>
		
	</div>
</section>
<%@ include file="/views/common/footer.jsp" %>
