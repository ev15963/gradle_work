/**
 * 
 */
package com.javateam.SpringBootMember.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.SpringBootMember.dao.AuthMyBatisDao;
import com.javateam.SpringBootMember.domain.CustomUser;
import com.javateam.SpringBootMember.domain.Role;
import com.javateam.SpringBootMember.domain.Users;


/**
 * @author javateam
 *
 */
@Service
public class AuthMyBatisServiceImpl implements AuthMyBatisService {
	
	@Autowired
	private AuthMyBatisDao dao;
	
	@Transactional(readOnly=true)
	@Override
	public boolean hasUsername(String username) {
		return dao.hasUsername(username);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void insertUsers(Users users, String role) {
		dao.insertUsers(users, role);
	}

	@Transactional(readOnly=true)
	@Override
	public CustomUser loadUserByUsername(String username) {
		CustomUser customUser = new CustomUser();
		Users users = dao.loadUserByUsername(username);
		customUser.setUsername(username);
		customUser.setPassword(users.getPassword());
		customUser.setEnabled(users.getEnabled()==1 ? true : false);
		return customUser;
	}

	@Transactional(readOnly=true)
	@Override
	public Role loadUserRole(String username) {
		return dao.loadUserRole(username);
	}

	@Override
	public void updateUsers(Users users) {
		dao.updateUsers(users);
	}

	@Override
	public void deleteUsers(String username) {
		dao.deleteUsers(username);
	}

} //