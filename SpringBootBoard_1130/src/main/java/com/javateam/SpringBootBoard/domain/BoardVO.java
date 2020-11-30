/**
 * 
 */
package com.javateam.SpringBootBoard.domain;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oracle
 *
 */
@Data
@NoArgsConstructor
public class BoardVO {
	
	private int boardNum; // 글 번호
	private String boardName; // 글 작성자
	private String boardPass; // 글 비밀번호
	private String boardSubject; // 글 제목
	private String boardContent; // 글 내용
	private String oriBoardFile; // 첨부 파일(원래 파일명)
	private String boardFile; // 첨부 파일(인코딩된 파일명)
	private int boardReRef; // 관련글 번호
	private int boardReLev; // 답글 레벨
	private int boardReSeq; // 관련글 중 출력 순서
	private int boardReadCount = 0; // 조회수
	private Date boardDate; // 작성일
	
	// BoardDTO -> BoardVO
    public BoardVO(BoardDTO board) {
       
        this.boardNum = board.getBoardNum();
        this.boardName = board.getBoardName();
        this.boardPass = board.getBoardPass();
        this.boardSubject = board.getBoardSubject();
        this.boardContent = board.getBoardContent();
        this.oriBoardFile = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        this.boardFile = board.getBoardFile().getOriginalFilename(); // 파일명 저장
        this.boardReRef = board.getBoardReRef();
        this.boardReLev = board.getBoardReLev();
        this.boardReSeq = board.getBoardReSeq();
        this.boardReadCount = board.getBoardReadCount();
        this.boardDate = board.getBoardDate();
    }
    
}