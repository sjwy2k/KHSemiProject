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
 * Servlet implementation class NoticeServlet
 */
@WebServlet("/notice/noticeList")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//noticeList.jsp페이지로 연결
		//DB에 있는 모든 notice테이블의 자료를 가져와서 보여줘야함
		//+페이징처리
		
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		int numPerPage=5;
		try {
			numPerPage=Integer.parseInt(request.getParameter("numPerPage"));
		}catch(NumberFormatException e) {
			numPerPage=5;
		}
		
		List<Notice> list=new NoticeService().searchNotice(cPage, numPerPage);
		
		
		//페이지바 만들기
		int totalNotice=new NoticeService().noticeCount();
		
		int totalPage=(int)Math.ceil((double)totalNotice/numPerPage);
		
		int pageBarSize=5;
		
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		String pageBar="";
		
		//이전
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}else {
			pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList.jsp?cPage="+(pageNo-1)+"'>[이전]</a>";
		}
		
		//페이지
		while(!(pageNo>pageEnd || pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}else {
				pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList.jsp?cPage="+pageNo+"&numPerPage="+numPerPage+"'>"+pageNo+"</a>";
			}
			//빠지면 무한루프!!!
			pageNo++;
		}
		
		//다음
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			//후위연산자로 증가한 상태이므로 증가한 값 그대로 두어야 한다
			pageBar+="<a href='"+request.getContextPath()+"/notice/noticeList.jsp?cPage="+pageNo+"&numPerPage="+numPerPage+"'>[다음]</a>";
		}
		
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("numPerPage", numPerPage);
		
		request.getRequestDispatcher("/views/notice/noticeList.jsp").forward(request, response);
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
