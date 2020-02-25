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

		
		//회원에 대한 정보를 가져와 화면에 출력해주는 로직!
		//client가 보낸 정보를 가져옴
		String userId=request.getParameter("userId");
		System.out.println(userId);
		//DB에서 userId와 일치하는 회원이 있는지 확인하고 있으면 가져오기
		Member m=new MemberService().selectSearch(userId);
		request.setAttribute("member", m);
		
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
