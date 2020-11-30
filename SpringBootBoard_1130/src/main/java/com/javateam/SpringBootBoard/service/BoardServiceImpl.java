package com.javateam.SpringBootBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.javateam.SpringBootBoard.dao.BoardDao;
import com.javateam.SpringBootBoard.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	/*
	 * 트랜잭션 매니저(transactionManager)를 직접 와이어링 하거나 트랜잭션 템플릿(transactionTemplate)을 와이어링
	 * 하는 방식 둘 중 하나를 사용하여 트랜잭션 제어 프로그래밍을 할 수 있습니다.
	 */

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager transactionManager;

	@Autowired
	private TransactionTemplate transactionTemplate;

	// 플래그 변수를 익명 클래스 메서드 안에 등록하는 에러나는 부분을 패치 -> 멤버 변수화
	private boolean flag = false;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean writeBoard(final BoardVO boardVO) {

		log.info("Service writeBoard");

		// 방법-1

		/*
		 * transactionTemplate.execute(new TransactionCallbackWithoutResult() {
		 * 
		 * @Override protected void doInTransactionWithoutResult(TransactionStatus
		 * status) {
		 * 
		 * try { flag = boardDao.writeBoard(boardVO); if (flag == false) throw new
		 * Exception();
		 * 
		 * } catch(Exception e) { status.setRollbackOnly(); flag = false;
		 * log.debug("Service writeBoard Exception : " + e.getMessage()); } // } // });
		 * 
		 * return flag;
		 */

		// 방법-2

		// MyBatis guide : http://www.mybatis.org/spring/ko/transactions.html
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			flag = boardDao.writeBoard(boardVO);

			if (flag == true) {
				transactionManager.commit(status);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			transactionManager.rollback(status);
			flag = false;
			e.printStackTrace();
		}

		return flag;
	} //

	@Override
	public int getBoardNumByLastSeq() {

		log.info("Service getBoardNumByLastSeq");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		int result = 0;

		try {
			result = boardDao.getBoardNumByLastSeq();
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getBoardNumByLastSeq Exception : " + e.getMessage());
		}

		return result;
	}

	@Override
	public BoardVO getBoard(int boardNum) {

		log.info("Service getBoard");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		BoardVO boardVO = new BoardVO();

		try {
			boardVO = boardDao.getBoard(boardNum);
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getBoard Exception : " + e.getMessage());
		}

		return boardVO;
	} //

	@Override
	public void updateReadCount(int boardNum) {

		log.info("Service updateReadCount");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		boolean flag = false;

		try {
			flag = boardDao.updateReadCount(boardNum);
			if (flag == true) {
				transactionManager.commit(status);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			log.error("Service updateReadCount Exception : " + e.getMessage());
			transactionManager.rollback(status);
			e.printStackTrace();
		} //

	} //

	@Override
	public List<BoardVO> getArticleList(int page, int rowsPerPage) {

		log.info("Service getArticleList");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		List<BoardVO> list = null;

		try {
			list = boardDao.getArticleList(page, rowsPerPage);
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getArticleList Exception : " + e.getMessage());
		}

		return list;
	}

	@Override
	public int getListCount() {

		log.info("Service getListCount");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		int result = 0;

		try {
			result = boardDao.getListCount();
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getListCount Exception : " + e.getMessage());
		}

		return result;
	} //

	@Override
	public boolean updateBoard(BoardVO boardVO) {

		log.info("Service updateBoard");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		boolean flag = false;

		try {
			flag = boardDao.updateBoard(boardVO);
			if (flag == true) {
				transactionManager.commit(status);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			log.error("Service updateBoard Exception : " + e.getMessage());
			transactionManager.rollback(status);
			flag = false;
			e.printStackTrace();
		} //

		return flag;
	}

	@Override
	public boolean replyWriteBoard(BoardVO boardVO) {

		log.info("Service replyWriteBoard");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		boolean flag = false;

		try {
			flag = boardDao.replyWriteBoard(boardVO);
			if (flag == true) {
				transactionManager.commit(status);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			log.error("Service replyWriteBoard Exception : " + e.getMessage());
			transactionManager.rollback(status);
			flag = false;
			e.printStackTrace();
		} //

		return flag;
	} //

	@Override
	public void updateBoardByRefAndSeq(int boardReRef, int boardReSeq) {

		log.info("Service updateBoardByRefAndSeq");

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				try {
					boardDao.updateBoardByRefAndSeq(boardReRef, boardReSeq);
				} catch (Exception e) {
					status.setRollbackOnly();
					log.debug("Service updateBoardByRefAndSeq Exception : " + e.getMessage());
				} //
			} //
		});

	} //

	@Override
	public boolean deleteBoard(int boardNum) {

		log.info("Service deleteBoard");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		boolean flag = false;

		try {
			flag = boardDao.deleteBoard(boardNum);
			if (flag == true) {
				transactionManager.commit(status);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {

			log.error("Service deleteBoard Exception : " + e.getMessage());
			transactionManager.rollback(status);
			flag = false;
			e.printStackTrace();
		} //

		return flag;
	} //

	@Override
	public int getCountReplys(int boardNum) {

		log.info("Service getCountReplys");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		int result = 0;

		try {
			result = boardDao.getCountReplys(boardNum);
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getCountReplys Exception : " + e.getMessage());
		}

		return result;
	} //

	@Override
	public List<BoardVO> getBoardBySearch(String searchKind, String searchWord, int rowsPerPage, int page) {

		log.info("Service getBoardBySearch");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		List<BoardVO> result = null;

		try {
			result = boardDao.getBoardBySearch(searchKind, searchWord, rowsPerPage, page);
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getBoardBySearch Exception : " + e.getMessage());
		}

		return result;
	} //

	@Override
	public int getCountBoardBySearch(String searchKind, String searchWord) {

		log.info("Service getCountBoardBySearch");

		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		int result = 0;

		try {
			result = boardDao.getCountBoardBySearch(searchKind, searchWord);
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("Service getBoardBySearch Exception : " + e.getMessage());
		}

		return result;
	}

}