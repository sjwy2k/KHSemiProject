package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.admin.model.service.AdminService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class MemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberFinderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		HttpSession session=request.getSession();
//		Member login=(Member)session.getAttribute("loginedMember");
//		if(login==null||!(login.getUserId().equals("admin"))) {
//			request.setAttribute("msg", "잘못된 접근입니다.");
//			request.setAttribute("loc", "/");
//			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
//			
//		}
				
//		String searchType=(String)request.getAttribute("searchType");
//		String searchKeyword=(String)request.getAttribute("searchKeyword");
		
		
		//1. 클라이언트가 보낸 검색항목과 검색어를 가져와서 DB에 있는지 확인하고 일치하는 데이터를 조회
		//2. view화면에 전송
		
		
		String searchType=(String)request.getParameter("searchType");
		String searchKeyword=(String)request.getParameter("searchKeyword");
		
//		System.out.println(searchType+", "+searchKeyword);
//		List<Member> list=null;
//		if(searchType.equals("userId")) {
//			list=new AdminService().searchMemberForId(searchKeyword);			
//		} /*
//		list=new AdminService().searchMember(searchType, searchKeyword);
		
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		
		int numPerPage;
		try {
			numPerPage=Integer.parseInt(request.getParameter("numPerPage"));			
		}catch(NumberFormatException e) {
			numPerPage=10;
		}
		
		List<Member> list=new AdminService().searchMemberPage(cPage, numPerPage, searchType, searchKeyword);
		int totalMember=new AdminService().memberCount(searchType, searchKeyword);
		
		//페이지바 구성하기
		int totalPage=(int)Math.ceil((double)totalMember/numPerPage);//페이지 개수
		int pageBarSize=5; //페이지바 보여지는 개수

		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;//시작번호
		int pageEnd=pageNo+pageBarSize-1;//끝번호
		
		String pageBar="";
		if(pageNo==1) {//시작번호가 1이면
			pageBar+="<span>[이전]</span>";//눌리지않는다
		}else {//pageNo가 1이 아닐때, 6 11 16 21 등등
			//a태그로 요청할수 있게 만들어줌
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>[이전]</a>";
		}
		
		//페이지 링크 만들기
		while(!(pageNo>pageEnd || pageNo>totalPage)) { //시작번호가 끝번호보다 크지 않거나 시작페이지가 총 페이지보다 크지 않을 때에만
			if(pageNo==cPage) {
				//내가 현재 보고 있는 페이지바에는 링크가 없다
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}else {
				//내가 현재 보고 있는 페이지 이외의 페이지바에는 넘어갈 수 있는 링크가 있다
				pageBar+="<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+pageNo+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>"+pageNo+"</a>"; 
			}
			pageNo++;//후위연산자, 벗어나면 1증가
		}
		
		//다음 버튼 만들기
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			//후위연산자로 증가한 상태이므로 증가한 값 그대로 두어야 한다
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+pageNo+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>[다음]</a>";
		}
		
		//pageBar 구성 끝!!
		
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		request.setAttribute("numPerPage", numPerPage);
		request.getRequestDispatcher("/views/member/memberList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
