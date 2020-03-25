package com.kh.notice.model.vo;

import java.util.Date;

public class Notice {
//	NOTICE_NO NUMBER PRIMARY KEY,
//    NOTICE_TITLE VARCHAR2(100) NOT NULL,
//    NOTICE_WRITER VARCHAR2(15) NOT NULL,
//    NOTICE_CONTENT VARCHAR2(4000) NOT NULL,
//    NOTICE_DATE DATE DEFAULT SYSDATE,
//    FILEPATH VARCHAR2(300),
//    STATUS VARCHAR2(1) DEFAULT 'Y',
//    constraint fk_notice_writer FOREIGN KEY (NOTICE_WRITER) REFERENCES MEMBER (USERID)
	private int noticeNo;
    private String noticeTitle;
    private String noticeWriter;
    private String noticeContent;
    private Date noticeDate;
    private String filePath;
    private String status;

    public Notice() {
		// TODO Auto-generated constructor stub
	}
    
	public Notice(int noticeNo, String noticeTitle, String noticeWriter, String noticeContent, Date noticeDate,
			String filePath, String status) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeWriter = noticeWriter;
		this.noticeContent = noticeContent;
		this.noticeDate = noticeDate;
		this.filePath = filePath;
		this.status = status;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeWriter() {
		return noticeWriter;
	}

	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeWriter=" + noticeWriter
				+ ", noticeContent=" + noticeContent + ", noticeDate=" + noticeDate + ", filePath=" + filePath
				+ ", status=" + status + "]";
	}
    
    
	

}
