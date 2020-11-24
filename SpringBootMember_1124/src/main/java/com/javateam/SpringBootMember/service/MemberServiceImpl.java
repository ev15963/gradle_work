package com.javateam.SpringBootMember.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.SpringBootMember.dao.MemberDao;
import com.javateam.SpringBootMember.domain.MemberVO;
import com.javateam.SpringBootMember.domain.Users;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private AuthMyBatisService authMyBatisService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 

	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	@Override
	public void insertMember(MemberVO member) {
		
		log.info("svc insertMember");
		dao.insertMember(member);
		
		// 패쓰워드 암호화
		String hashedPassword = passwordEncoder.encode(member.getMemberPassword());
		
		Users users = new Users();
		users.setUsername(member.getMemberId());
		users.setPassword(hashedPassword);
		users.setEnabled(1);
		
		// 암호화 패쓰워드 및 롤(Role) 정보 저장
		authMyBatisService.insertUsers(users, "ROLE_USER");
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isMember(String id) {
		
		log.info("svc isMember");
		return dao.isMember(id);
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isEnableEmail(String email) {
		
		log.info("svc isEnableEmail");
		return dao.isEnableEmail(email);
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isEnablePhone(String phone) {

		log.info("svc isEnablePhone");
		return dao.isEnablePhone(phone);
	}
	
	@Transactional(readOnly=true)
	@Override
	public MemberVO getMember(String memberId) {
		
		log.debug("svc getMember");
		return dao.getMember(memberId);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void updateMember(MemberVO member) {
		
		log.debug("svc updateMember");
		dao.updateMember(member);
		
		log.info("### 패쓰워드 :"+ member.getMemberPassword());
				
		// 패쓰워드 없으면(공백) 패쓰워드 변경 구문 통과(pass)
		if (!member.getMemberPassword().trim().contentEquals("")) {
			
			log.info("############### 패쓰워드 변경 ############");
			// 패쓰워드 암호화
			log.info("--------------------------------------");
			BCryptPasswordEncoder passwordEncoder 
				= new BCryptPasswordEncoder();
			String hashedPassword 
				= passwordEncoder.encode(member.getMemberPassword());
			
			Users users = new Users();
			users.setUsername(member.getMemberId());
			users.setPassword(hashedPassword);
			users.setEnabled(1);
			
			log.info("###### 신규 패쓰워드 : " + users.getPassword());
			
			// 암호화 패쓰워드 및 롤(Role) 정보 저장
			authMyBatisService.updateUsers(users);
		} else  {
			log.info("##### 패쓰워드 불변 ######");	
		} //		
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void deleteMember(String memberId) {

		log.debug("svc deleteMember");
		dao.deleteMember(memberId);
		
		authMyBatisService.deleteUsers(memberId);
	}
	
	@Transactional(readOnly=true)
	@Override
	public boolean isEnableEmail(String memberId, String email) {
		log.debug("svc isEnableEmail(update)");
		return dao.isEnableEmail(memberId, email);
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isEnablePhone(String memberId, String phone) {
		log.debug("svc isEnablePhone(update)");
		return dao.isEnablePhone(memberId, phone);
	}

}