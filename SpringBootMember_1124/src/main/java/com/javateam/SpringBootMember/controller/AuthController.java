package com.javateam.SpringBootMember.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javateam.SpringBootMember.domain.CustomUser;

import lombok.extern.slf4j.Slf4j;
 
 
@Controller
@Slf4j
public class AuthController {
	
	@RequestMapping("/welcome.do")
	public String welcome() {
		
		log.info("home");
		return "/auth/welcome";
	}
    
    @RequestMapping(value= {"/admin/home.do", "/admin"}, 
					method = RequestMethod.GET)
	public String securedAdminHome(ModelMap model) {
	
    	log.info("/admin/home.do");
    	
		Object principal = SecurityContextHolder.getContext()
												.getAuthentication()
												.getPrincipal();
		CustomUser user = null;
		
		if (principal instanceof CustomUser) {
			user = ((CustomUser)principal);
		}
		
		log.info("user : " +user);
		
		String name = user.getUsername();
		model.addAttribute("username", name);
		model.addAttribute("message", "관리자 페이지에 들어오셨습니다.");
		
		return "/admin/home";
    }
    
    @RequestMapping(value= {"/secured/home.do", "/secured"}, 
    				method = RequestMethod.GET)
    public String securedHome(ModelMap model) {
    	
    	log.info("/secured/home.do");
    	
        Object principal = SecurityContextHolder.getContext()
        										.getAuthentication()
        										.getPrincipal();
        
        CustomUser user = null;
        
        if (principal instanceof CustomUser) {
        	user = ((CustomUser)principal);
        }
        
        log.info("user : "+user);
     
	    String name = user.getUsername();
	    model.addAttribute("username", name);
	    model.addAttribute("message", "일반 사용자 페이지에 들어오셨습니다.");
	    
	    return "/secured/home";
    }
    
    @RequestMapping(value="/login.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
    	
    	log.info("login.do");
    	
    	return "/auth/login";
    }
	
	
	@RequestMapping(value="/login_proc.do", method = RequestMethod.POST)
	public String loginProc() {
	  
	  	log.debug("login_proc.do");
	  
	  	return "/auth/login"; 
  	}
	 
    
    @RequestMapping(value="/logout_proc.do", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
    					 HttpServletResponse response) {
    	
    	log.info("logout_proc.do");
    	
	    Authentication auth 
	    	= SecurityContextHolder.getContext().getAuthentication();
	    
	    log.info("auth : "+auth);
	    
	    // logout !
	    if (auth != null) {    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }

	    return "/auth/logout";
    } //
    
    @RequestMapping(value="/login_error.do", method = RequestMethod.GET)
    public String loginError(ModelMap model, HttpSession session) {
    	
    	// Spring CustomProvider 인증(Auth) 에러 메시지 처리
    	log.info("##### 로그인 에러 처리 페이지 #####");
        Object secuSess = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        log.info("#### 인증 오류 메시지 : " + secuSess);
        log.info("#### 인증 오류 메시지 : " + secuSess.toString());

        model.addAttribute("error", "true");
        model.addAttribute("msg", secuSess);

        return "/auth/login";
    } //
    
    // 권한없는 페이지를 들어갔을때
    @RequestMapping("/403")
    public String accessDenied(){
    	return "/error/403";
    }
    
    
    
} //