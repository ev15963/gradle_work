package com.example.demoJPA_1116.service;
/**
 * 
 * @author java
 *
 */
public interface DemoService {
	/**
	 * 회원 정렬
	 * @param str 정렬 방향 ex) 오름(ASC)/내림차순(DESC)
	 * @return 상태코드
	 * @throws Exception 예외처리
	 */
	public void order(String str) throws Exception;
}
