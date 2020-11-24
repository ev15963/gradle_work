package com.javateam.SpringBootMember.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class IsEmailTest {
	
	@Autowired MemberDao memberDao;
	String email;
	String id;
	
	@BeforeEach
	void setUp() throws Exception {
//		email = "none@email.com"; 
		email = "fsd@naver.com";
		id = "dddd123456";
	}

	@Test
	void test() {
		assertTrue(memberDao.isEnableEmail(id, email));
		
	}

}
