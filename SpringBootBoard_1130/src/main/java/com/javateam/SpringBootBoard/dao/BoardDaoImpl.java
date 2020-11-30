package com.javateam.SpringBootBoard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.SpringBootBoard.domain.BoardVO;
import com.javateam.SpringBootBoard.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeBoard(BoardVO boardVO) {

		log.info("dao writeBoard");
		boolean flag = false;
		
		try {
			sqlSession.getMapper(BoardMapper.class).writeBoard(boardVO);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return flag;
	} //
	
	@Override
	public int getBoardNumByLastSeq() {

		log.info("dao getBoardNumByLastSeq");
		return sqlSession.getMapper(BoardMapper.class).getBoardNumByLastSeq();
	}	
	
	@Override
	public BoardVO getBoard(int boardNum) {
		
		log.info("dao selectBoard");
		return sqlSession.getMapper(BoardMapper.class).getBoard(boardNum);
	} //

	@Override
	public boolean updateReadCount(int boardNum) {

		log.info("dao updateReadCount");
		boolean flag = false;
		
		try {
			sqlSession.getMapper(BoardMapper.class).updateReadCount(boardNum);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		
		return flag;
	} //

	@Override
	public List<BoardVO> getArticleList(int page, int rowsPerPage) {

		log.info("dao getArticleList");
		return sqlSession.getMapper(BoardMapper.class).getArticleList(page, rowsPerPage);
	}

	@Override
	public int getListCount() {

		log.info("dao getListCount");
		return sqlSession.getMapper(BoardMapper.class).getListCount();
	}

	@Override
	public boolean updateBoard(BoardVO boardVO) {

		log.info("dao updateBoard");
		boolean flag = false;
		
		try {
			sqlSession.getMapper(BoardMapper.class).updateBoard(boardVO);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		
		return flag;
	}
	
	@Override
	public boolean replyWriteBoard(BoardVO boardVO) {

		log.info("dao replyWriteBoard");
		boolean flag = false; 
		
		try {
			sqlSession.getMapper(BoardMapper.class).replyWriteBoard(boardVO);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public void updateBoardByRefAndSeq(int boardReRef, int boardReSeq) {
		
		log.info("dao updateBoardByRefAndSeq");
		sqlSession.getMapper(BoardMapper.class).updateBoardByRefAndSeq(boardReRef, boardReSeq);
	}

	@Override
	public boolean deleteBoard(int boardNum) {

		log.info("dao deleteBoard");
		boolean flag = false; 
		
		try {
			sqlSession.getMapper(BoardMapper.class).deleteBoard(boardNum);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public int getCountReplys(int boardNum) {

		log.info("dao getCountReplys");
		
		return sqlSession.getMapper(BoardMapper.class).getCountReplys(boardNum);
	}

	@Override
	public List<BoardVO> getBoardBySearch(String searchKind, 
										 String searchWord, 
										 int rowsPerPage, 
										 int page) {

		log.info("dao getBoardBySearch");
		log.info("검색 구분 : {}", searchKind);
		log.info("검색어 : {}", searchWord);
		
		return sqlSession.getMapper(BoardMapper.class).getBoardBySearch(searchKind, searchWord, rowsPerPage, page);
	}

	@Override
	public int getCountBoardBySearch(String searchKind, String searchWord) {

		log.info("dao getBoardBySearch");
		log.info("검색 구분 : {}", searchKind);
		log.info("검색어 : {}", searchWord);
		
		return sqlSession.getMapper(BoardMapper.class).getCountBoardBySearch(searchKind, searchWord);
	}
	
}