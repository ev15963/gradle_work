package com.javateam.SpringBootBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.service.BoardService;
import com.javateam.SpringBootBoard.util.FileNamingEncoder;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("board")
public class BoardViewRestController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileNamingEncoder fileNamingEncoder; 
	
	// JSON Feed Service : 개별 게시글 보기
	@RequestMapping(value="/boardDetailREST.do/boardNum/{boardNum}",
					produces="application/json; charset=UTF-8")
	public ResponseEntity<String> boardDetailFeed(@PathVariable("boardNum") int boardNum) 
	{
		log.info("##### boardDetailREST.do #####");
		
		// HTTP Header 정보 setting
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		
		// 조회수 업데이트(갱신)
		boardService.updateReadCount(boardNum);

		String json="";
		ObjectMapper mapper = new ObjectMapper();
		BoardVO board = boardService.getBoard(boardNum);
		
		// 첨부 파일명 원래 파일명으로 변경 처리 : 마지막 첨가되었던 접미사("_난수") 제거
		if (board.getBoardFile() != null) {
			
			String fileName = fileNamingEncoder.decodeFilename(board.getBoardFile());
			board.setOriBoardFile(fileName);
			board.setBoardFile(board.getBoardFile());
			
			log.info("### 원래 파일 명 : {}", board.getBoardFile());
		} // 
	       
        try {
               json = mapper.writeValueAsString(board);
                
        } catch (JsonProcessingException e) {
            log.error("json exception !");
            e.printStackTrace();
        }
		
        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	} //
	
}