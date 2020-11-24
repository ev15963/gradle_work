package com.javateam.SpringBootMember.dao;

import com.javateam.SpringBootMember.domain.MemberVO;

public interface MemberDao {
	
	/**
	 * 회원 가입 처리
	 * @param member 회원정보
	 */
	void insertMember(MemberVO member);
	
	/**
	 * 회원 존재 여부 점검(회원 아이디 중복 점검)
	 * @param id 회원 아이디
	 * @return 회원 존재 여부
	 */
	boolean isMember(String id);
	
	/**
	 * 이메일 중복 점검
	 * @param email 이메일
	 * @return 이메일 중복 점검 여부
	 */
	boolean isEnableEmail(String email);
	
	/**
	 * 휴대폰 중복 점검
	 * @param phone
	 * @return 휴대폰 중복 점검 여부
	 */
	boolean isEnablePhone(String phone);
	
	/**
	 * 개별 회원정보 레코드(튜플) 조회(read)
	 * 
	 * @param memberId 회원 아이디
	 * @return 회원정보
	 * @throws Exception 예외처리
	 */
	MemberVO getMember(String memberId);
	
	/**
	 * 개별 회원정보 레코드(튜플) 수정(갱신) (update)
	 * 
	 * @param member 회원정보
	 * @throws Exception 예외처리
	 */
	void updateMember(MemberVO member);
	
	/**
	 * 개별 회원정보 레코드(튜플) 삭제 (delete)
	 * 
	 * @param memberId 회원 아이디
	 * @throws Exception 예외처리
	 */
	void deleteMember(String memberId);
	
	/**
	 * 주어진 회원 아이디의 이메일을 타 회원들과 중복하지 않고 사용가능한지 여부 점검
	 * 
	 * usage) 회원 가입/변경시 이메일 중복 점검
	 * 
	 * @param memberId 회원 아이디
	 * @param email 이메일
	 * @return 이메일 사용가능 여부
	 * @throws Exception 예외처리
	 */
	boolean isEnableEmail(String memberId, String email);
	
	/**
	 * 연락처가 타 회원들과 중복하지 않고 사용가능한지 여부 점검
	 * 
	 * usage) 회원 수정  연락처 중복 점검
	 * 
	 * @param memberId 회원 아이디
	 * @param phone 연락처
	 * @return 연락처 사용가능 여부
	 * @throws Exception 예외처리
	 */
	boolean isEnablePhone(String memberId, String phone);
	
	
}