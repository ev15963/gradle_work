package com.example.demo_1113.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DemoController {
	@GetMapping("/")
	public String home(Model model) {
		log.info("home");

		model.addAttribute("java", "1.8");

		return "home";

	}

}