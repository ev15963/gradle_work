package com.javateam.springFormSpringValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.javateam.springFormSpringValidation.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@RequestMapping("/")
	public String home(Model model) {
		
		log.info("home");
		
		model.addAttribute("memberVO", new MemberVO());
		
		return "/form";
	}
	
	@Autowired
	private MemberVOValidator validator;
	
	@RequestMapping("/form")
	public String form(Model model) {
		
		log.info("model form !");
		
		model.addAttribute("memberVO", new MemberVO());
		
		return "form";
	} //
	
	@RequestMapping("formAction") 
	public ModelAndView formAction(@ModelAttribute("memberVO") MemberVO memberVO,
								   BindingResult result) {
		
		log.info("spring form-validation formAction ");
		
		// Spring form validate
		this.validator.validate(memberVO, result);
		
		ModelAndView model = new ModelAndView();
		
		if (result.hasErrors()) {
			
			log.error("폼 에러가 존재합니다.");
			
			model.getModel().putAll(result.getModel());
			model.setViewName("/form");
			
			return model;
			
		} else {	
			
			try {
					log.error(memberVO.toString());
					model.setViewName("/formActionResult");
					
			} catch(Exception e)	{
				
				result.reject("정상적인 정보가 아닙니다.");
				model.getModel().putAll(result.getModel());
				model.setViewName("/form");
				
				return model;
			} // try
			
		} //
		
		return model;
	} //
	
}