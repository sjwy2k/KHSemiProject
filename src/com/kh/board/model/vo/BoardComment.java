package com.kh.board.model.vo;

import java.util.Date;

public class BoardComment {
//	"BOARD_COMMENT_NO" NUMBER,
//	"BOARD_COMMENT_LEVEL" NUMBER DEFAULT 1,
//	"BOARD_COMMENT_WRITER" VARCHAR2(15), 
//	"BOARD_COMMENT_CONTENT" VARCHAR2(2000), 
//	"BOARD_REF" NUMBER, 
//	"BOARD_COMMENT_REF" NUMBER, 
//	"BOARD_COMMENT_DATE" DATE DEFAULT SYSDATE, 
	private int boardCommentNo;
	private int boardCommentLevel;
	private String boardCommentWriter;
	private String boardCommentContent;
	private int boardRef;
	private int boardCommentRef;
	private Date boardCommentDate;
	
	public BoardComment() {
		// TODO Auto-generated constructor stub
	}

	
	
	public BoardComment(int boardCommentNo, int boardCommentLevel, String boardCommentWriter,
			String boardCommentContent, int boardRef, int boardCommentRef, Date boardCommentDate) {
		super();
		this.boardCommentNo = boardCommentNo;
		this.boardCommentLevel = boardCommentLevel;
		this.boardCommentWriter = boardCommentWriter;
		this.boardCommentContent = boardCommentContent;
		this.boardRef = boardRef;
		this.boardCommentRef = boardCommentRef;
		this.boardCommentDate = boardCommentDate;
	}



	public int getBoardCommentNo() {
		return boardCommentNo;
	}

	public void setBoardCommentNo(int boardCommentNo) {
		this.boardCommentNo = boardCommentNo;
	}

	public int getBoardCommentLevel() {
		return boardCommentLevel;
	}

	public void setBoardCommentLevel(int boardCommentLevel) {
		this.boardCommentLevel = boardCommentLevel;
	}

	public String getBoardCommentWriter() {
		return boardCommentWriter;
	}

	public void setBoardCommentWriter(String boardCommentWriter) {
		this.boardCommentWriter = boardCommentWriter;
	}

	public String getBoardCommentContent() {
		return boardCommentContent;
	}

	public void setBoardCommentContent(String boardCommentContent) {
		this.boardCommentContent = boardCommentContent;
	}

	public int getBoardRef() {
		return boardRef;
	}

	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}

	public int getBoardCommentRef() {
		return boardCommentRef;
	}

	public void setBoardCommentRef(int boardCommentRef) {
		this.boardCommentRef = boardCommentRef;
	}

	public Date getBoardCommentDate() {
		return boardCommentDate;
	}

	public void setBoardCommentDate(Date boardCommentDate) {
		this.boardCommentDate = boardCommentDate;
	}



	@Override
	public String toString() {
		return "BoardComment [boardCommentNo=" + boardCommentNo + ", boardCommentLevel=" + boardCommentLevel
				+ ", boardCommentWriter=" + boardCommentWriter + ", boardCommentContent=" + boardCommentContent
				+ ", boardRef=" + boardRef + ", boardCommentRef=" + boardCommentRef + ", boardCommentDate="
				+ boardCommentDate + "]";
	}
	
	

}
