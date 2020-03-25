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
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet("/notice/noticeFormEnd")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//공지사항을 등록하는 서비스
		//System.out.println(request.getParameter("title")); //null
		
		//cos.jar 라이브러리에서 제공하는 MultiPartRequest객체를 사용하여
		//클라이언트가 보낸 데이터를 받아옴 -> 클라이언트가 multipart/form-data로 전송했을 때..
		
		//1. multipart/form-data로 형식이 넘어왔는지 확인
		//isMultipartContent() : 형식이 이걸로 넘긴거냐?? 맞으면 true 다르면 false
		if(!ServletFileUpload.isMultipartContent(request)) {//부정연산
			//업로드처리 로직에서 multipart/formdata 형식으로 넘어오지 않는다면, 안 넘어온다면
			//서비스로직이 돌아가면 안됨.
			request.setAttribute("msg", "공지사항 등록에러[form:enctype 관리자에게 문의]");
			request.setAttribute("loc", "/notice/noticeWrite");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		//공지사항 내용 DB에 저장하고 전송된 파일 was서버 폴더에 저장하는 로직을 수행
		//파일 업로드를 위한 로직처리
		//1. 파일을 내 was서버 어디에 저장할 지 경로를 지정
		String path=getServletContext().getRealPath("/upload/notice/");
		System.out.println("경로 : "+path);
		//2. 업로드 파일에 대한 최대용량을 설정
		int maxSize=1024*1024*10; //최대 10MByte
		
		//multipartrequest에서 필요한 2가지가 충족되었다
		//3. cos.jar에서 지원하는 MultipartRequest객체를 생성
		//MultipartRequest(HttpServletRequest, 저장경로, 파일저장최대크기, 문자열인코딩값, 파일rename정책)
		//성공하면 바로저장되고 실패하면 에러발생
		//rename정책 : 파일명 동일하면 자동적으로 다르게 관리할 수 있도록 rename정책 만듬(rename객체 규약)
		try {
			MultipartRequest mr=new MultipartRequest(request, path, maxSize, "UTF-8", new DefaultFileRenamePolicy()); //생성되는 순간 들어감
			String title=mr.getParameter("title");
			String content=mr.getParameter("content");
			String writer=mr.getParameter("writer");
			String fileName=mr.getFilesystemName("upfile");//파일시스템 이름은 별도로 불러온다!
			System.out.println(fileName);
			Notice n=new Notice(0,title, writer, content, null, fileName, /**/"Y");
			//n을 DB에 저장하기
			int result=new NoticeService().insertNotice(n);
			
			//저장 성공이면 공지사항 저장성공 메세지 출력, 공지사항 리스트로 이동
			//저장 실패면 실패메세지 출력, 공지사항 작성 페이지로 이동
			//+파일이 저장되면 안되므로 삭제하기
			
			if(result>0) {
				//msg.jsp로 이동
				request.setAttribute("msg", "공지사항 저장성공");
				request.setAttribute("loc", "/notice/noticeList");
			}else {
				request.setAttribute("msg", "공지사항 저장실패");
				request.setAttribute("loc", "/notice/noticeWrite");
				File f=new File(path+fileName);
				if(f.isFile()) {
					f.delete();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
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
