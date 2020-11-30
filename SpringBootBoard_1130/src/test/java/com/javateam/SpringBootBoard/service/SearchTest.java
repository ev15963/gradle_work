package com.javateam.SpringBootBoard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.javateam.SpringBootBoard.dao.BoardDao;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@WebAppConfiguration  
@Slf4j  
public class SearchTest {
	
	@Autowired
	private BoardDao dao;
	
	@Autowired
	private BoardService svc;
	
	String searchWord;
	int rowsPerPage; 
	int page;
	
	@BeforeEach // jUnit4의 @Before와 같은 기능 
	public void setUp() {
		
		searchWord = "길동";
		rowsPerPage = 5;
		page = 1;
	}

	@DisplayName("유사 검색 결과")
	@Test
	public void test() {
		
		log.info("검색 결과 : "+svc.getBoardBySearch("제목", searchWord, rowsPerPage, page).size());
		assertEquals(5, svc.getBoardBySearch("제목", searchWord, rowsPerPage, page).size());
	}
	
}