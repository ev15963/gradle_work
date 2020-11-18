package com.example.SpringJsonFeed_1118.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String jsonFeed2(@RequestParam("id") String id) throwJsonProcessingException  {
		
		///미완성
	}
}
