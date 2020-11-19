package com.example.SpringJsonFeed_1118.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SpringJsonFeed_1118.domain.TestVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JsonFeedService {

	@RequestMapping(value="/jsonFeed", produces="application/json; charset=UTF-8")
	public String jsonFeed(@RequestParam("id") String id, Model model) throws JsonProcessingException  {
		
		log.info("jsonFeed");
		
		String json =new ObjectMapper().writeValueAsString(new TestVO(id, "홍길동", "서울 구로"));
		model.addAttribute("json", json);
		return "jsonFeed";
	}
	
	@RequestMapping(value="/jsonFeed2", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String jsonFeed2(@RequestParam("id") String id) throws JsonProcessingException {
		
		log.info("jsonFeed2");
		String json = new ObjectMapper().writeValueAsString(new TestVO(id, "홍길동", "서울 구로"));
		return json;
	}
	
	@RequestMapping(value = "/jsonFeed3", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<TestVO> jsonFeed3(@RequestParam("id") String id) {

		log.info("jsonFeed3");

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		ResponseEntity<TestVO> re = null;
		TestVO testVO = this.getTestVO(id);

		try {

			if (testVO != null) {
				re = new ResponseEntity<TestVO>(testVO, HttpStatus.OK);
			} else {
				re = new ResponseEntity<TestVO>(testVO, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			log.error("JSON 생성 오류 ");
			re = new ResponseEntity<TestVO>(testVO, HttpStatus.EXPECTATION_FAILED);
		}

		return re;
	} //

	@RequestMapping(value = "/jsonFeed4/id/{id}", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> boardDetailFeed(@PathVariable("id") String id) {
		
		log.info("jsonFeed4");

		// HTTP Header 정보 setting
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		TestVO testVO = this.getTestVO(id);

		try {
			
			json = mapper.writeValueAsString(testVO);
			
		} catch (JsonProcessingException e) {
			log.error("json exception !");
			e.printStackTrace();
		}

		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	} //

	private TestVO getTestVO(String id) {
		return new TestVO(id, "홍길동", "서울 구로");
	}
}
