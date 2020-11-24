/**
 * 
 */
package com.javateam.SpringBootMember.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.SpringBootMember.domain.Role;
import com.javateam.SpringBootMember.domain.Users;

import lombok.extern.slf4j.Slf4j;

/**
 * @author javateam
 *
 */
@Repository
@Slf4j
public class AuthMyBatisDaoImpl implements AuthMyBatisDao {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * @see com.javateam.SpringMember.service.AuthMyBatisService#hasUsername(java.lang.String)
	 */
	@Override
	public boolean hasUsername(String username) {

		log.info("hasUsername");
		
		return (int)sqlSession.selectOne("mapper.UserMapper.hasUsername", username) == 1 ? true : false;
	} //

	/**
	 * @see com.javateam.SpringMember.service.AuthMyBatisService#insertUsers(com.javateam.springSecuritySample1.vo.Users, java.lang.String)
	 */
	@Override
	public void insertUsers(Users users, String role) {

		log.info("insertUsers");
		
		Map<String, String> map = new HashMap<>();
		sqlSession.insert("mapper.UserMapper.insertUser", users);

		map.put("username", users.getUsername());
		map.put("role", role);
		sqlSession.insert("mapper.UserMapper.insertUserRoles", map);
	} //

	@Override
	public Users loadUserByUsername(String username) {

		log.info("loadUserByUsername");
		return sqlSession.selectOne("mapper.UserMapper.loadUserByUsername", username);
	}

	@Override
	public Role loadUserRole(String username) {
		
		log.info("loadUserRole");
		return sqlSession.selectOne("mapper.UserMapper.loadUserRole", username);
	}
	
	@Override
	public void updateUsers(Users users) {

		log.info("updateUsers");
		sqlSession.update("mapper.UserMapper.updateUser", users);		
	}

	@Override
	public void deleteUsers(String username) {

		log.info("deleteUsers");
		sqlSession.delete("mapper.UserMapper.deleteUser", username);
	}

} //