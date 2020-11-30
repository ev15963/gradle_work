package com.javateam.SpringBootBoard.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.SpringBootBoard.util.FileNamingEncoder;

@RestController 
public class CKImageUploadController {

	private static final Logger log = LoggerFactory.getLogger(CKImageUploadController.class);

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private FileNamingEncoder fne; 
	
	// 기본 업로드 폴더
	private final String UPLOAD_URL = "/static/upload"; // 웹 경로
	
	// 업로드 설정
    private final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 최대 파일 크기 40MB
    private final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // request 전송되는 파일 사이즈 : 파일사이즈 + form data , 50MB

	// 이미지 업로드
	// product_edit페이지에서 맵핑되는 메소드
	@RequestMapping(value="imageUploadCK.do", 
					produces="application/json; charset=UTF-8", 
					method=RequestMethod.POST)
	public String imageUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		log.info("------------- imageUploadCK.do ---------------------");
		
		String seperator = java.io.File.separator;

		int intRtnUpload = 0; // 반환할 업로드 수
		String strRtnMessage = ""; // 반환할 에러메세지.
		String strRtnFileUrl = ""; // 반환할 웹경로와 파일명
		String strRtnFileName = ""; // 반환할 파일명

		Map<String, Object> map = new HashMap<String, Object>(); // json 파일로 반환하기 위해

		// 멀티파트(multipart)로 전송여부 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
			strRtnMessage = "Error: multipart/form-data 로 전송해야 합니다.";
			return "";
		}

		// 업로드 폴더
		String uploadPath = servletContext.getRealPath("/WEB-INF/classes/static/upload");

		// 업로드 폴더가 없는 경우 하위폴더도 같이 생성
		File uploadDir = new File(uploadPath);
		
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		uploadDir = null;

		// 웹 경로
		String sUploadUrl = request.getContextPath() + UPLOAD_URL; // 교정 

		log.info("upload physical dir:" + uploadPath + " , upload web dir:" + sUploadUrl);

		// =======================================
		// 업로드 설정
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(MEMORY_THRESHOLD); // sets memory threshold - beyond which files are stored in disk
		dfif.setRepository(new File(System.getProperty("java.io.tmpdir"))); // 임시 저장 폴더 설정

		// 파일 업로드
		ServletFileUpload upload = new ServletFileUpload(dfif);
		upload.setHeaderEncoding("UTF-8");  // 한글 처리
		upload.setFileSizeMax(MAX_FILE_SIZE); // sets maximum size of upload file
		upload.setSizeMax(MAX_REQUEST_SIZE); // sets maximum size of request (include file + form data)

		try {
			// @SuppressWarnings("unchecked") // 컴파일러 경고중 unchecked 는 제외합니다.

			// request 대신 upload 하는 것으로 받아야 됨.
			List<FileItem> formItems = upload.parseRequest(request);

			// 전송된 것이 null 이 아니고 사이즈가 있는 경우만 실행
			if (formItems != null && formItems.size() > 0) {

				for (FileItem item : formItems) {

					if (item.isFormField()) {
						// form field 인 경우
						String FormFieldName = item.getFieldName(); // input 박스 Name
						String FormFieldValue = item.getString("UTF-8"); // 컨트롤의 값을 한글로 받아주기

						log.info("input 박스 Name:{" + FormFieldName + "} . Value:{" + FormFieldValue + "}");

					} else {
						// 파일이 사이즈가 있으면 진행..
						// 업로드된 파일정보
						//long lFileSize = item.getSize(); // 파일 사이즈
						//String sContentType = item.getContentType(); // 컨텐츠 유형 image/png
						String sOrgFileName = item.getName(); // 원본 파일명 a.png

						// a.txt에서 a , txt 분리
						//String sFileName = org.apache.commons.io.FilenameUtils.getBaseName(sOrgFileName);
						String sFileExtension = org.apache.commons.io.FilenameUtils.getExtension(sOrgFileName)
								.toLowerCase(); // 확장자는 소문자로 변환

//    	            			log.info(
//    	            					"파일사이즈:" + lFileSize + 
//    	            					" . 컨텐츠유형:{"+sContentType+"}" + 
//    	            					" . 원본파일명:{"+sOrgFileName+"}" +
//    	            					" . 파일명:{"+sFileName+"} . 파일확장자:{"+sFileExtension+"}"
//    	            					);

						// 확장자 검사
						String[] lAbleExt = { "bmp", "png", "jpg", "pdf", "xls", "xlsx", "ppt", "pptx" };
						boolean bAbleExt = false;
						for (String ableExt : lAbleExt) {
							// log.info("업로드 가능 파일 확장자 : /" + ableExt + "/" + sFileExtension + "/");
							if (ableExt.equals(sFileExtension)) {
								bAbleExt = true;
								break;
							}
						}

						// log.info("업로드 가능 여부 : " + bAbleExt );

						// 파일 업로드
						if (bAbleExt == false) {
							item.delete(); // request 받은 파일도 삭제한다.
							strRtnMessage = "업로드 불가 확장자명[" + lAbleExt.toString() + "]입니다.";
							// log.info(strRtnMessage);
							// request.setAttribute("message",strRtnMessage );
						} else {

							// 업로드되는 고유명을 만든다.
							log.info("file upload start");

							/*
							// 저장 경로 : 기본경로 / images / 20190101 / 파일
							// 시간
							DateTimeFormatter dtt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
							LocalDateTime localDate = LocalDateTime.now();

							String prefixDate = dtf.format(localDate); // 20190101
							String prefixFileDate = dtt.format(localDate); // 20190101245959

							// 저장되는 물리,웹경로를 만든다 .. 이미지는 images , 다른 건 files
							if (sFileExtension.equals("bmp") || sFileExtension.equals("png")
									|| sFileExtension.equals("jpg")) {
								uploadPath += seperator + "images" + seperator + prefixDate;
								sUploadUrl += seperator + "images" + seperator + prefixDate;
							} else {
								uploadPath += seperator + "files" + seperator + prefixDate;
								sUploadUrl += seperator + "files" + seperator + prefixDate;
							}

							// 업로드도 폴더가 없는 경우 생성
							File uploadDir2 = new File(uploadPath);
							if (!uploadDir2.exists()) {
								uploadDir2.mkdirs();
							}
							uploadDir2 = null;

							log.info("upload directory:" + uploadPath + " fileUrl:" + sUploadUrl);

							// 파일중복을 위해 파일명을 변경한다. a_111111.png
							// String sSaveFileName = sFileName + "_" +
							// java.util.UUID.randomUUID().toString() + "." + sFileExtension ;
							String sSaveFileName = sFileName + "_" + prefixFileDate + "." + sFileExtension;

							// 업로드할 경로와 파일명을 설정한다.
							String filePath = uploadPath + seperator + sSaveFileName; // 물리 경로
							String fileUrl = sUploadUrl + seperator + sSaveFileName; // 웹 경로
							
							*/
							
							String enFileName = fne.enFilename(sOrgFileName);
							String filePath =  uploadPath + "/" + enFileName;
							String fileUrl = sUploadUrl + seperator + enFileName;

							// 파일 개체를 만들어 복사한다.
							File savedFile = new File(filePath);
							
							try {
								item.write(savedFile); // 파일 저장 item --> storeFile 에 쓰기 해준다.

								if (savedFile.exists()) {

									intRtnUpload = 1; // 업로드 성공했음.
									// strRtnFileName = sSaveFileName; // 파일명만
									strRtnFileUrl = fileUrl; // 웹상의 경로

									strRtnMessage = "Upload has been done successfully.";
									log.info(strRtnMessage);

									// request.setAttribute("status","success");
									// request.setAttribute("message","Upload has been done successfully! " +
									// filePath );

								} else {
									strRtnMessage = "생성한 파일이 존재하지 않습니다.";
									log.info(strRtnMessage);
									// request.setAttribute("message","Error : Create file" + filePath );
								}
							} catch (Exception e) {
								strRtnMessage = "파일생성을 실패했습니다.";
								log.info(strRtnMessage);
							} finally {
								savedFile = null; // 파일 개체 해제.
								item.delete(); // request 한 파일도 삭제한다.
							}
						} // end able

					} // end FormField
				} // end for
			} // end formitem

		} catch (Exception e) {
			strRtnMessage = "Upload Fail:" + e.getMessage();
			log.info(strRtnMessage);
			// request.setAttribute("message",strRtnMessage);
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////

		// 반환할 내용
		String strJson = "";
		map.put("uploaded", intRtnUpload);
		map.put("filename", strRtnFileName);
		map.put("url", strRtnFileUrl);

		// json으로 변환
		try {

			ObjectMapper mapper = new ObjectMapper();
			strJson = mapper.writeValueAsString(map);

		} catch (Exception e) {
			strJson = "";
		} 

		return strJson; // JSON
	}
}
