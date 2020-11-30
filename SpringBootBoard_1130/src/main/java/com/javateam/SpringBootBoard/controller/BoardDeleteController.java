package com.javateam.SpringBootBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.SpringBootBoard.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardDeleteController {

	@Autowired
	private BoardService boardService;
							
	@RequestMapping(value="/deleteBoardProcREST.do/{boardNum}",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> deleteBoardProc(@PathVariable("boardNum") int boardNum,
												  @RequestParam("boardPass") String boardPass) {

		log.info("############# deleteBoardProcREST.do ##################");
		
		boolean flag = false; // 글삭제 성공여부 플래그 추가
		String msg = "";
		
		// 답글 여부 점검하며 삭제 !
		if (boardService.getCountReplys(boardNum) > 0)	{ // 댓글 있는 원글
			
			msg += "답글이 있는 글은 삭제할 수 없습니다.";
			
		} else { // 댓글 없는 원글
			
			// 패쓰워드 점검
			String dbPass = boardService.getBoard(boardNum).getBoardPass();
			
			if (!dbPass.contentEquals(boardPass)) {
				
				msg += "패쓰워드가 일치하지 않습니다. ";
				
			} else {
			
				flag = boardService.deleteBoard(boardNum);
			} //
		} //
		
		if (flag == false) {
			msg += "글삭제에 실패하였습니다.";
		} else {
			msg += "글삭제에 성공하였습니다.";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		return new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
	} //
	
}