package com.member.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.common.encrypt.AESEncrypt;
import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class InsertMemberServlet
 */
@WebServlet(name="MemberEnrollEndServlet",urlPatterns="/memberEnrollEnd")
public class MemberEnrollEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");

		//1. client가 보낸 데이터를 받아오기!
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		String userName=request.getParameter("userName");
		String gender=request.getParameter("gender");
		int age=Integer.parseInt(request.getParameter("age"));
		
		//암호화 처리대상 phone, email, address
		String email=request.getParameter("email");
//		AESEncrypt.encrypt("암호화 이메일 : "+email);
		email=AESEncrypt.encrypt(email);
		
		String phone=request.getParameter("phone");
//		AESEncrypt.encrypt("암호화 전화 : "+phone);
		phone=AESEncrypt.encrypt(phone);
		
		String address=request.getParameter("address");
//		AESEncrypt.encrypt("암호화 주소 : "+address);
		address=AESEncrypt.encrypt(address);
		
		//데이터가 복수일경우에는 배열로 받아옴
		String[] hobby=request.getParameterValues("hobby");
		String hobbies=String.join(", ", hobby);
		
		Member m=new Member(userId, password, userName, gender, age, email, phone, address, hobbies, null);
		int result=new MemberService().insertMember(m);
		
//		RequestDispatcher rd=request.getRequestDispatcher("/");//메인화면으로 이동
//		rd.forward(request, response);
		
		//중복가입을 막는 로직
		//1.sendRedirect
//		response.sendRedirect(request.getContextPath());
		//2. 결과에 대해 client에 메세지 띄워주기
		String msg=""; //client에게 보여줄 메세지 내용
		String loc=""; //메세지 띄운 후 이동할 페이지
		if(result>0) {
			msg="회원가입에 성공하였습니다.";
			loc="/";
		}else {
			msg="회원가입에 실패하셨습니다.";
			loc="/memberEnroll";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		RequestDispatcher rd=request.getRequestDispatcher("/views/common/msg.jsp");//메인화면으로 이동
		rd.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
