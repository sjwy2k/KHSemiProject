<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ include file="/views/common/header.jsp" %>
<%@ page import="java.util.List, com.kh.board.model.vo.Board, com.kh.board.model.vo.BoardComment" %>
<%
	Board b=(Board)request.getAttribute("board");
	List<BoardComment> list=(List)request.getAttribute("boardComment");
%>
<style>
	section#board-container{width:600px; margin:0 auto; text-align:center;}
	section#board-container h2{margin:10px 0;}
	table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
	table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
	/*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
</style>

<section id="board-container">
<h2>게시판</h2>
<table id="tbl-board">
	<tr>
		<th>글번호</th>
		<td><%=b.getBoardNo() %></td>
	</tr>
	<tr>
		<th>제 목</th>
		<td><%=b.getBoardTitle() %></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=b.getBoardWriter() %></td>
	</tr>
	<tr>
		<th>조회수</th>
		<td><%=b.getBoardReadCount() %></td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
			<%if(b.getBoardOriginalFileName()!=null){ %>
			<a href="<%=request.getContextPath()%>/board/boardFileDownload?filePath=<%=b.getBoardOriginalFileName()%>">
				<img src="<%=request.getContextPath()%>/images/file.png" width="16px"/>
			</a>
			<span><%=b.getBoardOriginalFileName() %></span>
			<%} %>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td><%=b.getBoardContent() %></td>
	</tr>
	<%if(loginMember!=null && (loginMember.getUserId().equals("admin") || loginMember.getUserId().equals(b.getBoardWriter()))){ %>
	글작성자/관리자인경우 수정삭제 가능
	<tr>
		<th colspan="2">
			<input type="button" value="수정하기" onclick="fn_updateBoard()">
			<input type="button" value="삭제하기" onclick="fn_deleteBoard()">
		</th>
	</tr>
	<%} %>
</table>
<!-- 댓글화면구현 -->
<div id="comment-container">
	<div class="comment-editor">
		<form action="<%=request.getContextPath()%>/board/boardCommentInsert" method="post">
			<input type="text" name="commentContent">
			<button type="submit" id="btn-insert">등록</button>
			<input type="hidden" name="boardRef" value="<%=b.getBoardNo() %>">
			<input type="hidden" name="commentWriter" value='<%=loginMember!=null?loginMember.getUserId():""%>'>
			<input type="hidden" name="commentLevel" value="1">
			<input type="hidden" name="commentRef" value="0">
		</form>
	</div>
</div>
<script>
	$(function(){
		$("input[name=commentContent]").focus(function(){
			if(<%=loginMember==null%>){
				alert("로그인 후 이용하세요");
				$("#userId").focus();
			}
		})
	});
</script>
<table id="tbl-comment">
	<!-- 댓글출력하기 -->
	<% if(list!=null && !list.isEmpty()){ 
		for(BoardComment bc : list) {
			if(bc.getBoardCommentLevel()==1){
	%>
	<tr class="level1">
		<td>
			<sub class="comment-writer"><%=bc.getBoardCommentWriter() %></sub>
			<sub class="comment-date"><%=bc.getBoardCommentDate() %></sub>
			<br/>
			<%=bc.getBoardCommentContent() %>
		</td>
		<td>
			<button class="btn-reply" value="<%=bc.getBoardCommentNo()%>">답글</button> 
		</td>
	</tr>
	<%	}else{%>
	<tr class="level2">
		<td>
			<sub><%=bc.getBoardCommentWriter() %></sub>
			<sub ><%=bc.getBoardCommentDate() %></sub>
			<br/>
			<%=bc.getBoardCommentContent() %>
		</td>
		<td></td>
	</tr>
	<%	}
	   }//for
	 }//if %>
</table>
<script>
	$(".btn-reply").click(function(){
		if(<%=loginMember!=null%>){
			const tr=$("<tr>");
			const td=$("<td>").css({
				"display":"none","text-align":"left"
			}).attr("colspan",2);
			const form=$("<form>").attr({
				"action":"<%=request.getContextPath()%>/board/boardCommentInsert",
				"method":"post"
			});
			const commentContent=$("<input>").attr({
				"type":"text",
				"name":"commentContent",
			});
			const boardRef=$("<input>").attr({
				"type":"hidden",
				"name":"boardRef",
				"value":"<%=b.getBoardNo()%>"
			});
			const commentWriter=$("<input>").attr({
				"type":"hidden",
				"name":"commentWriter",
				"value":"<%=loginMember!=null?loginMember.getUserId():""%>"
			});
			const commentLevel=$("<input>").attr({
				"type":"hidden",
				"name":"commentLevel",
				"value":"2"
			});
			const commentRef=$("<input>").attr({
				"type":"hidden",
				"name":"commentRef",
				"value":$(this).val()
			});
			console.log($(this).val());
			const btn=$("<button>").attr({
				"type":"submit","class":"btn-insert2"
			}).html("등록");
			form.append(commentContent).append(boardRef).append(commentWriter).append(commentLevel).append(commentRef).append(btn);
			td.append(form);
			tr.append(td);//붙이기
			($(this).parent().parent()).after(tr);
			tr.children("td").slideDown(500);
			$(this).off("click");//생성했으면 이벤트 종료
		}
	})
</script>
</section>
<%@ include file="/views/common/footer.jsp" %>
