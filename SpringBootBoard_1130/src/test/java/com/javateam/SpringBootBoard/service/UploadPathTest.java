package com.javateam.SpringBootBoard.service;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@WebAppConfiguration  
@Slf4j  
public class UploadPathTest {
	
	@Autowired
	private ServletContext servletContext;
	
	
	@DisplayName("이미지 저장 경로 검색 결과")
	@Test
	public void test() {
		
		String path2 = servletContext.getRealPath("/upload/");
		log.info("검색 결과 : "+ path2);
		
	}
	
}