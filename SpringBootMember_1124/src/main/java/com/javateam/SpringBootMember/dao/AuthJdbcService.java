package com.javateam.SpringBootMember.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import com.javateam.SpringBootMember.domain.CustomUser;
import com.javateam.SpringBootMember.domain.Role;
import com.javateam.SpringBootMember.domain.Users;

import lombok.extern.slf4j.Slf4j;
/*
 * Spring JDBC를 사용 : javateacher
 */
@Repository
@Slf4j
public class AuthJdbcService {
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public boolean hasUsername(String username) {
    	
    	boolean flag = false;
    	String sql = "SELECT * FROM users WHERE username=?";
    	
    	log.info("hasUsername : " + username);
    	
    	try {
    		
    		 Users user = (Users)this.jdbcTemplate
    				 				 .queryForObject(sql, 
								   				     new Object[]{ username },
								   				     new BeanPropertyRowMapper<Users>(Users.class));
    		 
    		 flag = user != null ? true : false;
    		 
		} catch (Exception e) {
			flag=false;
		}
    	
    	return flag; // 
    } //
    
    
    public void insertUsers(Users users, String role) {
    	
    	log.info("insertUsers");
    	
    	String sql  = "INSERT INTO users V: guswqoALUES (?,?,1)";
    	String sql2 = "INSERT INTO user_roles VALUES "
    				+ "(user_roles_seq.nextval,?,?)";
    	
    	this.jdbcTemplate.update(sql, 
    							 new Object[] { users.getUsername(), 
											    users.getPassword() });
    	
    	this.jdbcTemplate.update(sql2, 
    							 new Object[] { users.getUsername(),
											    role });
    	
    } //
    
    public CustomUser loadUserByUsername(String userName) {
    	
    	log.info("loadUserByUsername");
    	
    	try {
	    	return (CustomUser)jdbcTemplate.queryForObject(
	    			 "SELECT * FROM users WHERE username=?", 
				     new Object[]{ userName },
				     new BeanPropertyRowMapper<CustomUser>(CustomUser.class));
	    } catch (EmptyResultDataAccessException e) {
	    	log.error("error1");
	    	return null;
	    }
    }

	public Role loadUserRole(String userName) {
		
		log.info("loadUserRole");

		try {
			return (Role)jdbcTemplate.queryForObject(
	   			 	"SELECT username, role FROM user_roles WHERE username=?", 
				     new Object[]{ userName },
				     new BeanPropertyRowMapper<Role>(Role.class));
		} catch (EmptyResultDataAccessException e) {
			log.error("error2");
	    	return null;
	    }
	}
    		
} //