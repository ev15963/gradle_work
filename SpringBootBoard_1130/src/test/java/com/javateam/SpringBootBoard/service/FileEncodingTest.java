package com.javateam.SpringBootBoard.service;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import com.javateam.SpringBootBoard.util.FileNamingEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@WebAppConfiguration  
@Slf4j  
public class FileEncodingTest {
	
	@Autowired
	private FileNamingEncoder fne;
	
	@Autowired
	private ServletContext sc;
	
	String filename;
		
	@Test
	public void test() {
	
		filename = "abcd.pdf";
		String savePath = sc.getRealPath("/resources/upload/");
	 	log.info("### savePath : " + savePath);
	 		 	
		log.info("변환 파일명 : " + fne.enFilename(filename));
		log.info("변환 파일 경로/파일명 : " + savePath + "\\" + fne.enFilename(filename));
		
	}
	

}
