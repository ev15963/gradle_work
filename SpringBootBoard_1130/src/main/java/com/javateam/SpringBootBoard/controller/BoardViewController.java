package com.javateam.SpringBootBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardViewController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/boardView.do")
	public String boardView(@RequestParam("boardNum") int boardNum,
						Model model) 
	{
		log.info("##### boardView.do #####");
		
		// 조회수 업데이트(갱신)
		boardService.updateReadCount(boardNum);

		BoardVO board = boardService.getBoard(boardNum);
		
		// 첨부 파일명 원래 파일명으로 변경 처리 : 마지막 첨가되었던 접미사("_게시글 아이디") 제거
		if (board.getBoardFile() != null) {
			
			// "_게시글 아이디" -> 공백 (치환)
			String fileName = board.getBoardFile().replaceAll("_"+board.getBoardNum(), "");
			board.setBoardFile(fileName);
			
			log.info("### 원래 파일 명 : {}", board.getBoardFile());
		} //
		
		model.addAttribute("board", board);
		
		return "/board/viewBoardPopup";
	       
	} //
	
}