package com.kh.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeFormUpdateEnd")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//enctype이 Multipart/form-data 형식으로 넘어왔는지 체크
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "공지사항 수정오류[form:enctype 관리자에게 문의]");
			request.setAttribute("loc", "/notice/noticeList");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		//파일 업로드 로직 수행
		//중요한것 upfile(새로 추가한 파일)이 있으면 그리고 없으면?
		//있으면 oriFile을 삭제 후 새 파일 업로드, 없으면 업로드 된 채로 그대로 둠
		String path=getServletContext().getRealPath("/upload/notice/");
		int maxSize=1024*1024*10;

//		String cilentFileName=request.getParameter("upfile");
//		String wasFileName=mr.getFilesystemName("upfile");

		try {
			MultipartRequest mr=new MultipartRequest(request, path, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			Notice n=new Notice(
					Integer.parseInt(mr.getParameter("no")),
					mr.getParameter("title"),
					mr.getParameter("writer"),
					mr.getParameter("content"),
					null,
					mr.getFilesystemName("upfile"),
					null
			);
			
			File f=mr.getFile("upfile");//client가 넘긴 파일이 있는지 없는지 확인
			if(f!=null&&f.length()>0) {//f가 null이아니거나 길이가 0이 아니면 파일이 있으므로
				//f가 null이거나 length가 0이면 파일이 없는것..
				File deleteFile=new File(path+mr.getParameter("oriFile"));
				boolean flag=deleteFile.delete();
				System.out.println(flag?"파일삭제 성공":"파일삭제 실패");
			}else {
				n.setFilePath(mr.getParameter("oriFile"));
			}
			
			int result=new NoticeService().updateNotice(n);
			if(result>0) {
				//msg.jsp로 이동
				request.setAttribute("msg", "공지사항 수정성공");
			}else {
				request.setAttribute("msg", "공지사항 수정실패");
			}
			request.setAttribute("loc", "/notice/noticeView?no="+mr.getParameter("no"));
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			
		}catch(IOException e) {
			e.printStackTrace();
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
