<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.member.model.vo.Member"%>
    
<% 
	Member m=(Member)request.getAttribute("member");
	String[] hobbys=m.getHobby().split(",");
	String[] checked=new String[5];
	for(String h:hobbys){
		switch(h){
			case "운동" : checked[0]="checked";break;
			case "등산" : checked[1]="checked";break;
			case "독서" : checked[2]="checked";break;
			case "게임" : checked[3]="checked";break;
			case "여행" : checked[4]="checked";break;
		}
	}
%>
<%@ include file="/views/common/header.jsp" %>


<section id=enroll-container>
	<h2>회원 정보 수정</h2>
	<form id="memberFrm" method="post" onsubmit="return update_validate();">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="userId" id="userId_" value="<%=m.getUserId() %>" readonly>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="userName" id="userName" value="<%=m.getUsername() %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>	
				<input type="number" name="age" id="age" value="<%=m.getAge() %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%=m.getEmail()%>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%=m.getPhone() %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%=m.getAddress() %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<%if("M".equals(m.getGender())){ %>
						<input type="radio" name="gender" id="gender0" value="M" checked>
						<label for="gender0">남</label>
						<input type="radio" name="gender" id="gender1" value="F">
						<label for="gender1">여</label>
					<%} else { %>
						<input type="radio" name="gender" id="gender0" value="M">
						<label for="gender0">남</label>
						<input type="radio" name="gender" id="gender1" value="F" checked>
						<label for="gender1">여</label>
					<%} %>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%=checked[0] %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%=checked[1] %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%=checked[2] %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%=checked[3] %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%=checked[4] %>><label for="hobby4">여행</label><br />
					
					<%-- String객체의 contains메소드 이용하기 --%>
					<%-- <input type="checkbox" name="hobby" value="독서" /> 독서    --%>
					<%-- <input type="checkbox" name="hobby" value="게임" /> 게임 --%>
				</td>
			</tr>
		</table>
		<input type="button" onclick="updatePassword()" value="비밀번호변경">
		<input type="button" onclick="updateMember();" value="정보수정"/>
		<input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>

<script>
	function updatePassword(){
<%-- 	var url="<%=request.getContextPath()%>/member/changePassword";
		var name="비밀번호 수정하기"
		var option="width=500,height=500,top=100,left=200,location=no"
		window.open(url, name, option); --%>
		
		let url="<%=request.getContextPath()%>/member/updatePassword?userId=<%=m.getUserId()%>";
		let option="width=400px,height=210px";
		
		open(url,"updatePassword",option);
	}
	function update_validate(){
		//regexp이용해 유효성검증하기
		return true;
	}
	function updateMember(){
		//작성된 form데이터를 서버에 전송하여 수정하는 로직
		//form을 가져오는 방법
		const frm=$("#memberFrm");
		frm.attr("action","<%=request.getContextPath()%>/member/memberUpdate");
		frm.submit();
	}
	
	function deleteMember(){
		location.href="<%=request.getContextPath()%>/member/memberDelete?userId=<%=m.getUserId()%>";
	}
</script>
<%@ include file="/views/common/footer.jsp" %>
