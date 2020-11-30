package com.javateam.SpringBootBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.SpringBootBoard.domain.BoardDTO;
import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.domain.FileVO;
import com.javateam.SpringBootBoard.service.BoardService;
import com.javateam.SpringBootBoard.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardWriteController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/writeBoardForm.do")
	public String writeBoardForm(Model model) {
		
		log.info("writeBoardForm");
		
		return "/board/writeBoardForm";
	}
	
	@RequestMapping(value="/writeBoardProcREST.do",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String writeBoardProc(@ModelAttribute("boardDTO") BoardDTO boardDTO,
							     @RequestParam(value="boardFile", required=false) MultipartFile file) {

		log.info("############# writeBoardProc ##################");
		
		log.info("########### BoardDTO : {}", boardDTO);
		
		boolean flag = false; // 글쓰기 성공여부 플래그 추가
		String msg = "";
		int boardNum = 0;
		
        // 추가 : 신규 boardNum 값 구하기 (sequence)
	    boardNum = boardService.getBoardNumByLastSeq();
	    log.info("시퀀스 게시글 번호 : " + boardNum);
	    
		// 첨부 파일 처리
	    FileVO fileVO = fileUploadService.storeUploadFile(boardNum, file);
	    
		// 저장 VO 생성
		BoardVO boardVO = new BoardVO(boardDTO);		
		boardVO.setBoardFile(!file.isEmpty() && file != null ? fileVO.getFileName() : "");
		
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardReRef(boardNum);
		log.info("############### boardVO : {}", boardVO);
		
		flag = boardService.writeBoard(boardVO);
		
		if (flag == false) {
			msg += "글쓰기에 실패하였습니다.";
		} else {
			msg += "글쓰기에 성공하였습니다.";
		}
		
		return msg;
	} //
	
	@RequestMapping(value="/writeBoardProc.do",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	public String writeBoardProc(@ModelAttribute("boardDTO") BoardDTO boardDTO) {
		
		log.info("############# writeBoardProc ##################");
				
		log.info("VO : {}", boardDTO);
		
	    MultipartFile file = boardDTO.getBoardFile(); // 업로드 파일
        int boardNum = 0;
        
        // 추가 : 신규 boardNum 값 구하기 (sequence)
	    boardNum = boardService.getBoardNumByLastSeq();
	    
	    log.info("시퀀스 게시글 번호 : " + boardNum);
	    
		// 첨부 파일 처리
	    FileVO fileVO = fileUploadService.storeUploadFile(boardNum, file);
		
	    log.info("첨부 파일 처리 : "+ fileVO.getMsg());
      
	    BoardVO boardVO = new BoardVO(boardDTO);
	    boardVO.setBoardFile(!boardDTO.getBoardFile().isEmpty() && file != null ? fileVO.getFileName() : "");
	    
	    boardVO.setBoardNum(boardNum);
		boardVO.setBoardReRef(boardNum);
		log.info("############### boardVO : {}", boardVO);
		
		boardService.writeBoard(boardVO);
		
		return "redirect:/";
	} //

}