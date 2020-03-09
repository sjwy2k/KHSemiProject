package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.common.encrypt.AESEncrypt;
import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet("/member/memberView")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//주소창에 로그인 없이 서블릿 매핑값 적었을 때 접속을 차단하는 방법
		HttpSession session=request.getSession(false); //세션이 있으면 가져오고 없으면 null을 return해라
		if(session!=null && session.getAttribute("loginedMember")==null) { //세션이 없거나 로그인이 되어있지 않다면
			request.setAttribute("msg", "잘못된 접근입니다. 메인화면으로 이동합니다");
			request.setAttribute("loc", "/");//메인화면으로 이동
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			//필터를 적용하여 잘못된 접근을 일괄적으로 방어할 수 있다.. 생각해볼 것
		}
		
		
		//회원에 대한 정보를 가져와 화면에 출력해주는 로직!
		//client가 보낸 정보를 가져옴
		String userId=request.getParameter("userId");
		System.out.println(userId);
		//DB에서 userId와 일치하는 회원이 있는지 확인하고 있으면 가져오기
		Member m=new MemberService().selectSearch(userId);
		request.setAttribute("member", m);
		
		//암호화된 내용 복호화하기
		m.setEmail(AESEncrypt.decrypt(m.getEmail()));
		m.setPhone(AESEncrypt.decrypt(m.getPhone()));
		m.setAddress(AESEncrypt.decrypt(m.getAddress()));
		
		request.getRequestDispatcher("/views/member/memberView.jsp").forward(request, response);
		
		
//		String userId=request.getParameter("userId");
//		Member m=new MemberService().getMemberInfo(userId);
//		
//		if(m!=null) {
//			request.setAttribute(userId, m);
//		}
//		
//		String loc="/";
//		request.setAttribute("loc", loc);
//		
//		RequestDispatcher rd=request.getRequestDispatcher("/member/memberView.jsp");
//		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
