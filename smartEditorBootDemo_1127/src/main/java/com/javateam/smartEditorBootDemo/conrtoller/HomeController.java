package com.javateam.smartEditorBootDemo.conrtoller;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		
		return "/smartEditor/write_form";
	}
	
	@PostMapping(value="write_proc.do", produces="text/plain; charset=UTF-8")
	@ResponseBody
	public String writeAction(HttpServletRequest request, 
				@RequestParam Map<String, String> map) {

		log.info("###### 게시글 저장 ");
		
		String result = "";
		
		map.forEach((x,y) -> {
			log.info("인자 : "+ x + "=" + y);
		});
				
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			result +=  key + "=" + value + "\n";
			log.info("result = "+ result);
		}
		
		/*
		 * Map<String, String[]> map2 = request.getParameterMap(); Set<String> keys =
		 * map2.keySet(); Iterator<String> it = keys.iterator();
		 * 
		 * while (it.hasNext()) {
		 * 
		 * String key = it.next(); String value = map2.get(key)[0]; log.info("인자 : "+key
		 * + "=" + value + "<br>"); }
		 */		
		
		return result;
	}
	
	@GetMapping("/photo_upload.do")
	public String photoUpload() {
		return "/smartEditor/photo_uploader";
	}
	
	@GetMapping("/smart_editor2_inputarea")
	public String smJs() {
		
		return "/smartEditor/smart_editor2_inputarea";
	}
	
	@GetMapping("/smart_editor2_inputarea_ie8")
	public String smJs2() {
		
		return "/smartEditor/smart_editor2_inputarea_ie8";
	}

}
