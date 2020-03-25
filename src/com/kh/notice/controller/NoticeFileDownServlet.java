package com.kh.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeFileDownServlet
 */
@WebServlet("/notice/noticeFileDownload")
public class NoticeFileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//파일다운로드 처리 로직
		//파일이 저장되어있는 실제 경로 가져오기
		//인코딩처리(한글파일명은 글자가 깨짐)
		//저장파일 load시킴(메모리에 얹는다) inputStream
		//response정보를 수정 -> 지금에 대한 응답은 파일입출력이다 라고 알려줌
		//contentType=application/octet-stream/Content-Disposition=attatchment;file-name="파일명"
		//클라이언트에게 보내줌 - 파일출력처리-output write
		
		String path=getServletContext().getRealPath("/upload/notice/"); //getServletContext().getRealPath = web
		//client가 보낸 파일명
		String file=request.getParameter("filePath");//client가 요청한 파일이름 매개변수로 받아옴
		
		//가져온 파일명과 일치하는 파일 가져오기
		File downFile=new File(path+file);
		//스트림 열기(+보조스트림 이용)
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(downFile));
		//보낼 스트림 가져오기(client 스트림)
		ServletOutputStream sos=response.getOutputStream();
		//outputstream, getWriter
		
		BufferedOutputStream bos=new BufferedOutputStream(sos);
		
		//인코딩처리
		String resFileName="";
		//IE분기처리
		//DOM객체의 navigator에서 브라우저에 대한 모든 정보를 가져온 다음에 indexOf(문자배열의 시작위치)
		boolean isMSIE=request.getHeader("user-agent").indexOf("MSIE")!=-1||
				request.getHeader("user-agent").indexOf("Trident")!=-1;
		//isMSIE가 true이면 IE이고 아니면 IE가 아님
		if(isMSIE) {
			//IE는 띄어쓰기(공백)를 \,+로 표시함 이것을 URL인코딩값으로 변경
			resFileName=URLEncoder.encode(file,"UTF-8").replaceAll("\\+","%20");			
		}else {
			//ISO-8859-1으로 한번 바꾸고, UTF파일로 다시 한번 더 바꿔준다
			resFileName=new String(file.getBytes("UTF-8"),"ISO-8859-1");
		}
		
		//응답하기전 헤더부분 수정 -> 파일형식으로 보내는거야 라고 지정
		response.setContentType("application/octet-stream");//mime타입 파일전송이다 라고 정하고(바이트단위로 보냄)
		response.setHeader("Content-Disposition","attachment;fileName"+resFileName);//다운로드라고 정해주고 파일이름을 정해줌
		//stream에서 파일을 읽어오고 다시 파일을 클라이언트한테 전송!
		int read=-1;
		
		while((read=bis.read())!=-1) {//파일을 더이상 읽어올 데 없을때까지 반복해서 읽는다
			bos.write(read);
		}
		
		bis.close();
		bos.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
