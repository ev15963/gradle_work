package com.javateam.demoSpringSecurity.service;

import com.javateam.demoSpringSecurity.domain.User;

/**
 * 
 * @author javateam
 *
 */
public interface UserService {
	
	void saveUser(User user,String[] roles);
	User findByUsername(String username);
}