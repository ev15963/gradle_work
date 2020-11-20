package com.javateam.demoMyBatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javateam.demoMyBatis.service.EmployeeService;

import lombok.extern.java.Log;

@Controller
@Log
public class DemoController {
	
	@Autowired
    private EmployeeService service;
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/getAll";
    }
   
    @RequestMapping("/getAll")
    public String getAll(Model model) {
       
        log.info("getAll");
        model.addAttribute("list", service.getAll());
        return "getAll";
    }
   
    @RequestMapping("/getOne/{id}")
    public String getOne(@PathVariable("id") int id, Model model) {
       
        log.info("getOne");
        model.addAttribute("employees", service.getOne(id));
        return "getOne";
    }

}