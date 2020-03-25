package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardFormEndServlet
 */
@WebServlet("/board/boardFormEnd")
public class BoardFormEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFormEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파일업로드 로직수행
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "게시판 작성 오류 관리자에게 문의하세요");
			request.setAttribute("loc", "/board/boardForm");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		try {
			String path=getServletContext().getRealPath("/upload/board/");
			int maxSize=1024*1024*10;
			MultipartRequest mr=new MultipartRequest(request, path, maxSize, "UTF-8", new MyFileRenamePolicy());
			String title=mr.getParameter("title"); 
			String writer=mr.getParameter("writer");
			String content=mr.getParameter("content");
			String oriFileName=mr.getOriginalFileName("up_file");
			String renamedFileName=mr.getFilesystemName("up_file");
			
			System.out.println("ori : "+oriFileName);
			System.out.println("rename : "+renamedFileName);			
			
			Board b=new Board(0, title, writer, content, oriFileName, renamedFileName, null, 0);
			int result=new BoardService().insertBoard(b);
			if(result>0) {
				request.setAttribute("msg", "게시글 등록성공");
				request.setAttribute("loc", "/board/boardList");
			}else {
				request.setAttribute("msg", "게시글 등록실패");
				request.setAttribute("loc", "/board/boardForm");
				File f=new File(path+renamedFileName);
				if(f.isFile()) {
					f.delete();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
//		File f=new File("/");
//		f.mkdirs();
		
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
