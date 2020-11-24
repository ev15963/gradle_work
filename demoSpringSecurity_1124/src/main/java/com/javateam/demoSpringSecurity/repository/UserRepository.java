/**
 * 
 */
package com.javateam.demoSpringSecurity.repository;

/**
 * @author javateam
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.javateam.demoSpringSecurity.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUsername(String username);
	
}