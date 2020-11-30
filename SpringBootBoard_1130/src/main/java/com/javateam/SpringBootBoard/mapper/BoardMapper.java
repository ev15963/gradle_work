package com.javateam.SpringBootBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.javateam.SpringBootBoard.domain.BoardVO;

public interface BoardMapper {
	
	void writeBoard(BoardVO boardVO);
	int getBoardNumByLastSeq(); // 마지막 시퀀스 리턴하는 메서드
	
	BoardVO getBoard(int boardNum);
	void updateReadCount(int boardNum);
	
	List<BoardVO> getArticleList(@Param("page") int page, 
								 @Param("rowsPerPage") int rowsPerPage);
	int getListCount();
	void updateBoard(BoardVO boardVO);
	
	void replyWriteBoard(BoardVO boardVO);
	void updateBoardByRefAndSeq(@Param("boardReRef") int boardReRef, 
								@Param("boardReSeq") int boardReSeq);
	
	void deleteBoard(int boardNum);
	int getCountReplys(int boardNum);
	
	List<BoardVO> getBoardBySearch(@Param("searchKind") String searchKind,
								   @Param("searchWord") String searchWord,
								   @Param("rowsPerPage") int rowsPerPage,
								   @Param("page") int page);
	
	int getCountBoardBySearch(@Param("searchKind") String searchKind, 
							  @Param("searchWord") String searchWord);	

}