package com.javateam.smartEditorBootDemo.conrtoller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageUploadController {
	
    @Autowired
    private ServletContext servletContext;
	
	@RequestMapping(value="image_uploader.do", produces="text/plain; charset=UTF-8")
	@ResponseBody
	public String imageUploader(HttpServletRequest request) throws IOException {
		
		// 로컬경로에 파일 저장하기 ============================================
		String sFileInfo = "";

		// 파일명 - 싱글파일업로드와 다르게 멀티파일업로드는 HEADER로 넘어옴 
		String name = request.getHeader("file-name");

		// 확장자
		String ext = name.substring(name.lastIndexOf(".")+1);

		// 파일 기본경로
		// String defaultPath = request.getServletContext().getRealPath("/WEB-INF/classes/static/upload/");
		String savePath = servletContext.getRealPath("/WEB-INF/classes/static/upload/");

		// 파일 기본경로 _ 상세경로
		// String path = defaultPath + "upload" + File.separator;
		// String path = defaultPath + File.separator;
		String path = savePath + File.separator;

		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}

		String realname = UUID.randomUUID().toString() + "." + ext;
		InputStream is = request.getInputStream();
		OutputStream os = new FileOutputStream(path + realname);
		int numRead;

		// 파일쓰기
		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
		while((numRead = is.read(b,0,b.length)) != -1) {
			os.write(b,0,numRead);
		}

		if(is != null) {
			is.close();
		}

		os.flush();
		os.close();

		System.out.println("path : "+path);
		System.out.println("realname : "+realname);

		// 파일 삭제
//	 	File f1 = new File(path, realname);
//	 	if (!f1.isDirectory()) {
//	 		if(!f1.delete()) {
//	 			System.out.println("File 삭제 오류!");
//	 		}
//	 	}

		// sFileInfo += "&bNewLine=true&sFileName="+ name+"&sFileURL="+"/upload/"+realname;
		
		// javateacher
		sFileInfo += "&bNewLine=true&sFileName="+ name+"&sFileURL=" + request.getContextPath() + "/static/upload/"+realname;
		// out.println(sFileInfo); // 중요 ! 피드되는 인자값이므로 지우면 안됨 ! 화면 인쇄로 피드되는 방식(javateacher) 

		// ./로컬경로에 파일 저장하기 ============================================
		
		return sFileInfo;
	}

}
