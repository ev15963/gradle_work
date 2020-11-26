package com.javateam.SpringMediaDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.javateam.SpringMediaDemo.domain.ProductVO;

import lombok.extern.java.Log;

/**
 * Handles requests for the application home page.
 */
@Controller
@Log
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		
		log.info("home");
		
		return "redirect:/product/a1234.json";
	}
	
	// xml, json
	@ResponseBody 
	@RequestMapping(value = "/product/{id}", 
					headers="Accept=*/*", 
					method = RequestMethod.GET,
					produces = {"application/json", "application/xml"})
	public ProductVO product(@PathVariable String id, Model model) {
		
		log.info("product");
		
		ProductVO product = new ProductVO();
		product.setId(id);
		product.setName("마우스");
		product.setDetail("게임용 마우스");
		
		return product;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pdf/{id}", 
					method = RequestMethod.GET)
	public ModelAndView getPdf(@PathVariable String id, Model model) {
		
		log.info("pdf");
		
		ProductVO product = new ProductVO();
		product.setId(id);
		product.setName("mouse");
		product.setDetail("게임용 마우스");
		
		return new ModelAndView("pdfView", "product", product);
	} //
	
	@ResponseBody
	@RequestMapping(value = "/excel/{id}", 
					method = RequestMethod.GET)
	public ModelAndView getExcel(@PathVariable String id, Model model) {
		
		log.info("pdf");
		
		ProductVO product = new ProductVO();
		product.setId(id);
		product.setName("마우스");
		product.setDetail("게임용 마우스");
		
		return new ModelAndView("excelView", "product", product);
	} //
	
}
