/**
 * 
 */
package com.javateam.demoSpringSecurity.service;

/**
 * @author javateam
 *
 */
public interface SecurityService {
	
	String findLoggedInUsername();
	void autologin(String username, String password);
}
