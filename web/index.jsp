<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<section id="content">
	<h2 align="center" style="margin-top:200px;">안녕하세요 MVC입니다</h2>
	<button id="board-btn">게시판 불러오기</button>
	<div id="tttt"></div>
	<script>
		$(function(){
			$("#board-btn").click(function(){
				$.ajax({
					url:"<%=request.getContextPath()%>/board/boardList1",
					type:"post",
					/* data:{cPage:'',numPerPage:""}, */
					dataType:"json",
					success:function(data){
/* 						const table=$("<table>");
						const tr=$("<tr>");
						tr.append($("<th>").html("번호"));
						tr.append($("<th>").html("제목"));
						tr.append($("<th>").html("작성자"));
						tr.append($("<th>").html("내용"));
						tr.append($("<th>").html("파일이름"));
						tr.append($("<th>").html("등록일"));
						tr.append($("<th>").html("조회수"));
						table.append(tr);
						for(let i=0;i<data.length;i++){
							const tr2=$("<tr>");
							tr2.append($("<td>").html(data[i]['boardNo']));
							tr2.append($("<td>").html(data[i]['boardTitle']));
							tr2.append($("<td>").html(data[i]['boardWriter']));
							tr2.append($("<td>").html(data[i]['boardContent']));
							tr2.append($("<td>").html(data[i]['boardOriginalFileName']));
							tr2.append($("<td>").html(data[i]['boardDate']));
							tr2.append($("<td>").html(data[i]['boardReadCount']));
							table.append(tr2);
						}
						//table.append(tr).append(tr2);	
						$("#tttt").html(table); */
						
						
						const table=$("<table>");
						const th=$("<tr>");
						th.append($("<th>번호</th>")).append($("<th>제목</th>")).append($("<th>내용</th>"));
						table.append(th);
						for(let i=0;i<data.length;i++){
							let tr=$("<tr>");
							let td=$("<td>").html(data[i]['boardNo']);
							tr.append(td);							
							td=$("<td>").html(data[i]['boardTitle']);
							tr.append(td);
							td=$("<td>").html(data[i]['boardContent']);
							tr.append(td);
							table.append(tr);
						}
						$("#tttt").append(table);
					}
				})
			})
		})
	</script>
</section>
<%@ include file="/views/common/footer.jsp" %>