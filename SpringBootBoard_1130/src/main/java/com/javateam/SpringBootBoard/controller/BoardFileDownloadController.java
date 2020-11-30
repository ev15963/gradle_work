package com.javateam.SpringBootBoard.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javateam.SpringBootBoard.util.FileNamingEncoder;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardFileDownloadController {
	
	// @Autowired 
	// private ResourceLoader resourceLoader;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private FileNamingEncoder fileNamingEncoder;
	
	private String getBrowser(HttpServletRequest request) {
		
		  String header = request.getHeader("User-Agent");

		  if (header.contains("MSIE")) {
			  return "MSIE";
	  	  } else if(header.contains("Trident")) {
	  		  return "MSIE11";
		  } else if(header.contains("Chrome")) {
			  return "Chrome";
		  } else if(header.contains("Opera")) {
			  return "Opera";
		  }

		  return "Firefox";
	}
	
	// 주의사항) {fileName:.+} 에서 ".+" 를 포함하지 않으면 파일의 확장자가 인자로 넘어오지 않음. 
	@RequestMapping(value="/upload/{boardNum}/{fileName:.+}", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 @PathVariable("boardNum") int boardNum,
    						 @PathVariable("fileName") String fileName) throws IOException {
		
		log.info("####### fileName : "+ fileName);
		
		// String filePath = resourceLoader.getResource("resources/boardUpload/"+fileName)
		//								.getURI()
		//								.getPath();
		// String filePath = servletContext.getRealPath("/resources/upload/") + fileName;
		
		// spring boot static 경로 사용시 
		String filePath = servletContext.getRealPath("/WEB-INF/classes/static/upload/") + fileName;
		
		// 접근 방지(access denied) 발생
		log.info("접근 권한 여부(읽기) : "+ new File(filePath).canRead());
		// 읽기 권한 가능으로 재설정
		new File(filePath).setReadable(true);
		log.info("접근 권한 재설정후 여부(읽기) : "+ new File(filePath).canRead());
		
		
		log.info("########### file Path : " + filePath);
     
        File file = null;
        file = new File(filePath);
         
        if(!file.exists()){
        	
            String msg = "파일이 존재하지 않습니다.";
            log.error(msg);
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        
        if (mimeType == null){
        	
            log.error("mimetype 인지 불가. 기본 Mime으로 설정 !");
            mimeType = "application/octet-stream";
        }
         
        log.error("mimetype : "+mimeType);
        
        // fileName = file.getName();
        // 원래 파일명
        fileName = fileNamingEncoder.decodeFilename(fileName);
        		
        // 다운로드시 한글 파일 깨짐 현상 방지
        String header = getBrowser(request);

        if (header.contains("MSIE")) {
        	String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
	        response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
        } else if (header.contains("Firefox")) {
	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        } else if (header.contains("Opera")) {
	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        } else if (header.contains("Chrome")) {
        	String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
        }

        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        
        response.setContentType(mimeType);
        response.setContentLength((int)file.length());
        
        // boardNum을 제외한 원래 파일명으로 저장
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    } //

}