package com.javateam.SpringBootBoard.util;

public interface FileNamingEncoder {

	/**
	 * 실제 저장 파일명 암호화 처리<br> 
	 * 효과) 업로드 파일들의 중복 방지
	 *  
	 * @param fileName 원본 파일명 ex) abcd.pdf 
	 * @return 업로드할 파일명 ex) abcd_암호화코드.pdf
	 */
	String enFilename(String fileName);
	
	/**
	 * 실제 저장 파일명 복호화 처리(원래 파일명 복원) 
	 * 
	 * @param encodedFilename 인코딩된 파일명
	 * @return 원래 파일명
	 */
	String decodeFilename(String encodedFilename);

}