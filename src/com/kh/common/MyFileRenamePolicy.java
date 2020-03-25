package com.kh.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File oldFile) {
		File newFile=null;
		do {
			//파일명을 rename할 중복되지않은 데이터
			long currentTime=System.currentTimeMillis();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmSSS");
			int dot=(int)(Math.random()*10000);
			
			//확장자명을 가져오기
			String oldName=oldFile.getName();//업로드된 파일명을 가져온것, 확장자명까지 같이 있다.
			String ext="";
			int jum=oldName.lastIndexOf(".");
			if(jum>-1) ext=oldName.substring(jum);
			
			String fileName=sdf.format(new Date(currentTime))+"_"+dot+ext;//새로운 파일의 이름
			newFile=new File(oldFile.getParent(), fileName); //매개변수로 폴더경로를 받는다, 그 경로에 새 파일을 저장한다
			
		}while(!createNewFile(newFile));
		
		
		return newFile;
	}
	
	private boolean createNewFile(File f) {
		try {
			//파일이 생성되면 true를 반환
			//파일이 저장되는 경로에 동일한 이름의 파일이 있으면? 파일을 생성하지 않고 false를 반환
			//파일이 있다면
			return f.createNewFile();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false; //파일이 생성되지 못하면 false를 반환
	}
	
	

}
