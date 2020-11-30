package com.javateam.SpringBootBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	
	@RequestMapping("/")
	public String boardHome() {
		
		log.info("home");		
		// return "redirect:/board/writeBoardForm.do";
		// return "redirect:/board/boardView.do?boardNum=2";
		return "redirect:/board/boardList.do/1";
		// return "/board/demo";
	}
	
}