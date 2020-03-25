package com.kh.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.admin.model.service.AdminService;
import com.kh.common.encrypt.AESEncrypt;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/admin/memberList")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//관리자 아이디만 접속할 수 있게  처리
		HttpSession session=request.getSession();
		Member login=(Member)session.getAttribute("loginedMember");
		
		//필터로 이전시켰음
//		if(login==null||!(login.getUserId().equals("admin"))) {
//			//세션이 없거나 login한 아이디가 admin가 아니라면 접근을 막는다
//			request.setAttribute("msg", "잘못된 접근입니다.");
//			request.setAttribute("loc", "/");
//			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
//		}
		
		String searchType=request.getParameter("searchType");
		String searchKeyword=request.getParameter("searchKeyword");
		System.out.println(searchType);
		System.out.println(searchKeyword);
		
		//회원정보에 대한 페이징 처리하기
		int cPage; //사용자가 지금 볼 페이지(현재 페이지)
		//I. 파라미터 값 유무로 초기페이지 설정
//		if(request.getParameter("cPage")!=null) {
//			cPage=Integer.parseInt(request.getParameter("cPage"));
//		}else {
//			cPage=1;
//		}
		//II. 예외처리방식
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) { //null을 Integer로 변환하려고 할 때 발생할 수 있는 에러
			cPage=1;
		}
		
		
		int numPerPage;
		try {
			numPerPage=Integer.parseInt(request.getParameter("numPerPage"));			
		}catch(NumberFormatException e) {
			numPerPage=10;
		}
		//한개 페이지에 출력될 데이터 수
//		int numPerPage=10;
		//설정한 만큼의 회원정보 가져오기
		List<Member> list=null;			
		int totalMember=0;
		if(searchType==null) {
			list=new AdminService().searchMember(cPage,numPerPage);			
			totalMember=new AdminService().memberCount();//전체데이터 행수
		}else {
			list=new AdminService().searchMember(searchType, searchKeyword,cPage,numPerPage);			
			totalMember=new AdminService().memberCount(searchType, searchKeyword);//전체데이터 행수
		}

		//페이지바 만들기
		int totalPage=(int)Math.ceil((double)totalMember/numPerPage);//페이지 개수
		
		int pageBarSize=5; //페이지바 보여지는 개수
		
		//페이지바의 시작번호, 끝 번호까지 출력해주는 변수
		//현재페이지 : 1~5 사이일경우 시작페이지 : 1
		//현재페이지 : 6~10 사이일경우 시작페이지 : 6
		//현재페이지 : 11~15 사이일경우 시작페이지 : 11
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;//시작번호
		int pageEnd=pageNo+pageBarSize-1;//끝번호
		
		String pageBar="";
		//페이지바를 누르면 클릭, 요청
		//a태그에 query string
		//[이전] 1 2 3 4 5 [다음]
		//이전 버튼 만들기
		if(pageNo==1) {//시작번호가 1이면
			pageBar+="<span>[이전]</span>";//눌리지않는다
		}else {//pageNo가 1이 아닐때, 6 11 16 21 등등
			//a태그로 요청할수 있게 만들어줌
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>[이전]</a>";
		}
		
		//페이지 링크 만들기
		while(!(pageNo>pageEnd || pageNo>totalPage)) { //시작번호가 끝번호보다 크지 않거나 시작페이지가 총 페이보다 크지 않을 때에만
			if(pageNo==cPage) {
				//내가 현재 보고 있는 페이지바에는 링크가 없다
				pageBar+="<span class='cPage'>"+pageNo+"</span>";
			}else {
				//내가 현재 보고 있는 페이지 이외의 페이지바에는 넘어갈 수 있는 링크가 있다
				pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>"+pageNo+"</a>"; 
			}
			pageNo++;//후위연산자, 벗어나면 1증가
		}
		
		//다음 버튼 만들기
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			//후위연산자로 증가한 상태이므로 증가한 값 그대로 두어야 한다
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage="+numPerPage+"&searchType="+searchType+"&searchKeyword="+searchKeyword+"'>[다음]</a>";
		}
		
		//pageBar 구성 끝!!
		
		
		
		//DB에 저장되어 있는 전체 회원에 대해 조회해서 가져오기
//		List<Member> members=new AdminService().selectAllMember();
//		request.setAttribute("members", members);
//		request.getRequestDispatcher("/views/admin/manageMembers.jsp").forward(request, response);
		//List<Member> list=new AdminService().searchMember();
		
		//암호화 복호화 처리하기
//		for(Member m:list) {
//			m.setEmail(AESEncrypt.decrypt(m.getEmail()));
//			m.setPhone(AESEncrypt.decrypt(m.getPhone()));
//			m.setAddress(AESEncrypt.decrypt(m.getAddress()));				
//			if(m.getEmail().contains("=")) {
//			}
//			if(m.getPhone().contains("=")) {
//			}
//			if(m.getAddress().contains("=")) {
//			}
//		}
		AESEncrypt.changeDecrypt(list); //공용메서드
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
