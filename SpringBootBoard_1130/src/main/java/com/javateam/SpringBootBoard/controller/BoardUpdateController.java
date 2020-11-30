package com.javateam.SpringBootBoard.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.domain.FileVO;
import com.javateam.SpringBootBoard.service.BoardService;
import com.javateam.SpringBootBoard.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardUpdateController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private BoardService boardService;

	// 일반글 수정 처리
	@RequestMapping(value="/updateBoard.do/{boardNum}", 
					produces="text/html; charset=UTF-8")
	@ResponseBody
	public String updateBoard(@PathVariable("boardNum") int boardNum,
							  @RequestParam(value="update_boardFile_new", required=false) MultipartFile file,
							  HttpServletRequest req) {

		log.info("########### updateBoard ############");
		
		String msg = "";
		boolean flag = false; // 업데이트 성공여부 플래그 추가
		
		log.info("###### 글수정 인자 내용 ######");
		
		req.getParameterMap().forEach((x,y) -> {
			log.info(x + " = " + y[0]);
		});
				
		// 패쓰워드 비교 점검
		String dbPass = boardService.getBoard(boardNum).getBoardPass();
		String inputPass = req.getParameter("update_boardPass");
		
		if (!dbPass.contentEquals(inputPass)) {
			
			msg += "패쓰워드가 일치하지 않습니다. 글 수정에 실패하였습니다.";
			return msg;
		}
		
		/////////////////////////////////////////////////////////////
		
		if (file != null) {
			log.info("### 첨부 파일 : " + file.getOriginalFilename());
		}	
		
		if (req.getParameter("update_boardSubject") == null || 
				req.getParameter("update_boardSubject").equals(""))
			msg += "글제목을 입력하십시오.\n";

		// ckeditor 적용에 따른 글내용 필드 변경
		if (req.getParameter("update_boardContent") == null || 
				req.getParameter("update_boardContent").equals(""))
			msg += "글내용을 입력하십시오.";
		
		if (!msg.equals("")) return msg; // 오류 있으면 메시지 전송

		log.info("msg : {}", msg);

		// 인자 출력
		log.info("update_boardNum : {}", req.getParameter("update_boardNum"));
		log.info("update_boardName : {}", req.getParameter("update_boardName"));
		log.info("update_boardSubject : {}", req.getParameter("update_boardSubject"));
		// log.info("update_boardContent : {}", req.getParameter("update_boardContent"));
		log.info("update_boardContent : {}", req.getParameter("new_update_boardContent")); // ckeditor 적용시
		
		if (file != null) {
			log.info("fileName : {}", file.getOriginalFilename());
		}	

		// 기존 정보 가져오기
		BoardVO boardVO = new BoardVO(); // 갱신할 VO
		BoardVO oldBoardVO = boardService.getBoard(boardNum);
		boardVO = oldBoardVO; // 

		// 게시글 수정
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardName(req.getParameter("update_boardName"));
		boardVO.setBoardSubject(req.getParameter("update_boardSubject"));
		// boardVO.setBoardContent(req.getParameter("update_boardContent"));
		
		boardVO.setBoardContent(req.getParameter("new_update_boardContent"));  // ckeditor 적용시

		// 주의) 편의상 다른 게시글에서도 동일한 첨부가 있을 경우 문제가 되므로 
		//      기존 파일 삭제는 생략하였습니다. 이러한 경우는 파일 업로드시 계정별 폴더 세분화     
		//      혹은 업로드 파일마다 접미사(순번 등) 등을 이용하여 처리할 수 있습니다.
		// 기존 파일과 비교
		// 추가) 첨부 파일 없을 경우(답글 수정시) 조건 추가 
		if (file==null ||
			file.getOriginalFilename()==null || 
			file.getOriginalFilename().trim().equals("")) {

			log.info("############ 신규 첨부 파일 없을 때 ############");
			
			// 기존 파일 저장
			log.info("기존 첨부 파일 : {}", oldBoardVO.getBoardFile());
			
			boardVO.setBoardFile(oldBoardVO.getBoardFile());
			
			// 추가 : 첨부 파일이 애시당초 없을 경우
			if (oldBoardVO.getBoardFile() == null) {
				boardVO.setBoardFile("");
			}
			
		} else {
			
			log.info("############ 신규 첨부 파일 있을 때 ############");

			log.info("fileName : {}", file.getOriginalFilename());
			
			// 첨부 파일 처리
		    FileVO fileVO = fileUploadService.storeUploadFile(boardNum, file);
		    boardVO.setBoardFile(fileVO.getFileName());
		    
		    log.info("첨부 파일 저장 메시지 : " + fileVO.getMsg());
		} //

		// 게시글 갱신 등록일 : 12시간 기준 hh: -> 24시간 기준으로 변경 HH:
		String dateString 
			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				  .format(new Date(System.currentTimeMillis())).toString();

		log.info("등록일 {}", dateString);
		boardVO.setBoardDate(new Date(System.currentTimeMillis()));

		log.info("########## boardVO : {}", boardVO);

		flag = boardService.updateBoard(boardVO);

		if (flag == false) {
			msg += "글수정에 실패하였습니다.";
		} else {
			msg += "글수정에 성공하였습니다.";
		}
		
		return msg;
	} //
	
	
	// 답글(댓글) 수정 처리
	@RequestMapping(value="/replyUpdateBoard.do/{boardNum}", 
					produces="text/html; charset=UTF-8")
	@ResponseBody
	public String replyUpdateBoard(@PathVariable("boardNum") int boardNum,
								   HttpServletRequest req) {

		log.info("########### replyUpdateBoard ############");
		
		String msg = "";
		boolean flag = false; // 업데이트 성공여부 플래그 추가
		
		log.info("###### 답글 수정 인자 내용 ######");
		
		req.getParameterMap().forEach((x,y) -> {
			log.info(x + " = " + y[0]);
		});
				
		// 패쓰워드 비교 점검
		String dbPass = boardService.getBoard(boardNum).getBoardPass();
		String inputPass = req.getParameter("reply_update_boardPass");
		
		if (!dbPass.contentEquals(inputPass)) {
			
			msg += "패쓰워드가 일치하지 않습니다. 글 수정에 실패하였습니다.";
			return msg;
		}
		
		/////////////////////////////////////////////////////////////
		
		if (req.getParameter("reply_update_boardSubject") == null || 
				req.getParameter("reply_update_boardSubject").equals(""))
			msg += "글제목을 입력하십시오.\n";

		if (req.getParameter("reply_update_boardContent") == null || 
				req.getParameter("reply_update_boardContent").equals(""))
			msg += "글내용을 입력하십시오.";

		if (!msg.equals("")) return msg; // 오류 있으면 메시지 전송

		log.info("msg : {}", msg);

		// 인자 출력
		log.info("reply_update_boardNum : {}", req.getParameter("reply_update_boardNum"));
		log.info("reply_update_boardName : {}", req.getParameter("reply_update_boardName"));
		log.info("reply_update_boardSubject : {}", req.getParameter("reply_update_boardSubject"));
		log.info("reply_update_boardContent : {}", req.getParameter("reply_update_boardContent")); 
		
		// 기존 정보 가져오기
		BoardVO boardVO = new BoardVO(); // 갱신할 VO
		BoardVO oldBoardVO = boardService.getBoard(boardNum);
		boardVO = oldBoardVO; // 

		// 게시글 수정
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardName(req.getParameter("reply_update_boardName"));
		boardVO.setBoardSubject(req.getParameter("reply_update_boardSubject"));
		boardVO.setBoardContent(req.getParameter("reply_update_boardContent"));
		boardVO.setBoardFile(""); // 답글이므로 첨부 파일 없음.

		// 게시글 갱신 등록일 : 12시간 기준 hh: -> 24시간 기준으로 변경 HH:
		String dateString 
			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				  .format(new Date(System.currentTimeMillis())).toString();

		log.info("등록일 {}", dateString);
		boardVO.setBoardDate(new Date(System.currentTimeMillis()));

		log.info("########## boardVO : {}", boardVO);

		flag = boardService.updateBoard(boardVO);

		if (flag == false) {
			msg += "답글수정에 실패하였습니다.";
		} else {
			msg += "답글수정에 성공하였습니다.";
		}
		
		return msg;
	} //

}
