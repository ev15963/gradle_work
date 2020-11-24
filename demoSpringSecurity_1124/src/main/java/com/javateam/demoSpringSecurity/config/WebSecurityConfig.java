/**
 * 
 */
package com.javateam.demoSpringSecurity.config;

import javax.sql.DataSource;

/**
 * @author javateam
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;
 
@Log // 추가(lombok)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true) // 추가
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  // since spring boot 2.0 over	
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
	  return super.authenticationManagerBean();
  }	
 
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Autowired
  private DataSource dataSource; // 추가
  
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
/*
  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
  ...
  .userDetailsService(userDetailsService());
  ...
  }
 */
  
  // since sapring boot 2.0 over
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception 
  {
	  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	  
	  // 주의사항) 아래의 코드 부류들을 삽입/대체시 지속적 Stackoverflow error !!!
      // auth.parentAuthenticationManager(authenticationManagerBean());
      // auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
      // auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder()); // 추가??
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception{
	  
	log.info("configure !!!");
	
    // h2 console 사용을 위한 설정 
    // http.csrf().ignoringAntMatchers("/h2console/**"); // h2
    http.headers().frameOptions().sameOrigin();
    
    http
        .authorizeRequests()
        // 해당 url을 허용한다. 
        //  .antMatchers("/resources/**","/loginError","/registration","/h2console/**").permitAll() // h2
        .antMatchers("/resources/**","/loginError","/registration", "/memberJoin").permitAll()
        // admin 폴더에 경우 admin 권한이 있는 사용자에게만 허용 
        .antMatchers("/admin/**").hasAuthority("ADMIN")
      // user 폴더에 경우 user 권한이 있는 사용자에게만 허용
        .antMatchers("/user/**").hasAuthority("USER")
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .loginPage("/login")
        .successHandler(new CustomAuthenticationSuccess()) // 로그인 성공 핸들러 
        .failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러 
        .permitAll()
        .and()
      .logout()
        .permitAll()
        .and()
       .exceptionHandling().accessDeniedPage("/403"); // 권한이 없을경우 해당 url로 이동
    
    // 추가된 부분 : remember-me 관련
    // remember-me cookie
    // => 사용자이름 + cookie expired time(만료 시간) + 패쓰워드 => Base64(Md5방식) 암호화
    /*
     * base64(username + ":" + expirationTime + ":" +
              md5Hex(username + ":" + expirationTime + ":" password + ":" + key))
     */
    http.rememberMe()
        .key("javateam")
        .userDetailsService(userDetailsService)
        .tokenRepository(getJDBCRepository())
        .tokenValiditySeconds(60*60*24); // 24시간(1일)
  }
  
  // 추가된 remember-me 관련 메서드
  private PersistentTokenRepository getJDBCRepository() {
	  
	  JdbcTokenRepositoryImpl repo 
	  	= new JdbcTokenRepositoryImpl();
	  
	  repo.setDataSource(dataSource);
	  
	  return repo;
  } //
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }  
  
}