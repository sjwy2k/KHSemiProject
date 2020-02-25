package com.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="LoginServlet",urlPatterns="/login")
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
			//request.setAttribute("loginedMember", m); 로그인유지가 되지 않음
			//로그인을 유지하기 위해 session객체에 데이터를 넣고 처리함
			HttpSession session=request.getSession(/*true*/);//기본으로 값을 가져옴.
			//getSession()메서드는 매개변수가 있음. default:true
			//true : session이 있으면 그 세션을 return, 없으면 생성해서 return
			//false : session이 있으면 그 세션을 return, 없으면 null값을 return
			
			//session객체의 유지기간을 설정가능
			//was의 web.xml에 session timeout에 대한 설정이 분으로 되어 있음
			//혹은 session.setMaxInactiveInterval()메서드를 이용해서 설정
			//session.setMaxInactiveInterval(20);//초 단위
			
			//cookie로 아이디 저장유지하기
			String saveId=request.getParameter("saveId");
			System.out.println("saveId : "+saveId);
			if(saveId!=null) {
				//아이디를 쿠키에 저장하게 함
				Cookie c=new Cookie("saveId",id);
				//쿠키의 유효기간설정
				c.setMaxAge(7*24*60*60);//초단위, 7일
				response.addCookie(c);
			}else {
				//저장된 cookie값 지우고 check된것 해제
				Cookie c=new Cookie("saveId",id);
				c.setMaxAge(0);//체크박스가 체크되어있지 않으므로 쿠키가 바로 없어진다
				response.addCookie(c);
			}
			
			session.setAttribute("loginedMember", m);
		} else {
			//로그인 실패
			msg="로그인 실패";
		}
		
		//서블릿에서 데이터를 다른 jsp, 서블릿으로 전달하는 방법
		//데이터 공유객체 ->request, session, context		
		String loc="/";
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		//RequestDispatcher rd=request.getRequestDispatcher("views/common/msg.jsp");
		//RequestDispatcher rd=request.getRequestDispatcher("/");
		//rd.forward(request,response);
		
		response.sendRedirect(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
