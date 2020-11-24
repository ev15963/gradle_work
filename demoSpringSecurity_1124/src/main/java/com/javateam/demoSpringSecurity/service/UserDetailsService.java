/**
 * 
 */
package com.javateam.demoSpringSecurity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author javateam
 *
 */
public interface UserDetailsService {
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	

}
