<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.List, com.member.model.vo.Member" %>
<%
	List<Member> list=(List)request.getAttribute("list");
	String searchType=request.getParameter("searchType")==null?"userId":request.getParameter("searchType");
	String searchKeyword=request.getParameter("searchKeyword")==null?"":request.getParameter("searchKeyword");
	int cPage=(int)request.getAttribute("cPage");
	int numPerPage=(int)request.getAttribute("numPerPage");
%>
<%@ include file="/views/common/header.jsp" %>
<style>
	section#memberList-container {text-align:center;}
	section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
	section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
	div#search-container{
		margin:0 0 10px 0;
		padding:3px;
		background-color:rgba(0,188,212,0.3);
		float:right;
	}
	div#search-userName{display:none;}
	div#search-gender{display:none;}
	div#search-userId{display:inline-block;}
	section#memberList-container div#neck-container{padding:0px; height: 50px; background-color:rgba(0, 188, 212, 0.3);}
	section#memberList-container div#search-container {margin:0 0 10px 0; padding:3px; float:left;}
	section#memberList-container div#numPerPage-container{float:right;}
	section#memberList-container form#numPerPageFrm{display:inline;}
</style>
	<section id="memberList-container">
		<h2>회원관리</h2>
		<div id="neck-container">
			<div id="search-container">
				검색타입 :
				<select id="searchType">
					<option value="userId" <%=searchType!=null&&searchType.equals("userId")?"selected":""%>>아이디</option>
					<option value="userName" <%=searchType!=null&&searchType.equals("userName")?"selected":""%>>이름</option>
					<option value="gender" <%=searchType!=null&&searchType.equals("gender")?"selected":""%>>성별</option>
				</select>
				<div id="search-userId">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="userId">
						<input type="text" name="searchKeyword" value="<%=searchType!=null&&searchType.equals("userId")?searchKeyword:"" %>" size="25" placeholder="검색할 아이디 입력">
						<input type="hidden" name="cPage" value="<%=cPage%>">
						<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
						<button type="submit">검색</button>
					</form>
				</div>
				<div id="search-userName">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="userName">
						<input type="text" name="searchKeyword" value="<%=searchType!=null&&searchType.equals("userName")?searchKeyword:"" %>" size="25" placeholder="검색할 이름 입력">
						<input type="hidden" name="cPage" value="<%=cPage%>">
						<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
						<button type="submit">검색</button>
					</form>
				</div>
				<div id="search-gender">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="gender">
						<label>
							<input type="radio" name="searchKeyword" value="M" 
							<%=searchKeyword==""||searchKeyword.equals("M")?"checked":"" %>>남
						</label>
						<label>
							<input type="radio" name="searchKeyword" value="F" 
							<%=searchKeyword!=null&&searchKeyword.equals("F")?"checked":"" %>>여
						</label>
						<input type="hidden" name="cPage" value="<%=cPage%>">
						<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
						<button type="submit">검색</button>
					</form>
				</div>
			</div>
			<!-- search-container끝난부분 -->
			<div id="numPerPage-container">
				페이지 회원수 :
				<form name="numPerPageFrm" id="numPerPageFrm"
				action="<%=request.getContextPath() %>/admin/memberList">
					<input type="hidden" name="searchType" value="<%=searchType%>">
					<input type="hidden" name="searchKeyword" value="<%=searchKeyword%>">
					<input type="hidden" name="cPage" value="<%=cPage%>">
					<select name="numPerPage" id="numPerPage">
						<option value="10" <%=numPerPage==0||numPerPage==10?"selected":"" %>>10</option>
						<option value="5"<%=numPerPage==5?"selected":"" %>>5</option>
						<option value="3"<%=numPerPage==3?"selected":"" %>>3</option>					
					</select>
					<!-- select선택을 하면 데이터 출력 개수가 옵션값으로 변경 -->
				</form>
			</div>
			
		</div>
		<table id="tbl-member">
			<thead>
				<tr>
					<th>아이디</th>
					<th>이름</th>
					<th>성별</th>
					<th>나이</th>
					<th>이메일</th>
					<th>전화번호</th>
					<th>주소</th>
					<th>취미</th>
					<th>가입일자</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list }" var="m" varStatus="member">
				<tr>
					<td>${m.userId }</td>
					<td>${m.username }</td>
					<td>${m.gender }</td>
					<td>${m.age }</td>
					<td>${m.email }</td>
					<td>${m.phone }</td>
					<td>${m.address }</td>
					<td>${m.hobby }</td>
					<td><fmt:formatDate value="${m.enrollDate }" pattern="yyyy년 MM월  dd일" /></td>
				</tr>
			</c:forEach>
			<%-- <%for(Member m:list){ %>
				<tr>
					<td>
						<%=m.getUserId() %>
					</td>
					<td>
						<%=m.getUsername() %>
					</td>
					<td>
						<%=m.getGender() %>
					</td>
					<td>
						<%=m.getAge() %>
					</td>
					<td>
						<%=m.getEmail() %>
					</td>
					<td>
						<%=m.getPhone() %>
					</td>
					<td>
						<%=m.getAddress() %>
					</td>
					<td>
						<%=m.getHobby() %>
					</td>
					<td>
						<%=m.getEnrollDate() %>
					</td>
				</tr>
			<%} %> --%>
			</tbody>
		</table>
		<div id="pageBar">
			<%=request.getAttribute("pageBar") %>
		</div>
	</section>
	<script>
	$(function(){
		$("#searchType").change(()=>{
			let type=$("#searchType").val();
			let userId=$("#search-userId");
			let userName=$("#search-userName");
			let gender=$("#search-gender");
			userId.hide();
			userName.hide();
			gender.hide();
			$("#search-"+type).css("display","inline-block");
		})
		$("#searchType").trigger("change");
		
		$("#numPerPage").change(()=>{
			$("#numPerPageFrm").submit();
		})
	});
	/* var searchType=document.getElementById("search-userId");
	searchType.addEventListener("change",function(){
		if()
		searchType.userId.style.display='none';
	}); */
	</script>
<%@ include file="/views/common/footer.jsp" %>
