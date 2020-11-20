package com.javateam.demoMyBatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javateam.demoMyBatis.service.EmployeeService;
import com.javateam.demoMyBatis.vo.EmployeesVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoRestController {
	
	@Autowired
    private EmployeeService service;
	
    @RequestMapping("/getOneJson")
    public ResponseEntity<EmployeesVO> getOne(@RequestParam("employeeId") int id, Model model) {
       
        log.info("getOne");
        
        HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		ResponseEntity<EmployeesVO> result = null;
		EmployeesVO empVO = service.getOne(id);

		try {

			if (empVO != null) {
				result = new ResponseEntity<EmployeesVO>(empVO, HttpStatus.OK);
			} else {
				result = new ResponseEntity<EmployeesVO>(empVO, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			log.error("JSON 생성 오류 ");
			result = new ResponseEntity<EmployeesVO>(empVO, HttpStatus.EXPECTATION_FAILED);
		}
		
		return result;
    }

}