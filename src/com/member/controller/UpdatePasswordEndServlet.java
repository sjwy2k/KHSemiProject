package com.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.service.MemberService;

/**
 * Servlet implementation class UpdatePasswordEndServlet
 */
@WebServlet(name="PasswordUpdateServlet",urlPatterns="/updatePasswordEnd")
public class UpdatePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePasswordEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//클라이언트가 보낸 패스워드와 사용자 아이디를 받아서 DB의 비밀번호를 수정하는 로직

		String userId=request.getParameter("userId");
		String pw=request.getParameter("password_new");
		
		System.out.println("userId : "+userId);
		System.out.println("password_new : "+pw);
		int result=new MemberService().updatePassword(userId, pw);
		System.out.println("처리결과 : "+(result>0?"처리성공":"처리실패"));
		String msg="";
		if(result>0) {
			msg="비밀번호 변경 완료.";
		}else {
			msg="비밀번호 변경 실패.";
		}
		String script="self.close()";
		request.setAttribute("msg", msg);
		request.setAttribute("script", script);
		
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
