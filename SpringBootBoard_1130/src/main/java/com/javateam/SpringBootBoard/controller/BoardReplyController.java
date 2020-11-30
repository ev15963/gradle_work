package com.javateam.SpringBootBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardReplyController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/replyWriteBoardProc.do")
	@ResponseBody
	public String replyWriteBoardProc(BoardVO boardVO) { 
		
		log.info("############## replyWriteBoardProc.do ################");

		log.info("########### boardVO : "+boardVO.toString());
		
		boolean msg = false;
		
		// 답글 등록 관련 변수
		// int reRef = boardVO.getBoardReRef(); // 댓글의 원 게시글 번호
		int reRef = boardVO.getBoardNum(); // 댓글의 원 게시글 번호
		int reLev = boardVO.getBoardReLev(); // 댓글의 레벨
		int reSeq = boardVO.getBoardReSeq(); // 댓글의 순서
		
		boardService.updateBoardByRefAndSeq(reRef, reSeq);
		
		// 답글 관련 인자 계산
		reSeq = reSeq + 1;
		reLev = reLev + 1;
		
		boardVO.setBoardReSeq(reSeq);
		boardVO.setBoardReLev(reLev);
		
		// 원글 번호 -> 원글 참고 번호
		boardVO.setBoardReRef(reRef);
		
		msg = boardService.replyWriteBoard(boardVO);
		
		return msg+"";
	}
	
}