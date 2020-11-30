/**
 * 
 */
package com.javateam.SpringBootBoard.service;

import org.springframework.web.multipart.MultipartFile;

import com.javateam.SpringBootBoard.domain.FileVO;

/**
 * @author javateam
 *
 */
public interface FileUploadService {
	
	FileVO storeUploadFile(int boardNum, MultipartFile file);

}