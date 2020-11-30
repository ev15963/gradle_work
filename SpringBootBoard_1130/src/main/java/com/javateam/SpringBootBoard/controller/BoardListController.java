package com.javateam.SpringBootBoard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.domain.PageVO;
import com.javateam.SpringBootBoard.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardListController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/boardList.do/{currentPage}")
	public String listBoard(@PathVariable Optional<Integer> currentPage,
							Model model) {
		
		log.info("전체 게시글 보기");
		
		int limit = 10; // 페이지당 글수
		List<BoardVO> articleList;
		
		int page = currentPage.isPresent() ? currentPage.get() : 1; // page 설정

		// 총페이지수
		int listCount = boardService.getListCount();
		
		articleList= boardService.getArticleList(page, limit);
		
		// 총 페이지 수
   		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		int startPage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
   	    int endPage = startPage + 10 - 1;
   	    
   	    if (endPage> maxPage) endPage= maxPage;
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setPage(page);
		pageVO.setStartPage(startPage);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("articleList", articleList);
		
		return "/board/boardList";
	} //
	
}