package com.javateam.SpringBootMember.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.SpringBootMember.domain.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDaoImpl implements MemberDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertMember(MemberVO member) {

		log.info("dao insertMember");
		sqlSession.insert("mapper.MemberMapper.insertMember", member);
	}

	@Override
	public boolean isMember(String id) {

		log.info("dao isMember");
		return (int)sqlSession.selectOne("mapper.MemberMapper.isMember", id) == 1 ? true : false;
	}

	@Override
	public boolean isEnableEmail(String email) {
		
		log.info("dao isEnableEmail");
		return (int)sqlSession.selectOne("mapper.MemberMapper.isEnableEmail", email) == 1 ? true : false; 
	}

	@Override
	public boolean isEnablePhone(String phone) {

		log.info("dao isEnablePhone");
		return (int)sqlSession.selectOne("mapper.MemberMapper.isEnablePhone", phone) == 1 ? true : false;
	}
	
	@Override
	public MemberVO getMember(String memberId) {

		log.debug("dao getMember");
		return sqlSession.selectOne("mapper.MemberMapper.getMember", memberId);
	}
	
	@Override
	public void updateMember(MemberVO member) {

		log.debug("dao updateMember");
		sqlSession.update("mapper.MemberMapper.updateMember", member);
	}

	@Override
	public void deleteMember(String memberId) {

		log.debug("dao deleteMember");
		sqlSession.delete("mapper.MemberMapper.deleteMember", memberId);
	}
	
	@Override
	public boolean isEnableEmail(String memberId, String email) {
		
		log.debug("dao isEnableEmail");
		log.info("아이디 : "+memberId);
		log.info("이메일 : "+email);		
		
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("email", email);
		
		return (int)sqlSession.selectOne("mapper.MemberMapper.isEnableEmailUpdate", map) == 1 ? true : false;
	}

	@Override
	public boolean isEnablePhone(String memberId, String phone) {
		
		log.debug("dao isEnablePhone");
		
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("phone", phone);
		
		return (int)sqlSession.selectOne("mapper.MemberMapper.isEnablePhoneUpdate", map) == 1 ? true : false;
	}

}