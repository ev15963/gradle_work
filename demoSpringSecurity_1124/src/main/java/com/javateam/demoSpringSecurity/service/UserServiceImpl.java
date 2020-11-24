/**
 * 
 */
package com.javateam.demoSpringSecurity.service;

/**
 * @author javateam
 *
 */
import java.util.HashSet;
import java.util.Set;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.demoSpringSecurity.domain.Role;
import com.javateam.demoSpringSecurity.domain.User;
import com.javateam.demoSpringSecurity.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
 
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    	
	@Autowired
	private UserRepository userRepository;
	  
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	  
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = RuntimeException.class)
	@Override  
	public void saveUser(User user,String[] roles) {
  
		log.debug("saveUser : {}", user);
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    Set<Role> rolesSet = new HashSet<Role>();
	    
	    for (String name : roles){
	      
	      Role role = new Role();
	      role.setUserName(name);
	      rolesSet.add(role);  
	    }
	    
	    user.setRoles(rolesSet);
	    userRepository.save(user);
    } //

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	} //
  
}