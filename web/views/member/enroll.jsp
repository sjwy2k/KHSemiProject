<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<section id="enroll-container">
	<h2>회원가입정보입력</h2>
 	<form action="<%=request.getContextPath() %>/memberEnrollEnd" method="post" onsubmit="return fn_enroll_validate();" >
       <table>
		<tr>
			<th>아이디</th>
			<td>
				<input type="text" placeholder="4글자이상" name="userId" id="userId_" required>
				<!-- 아이디 중복체크하기 -->
				<input type="button" value="중복검사" onclick="checkIdDuplicated();" target="_blank">
			</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>	
			<input type="text"  name="userName" id="userName" required><br>
			</td>
		</tr>
		<tr>
			<th>패스워드</th>
			<td>
				<input type="password" name="password" id="password_" required>
			</td>
		</tr>
		<tr>
			<th>패스워드확인</th>
			<td>	
				<input type="password" id="password_2" required><br>
			</td>
		</tr>  
		<tr>
			<th>나이</th>
			<td>	
			<input type="number" name="age" id="age"><br>
			</td>
		</tr> 
		<tr>
			<th>이메일</th>
			<td>	
				<input type="email" placeholder="abc@xyz.com" name="email" id="email"><br>
			</td>
		</tr>
		<tr>
			<th>휴대폰</th>
			<td>	
				<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required><br>
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>	
				<input type="text" placeholder="" name="address" id="address"><br>
			</td>
		</tr>
		<tr>
			<th>성별 </th>
			<td>
				<input type="radio" name="gender" id="gender0" value="M" checked>
				<label for="gender0">남</label>
				<input type="radio" name="gender" id="gender1" value="F">
				<label for="gender1">여</label>
			</td>
		</tr>
		<tr>
			<th>취미 </th>
			<td>
				<input type="checkbox" name="hobby" id="hobby0" value="운동"><label for="hobby0">운동</label>
				<input type="checkbox" name="hobby" id="hobby1" value="등산"><label for="hobby1">등산</label>
				<input type="checkbox" name="hobby" id="hobby2" value="독서"><label for="hobby2">독서</label><br />
				<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
				<input type="checkbox" name="hobby" id="hobby4" value="여행"><label for="hobby4">여행</label><br />
			</td>
		</tr>
	</table>
	<input type="submit" value="가입" >
	<input type="reset" value="취소">
    </form>
	<script>
	//아이디 중복검사
	function checkIdDuplicated(){
		var userId=$('#userId_').val().trim();
		if(!userId||userId.length<4) {
			alert("아이디는 4글자 이상 입력하세요");
			$("#userId_").focus();
			return;
		}
		open("<%=request.getContextPath()%>/checkIdDuplicate?userId="+userId,"checkId","width=300px,height=200px");
	}

	//유효성 검사
	function fn_enroll_validate(){
		const userId=$('#userId_');
		if(userId.val().length<4){
			alert('아이디는 최소 4자리 이상으로 입력');
			return false;
		}
		return true;
	}
	$(function(){
		//비밀번호 확인 로직
		$('#password_2').blur(()=>{
			console.log(this);
			const p1=$('#password_').val();
			//const p2=$('#password_2').val();
			const p2=$(event.target).val();
			if(p1!=p2){
				alert('패스워드가 일치하지 않습니다.');
				$('password_').val('');
				$('password_2').val('');
				$('password_1').focus();
			}
		})
	});
	</script>
</section>
<%@ include file="/views/common/footer.jsp" %>