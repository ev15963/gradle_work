package com.javateam.SpringBootBoard.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@WebAppConfiguration
@Slf4j
public class WriteBoardDaoTest {
	
	@Autowired
	private BoardService svc;
	
	private BoardVO vo;
	
	@BeforeEach
	public void setup() {
		
		vo = new BoardVO();
		vo.setBoardName("데모글 작성자");
		vo.setBoardPass("123456789");
		vo.setBoardSubject("데모 글제목");
		vo.setBoardContent("데모 글내용");
		vo.setBoardFile("");
		vo.setBoardReLev(0);
		vo.setBoardReSeq(0);
		vo.setBoardReadCount(0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test() {
		
		log.info("test");
		log.info("vo : " + vo);
		
		assertTrue(svc.writeBoard(vo));
	} // 

}