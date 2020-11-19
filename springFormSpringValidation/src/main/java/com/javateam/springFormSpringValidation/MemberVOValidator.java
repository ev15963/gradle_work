package com.javateam.springFormSpringValidation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.javateam.springFormSpringValidation.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberVOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		// return MemberVO.class.isAssignableFrom(clazz);
		return MemberVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		log.info("Spring Form validate method");

		MemberVO member = (MemberVO)target;
		
		if (member.getId() == null || member.getId().length()==0) {
			
			log.error("아이디 공백 !");
			errors.rejectValue("id", "error"); // message.properties 파일 참조
		} //
		
		if (member.getPw() == null || member.getPw().length()==0) {
			
			log.error("패쓰워드 공백 !");
		    errors.rejectValue("pw", "error"); // message.properties 파일 참조
		} //
		
	} //

} //