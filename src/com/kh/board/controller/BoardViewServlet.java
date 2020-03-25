package com.kh.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//noticeView와 동일한 로직
		int no=Integer.parseInt(request.getParameter("no"));
		
		
		//쿠키에 읽은 게시판을 저장하고 있으면 조회수를 올리지 않게 설정
		Cookie[] cookies=request.getCookies();
		String cookieVal=""; // 데이터를 보관용
		boolean hasRead=false; //읽은 표시
		
		//cookie값에 있는 읽은 게시판을 확인
		if(cookies!=null) {
			for(Cookie c:cookies){
				String name=c.getName();
				String value=c.getValue();
				if("boardCookie".equals(name)) {
					cookieVal=value;//cookie에 값이 동일하면 집어넣는다
					if(value.contains("|"+no+"|")) {
						hasRead=true;
						break;//쿠키가 있어도 쿠키안에 읽은 상태가 아니라면 빠져나간다
					}
				}
			}
		}
		//|1|쿠키에 오늘 읽었던 글번호들을 모두 저장
		
		//읽지 않았다면 쿠키에 데이터를 추가
		if(!hasRead) { //읽지 않았다면
			Cookie c=new Cookie("boardCookie",cookieVal+"|"+no+"|");
			//쿠키값이 동일하면 덮어쓰게된다.
			c.setMaxAge(-1);//세션이 종료시에 쿠키 삭제
			response.addCookie(c);//쿠키에추가
			
		}
		
		Board b=new BoardService().selectBoard(no, hasRead);
		List<BoardComment> list=new BoardService().selectBoardComment(no); //comment도 받아오기
		
		
		if(b==null) {
			request.setAttribute("msg", "요청한 게시글이 없습니다");
			request.setAttribute("loc", "/board/boardList");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		} else {
			request.setAttribute("board", b);
			request.setAttribute("boardComment", list); //객체에 comment저장
			request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
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
