package com.kh.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeViewServlet
 */
@WebServlet("/notice/noticeView")
public class NoticeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//클라이언트가 요청한 한개의 공지사항 상세화면을 출력
		int no=Integer.parseInt(request.getParameter("no"));
//		Notice n=new NoticeService().searchNoticeForNo(no);
		Notice n=new NoticeService().selectNotice(no);
		
		//해보자
		//n이 null이면 요청한 공지사항이 없습니다 메세지 출력하고 noticeList페이지로 전환(msg이용)
		//n이 값이 있으면 상세보기페이지로 전환
		
		if(n==null) {
			//msg.jsp로 이동
			request.setAttribute("msg", "요청한 공지사항이 없습니다.");
			request.setAttribute("loc", "/notice/noticeList");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}else {
			//noticeView.jsp
			request.setAttribute("notice", n);
			request.getRequestDispatcher("/views/notice/noticeView.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
