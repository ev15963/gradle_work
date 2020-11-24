/**
 * 
 */
package com.javateam.demoSpringSecurity.controller;

/**
 * @author javateam
 *
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javateam.demoSpringSecurity.domain.User;
import com.javateam.demoSpringSecurity.service.SecurityService;
import com.javateam.demoSpringSecurity.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebController {
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private SecurityService securityService;
  
  @RequestMapping(value="/main")
  public String main(){
    return "main";
  }
  
  // 로그인 
  @RequestMapping("/login")
  public String login(Model model, String error, String logout, HttpServletRequest request ){
    if (logout != null){
      model.addAttribute("logout", "로그아웃 처리되었습니다.");
    }
    return "login";
  }
  
  // 로그인 실패시
  @RequestMapping(value="/loginError")
  public String loginError(Model model, String username ){
	  
	model.addAttribute("error", "패쓰워드가 잘못되었습니다.");
	model.addAttribute("username", username);
	
	return "login";
  }
  
  // 회원가입폼 
  @RequestMapping(value="/registration",method=RequestMethod.GET)
  public String registration(Model model){
	  
	log.info("회원가입");  
    model.addAttribute("userForm", new User());
    return "registration";
  }
  
  // 회원가입 처리 후 로그인 
  @RequestMapping(value="/memberJoin",method=RequestMethod.POST)
  public String registration(@ModelAttribute("userForm") User userForm, 
		  					 BindingResult bindingResult, 
  					 		 Model model,
  					 		 HttpServletRequest request,
  					 		 String[] roles ){
	  
	log.info("회원가입 처리");
	log.info("회원가입 정보 : {}", userForm);
	
	String password = userForm.getPassword();
	String errMsg = "";
	String returnPath = "";
	boolean flag = false;
	
	// 이 부분에서 예외처리 없이 진행될 경우 중복 데이터 그대로 유입되는 버그 발생할 수 있음.
	try {
			userService.saveUser(userForm,roles) ;
			flag = true;
			
	} catch (Exception e) {
		log.error("회원저장 에러 처리 " + e);
		flag = false;
	}
	
	if (flag== true) {
		
		log.info("회원가입 정상 처리");
		errMsg = "회원가입 처리되었습니다.";
		
		securityService.autologin(userForm.getUsername(), password); // 자동 로그인
		returnPath = "redirect:/main";
		
	} else {
		
		log.info("회원가입 처리시 에러가 발생했습니다");
		errMsg = "예외가 발생하여 회원가입이 처리되지 않았습니다.";
		model.addAttribute("err_msg", errMsg);
		returnPath = "exception";
	}	
    
    return returnPath;
  }
  
  // admin 사용자 테스트 
  // @Secured({"ADMIN"}) // 추가 : role 접근 제한 기능  
  @RequestMapping("/admin")
  public String admin(){
    return "/admin/admin";
  }
  
  // user 사용자 테스트 
  // @Secured({"USER"}) // 추가 : role 접근 제한 기능  
  @RequestMapping("/user")
  public String user(){
    return "/user/user";
  }
  
  // 권한없는 페이지를 들어갔을때 
  @RequestMapping("/403")
  public String access(){
    return "/access";
  }

}