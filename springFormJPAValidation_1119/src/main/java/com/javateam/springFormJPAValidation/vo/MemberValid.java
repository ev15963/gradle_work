package com.javateam.springFormJPAValidation.vo;

import java.util.Date; 

import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class MemberValid {
	
	@Id
	@Size(min=8, 
		  max=20, 
		  message="아이디는 8~20자입니다")
	private String id;
	
	@NotNull 
	@Pattern(regexp="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,20}", 
			 message="패쓰워드는 특수문자, 숫자, 영문자대소문자를 혼합하여 8~20자로 입력합니다.")
	private String pw;
	
	@NotNull // (message="필수 사항은 반드시 입력하십시오")
	@Size(min=2, 
		  max=50, 
		  message="회원명은 2자 이상입니다")
	private String name;
	
	@NotEmpty  // 주의사항) @Email은 반드시 @NotEmpty와 같이 사용하십시오. 아니면 메시징이 안됩니다.
	@Email(message="잘못된 이메일 형식입니다.")
	private String email;
	
	@Past(message="생일은 금일 기준 이전 일이 들어가야 합니다")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date birthday;
	
}