package com.javateam.SpringBootBoard.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
	
	private int boardNum; // 글 번호
	private String boardName; // 글 작성자
	private String boardPass; // 글 비밀번호
	private String boardSubject; // 글 제목
	private String boardContent; // 글 내용
	private MultipartFile boardFile; // 첨부 파일
	private int boardReRef; // 관련글 번호
	private int boardReLev; // 답글 레벨
	private int boardReSeq; // 관련글 중 출력 순서
	private int boardReadCount = 0; // 조회수
	private Date boardDate; // 작성일

}