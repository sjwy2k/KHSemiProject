package com.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//로그인 로직처리하기
		//사용자가 보내준 id와 password를 받아와서 dao에 있는지 체크하고 값이 있으면 로그인 없으면 로그인실패		
		//1. client가 보낸 데이터 확인하기
		
		String id=request.getParameter("userId");
		String pw=request.getParameter("password");
		
//		System.out.println("아이디 : " + id);
//		System.out.println("비밀번호 : " + pw);
//		PrintWriter out=response.getWriter();
//		out.write("아이디 : " + id);
//		out.write("비밀번호 : " + id);
		
		
		//2. DB에 id, pw에 일치한 값이 있는지 확인, 일치하는 값이 있으면 그 값을 가져옴
		Member m=new MemberService().selectId(id,pw);
		
		//m이 일치하는 값이 있으면 값이 있고, 없으면 null
		//결과에 따라서 화면을 분기처리 해야함(조건문)
		//메세지 출력 jsp를 만들고 로그인 성공하면 성공 메세지
		//실패하면 실패 메세지를 출력하고
		//메인화면으로 돌아가게 처리하기
		String msg="";
		if(m!=null) {
			//로그인성공
			msg="로그인 성공";
		} else {
			//로그인 실패
			msg="로그인 실패";
		}
		
		//서블릿에서 데이터를 다른 jsp, 서블릿으로 전달하는 방법
		//데이터 공유객체 ->request, session, context
		
		String loc="/";
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		RequestDispatcher rd=request.getRequestDispatcher("views/common/msg.jsp");
		rd.forward(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
