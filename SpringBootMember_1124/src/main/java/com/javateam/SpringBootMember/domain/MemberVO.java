/**
 * 
 */
package com.javateam.SpringBootMember.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Data;
import lombok.extern.java.Log;

/**
 * 회원정보 VO(값 객체)
 * 
 *
 * @author javateam
 * @version 1.0
 */

@Data
@Log
public class MemberVO {
	
	/** 아이디 */
	private String memberId;
	/** 패쓰워드 */
	private String memberPassword;
	/** 별명 */
	private String memberNickname;
	/** 이름 */
	private String memberName;
	/** 성별 */
	private String memberGender;
	/** 이메일 */
	private String memberEmail;
	/** 연락처 */
	private String memberPhone;
	/** 생년월일 */
	private Date memberBirth;
	/** 우편번호 */
	private String memberZip;
	/** 주소 */
	private String memberAddress;
	/** 가입일 */
	private Date memberJoinDate;
	
	public MemberVO() {}
	
	/**
	 * DTO -> VO 치환
	 * @param memberDTO
	 * @throws ParseException 
	 */
	public MemberVO(MemberDTO memberDTO) {
		this.memberId = memberDTO.getMemberId();
		this.memberPassword = memberDTO.getMemberPassword();
		this.memberNickname = memberDTO.getMemberNickname();
		this.memberName = memberDTO.getMemberName();
		this.memberGender = memberDTO.getMemberGender();
		this.memberEmail = memberDTO.getMemberEmail();
		this.memberPhone = memberDTO.getMemberPhone();
		
		// 버그 패치
		this.memberBirth = new Date(java.sql.Date.valueOf(this.toDate(memberDTO.getMemberBirth())).getTime());
		this.memberZip = memberDTO.getMemberZip();
		this.memberAddress 
			= memberDTO.getMemberAddressBasic() 
			+ "*"
		    + memberDTO.getMemberAddressDetail();
		this.memberJoinDate = memberDTO.getMemberJoinDate();
	}
        
    // KST(date) 날짜 -> 변환
    private String toDate(String date) {
    	
    	log.info("변환전 날짜 : "+date);
    	
    	String result = "";
    	
    	// 2020-10-10 인지 점검
    	if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
    		
    		result = date;
    		
    	} else { //  Mon May 22 00:00:00 KST 2000 형식이면...
    	    
	    	SimpleDateFormat original = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", new Locale("ko-kr"));
	    	SimpleDateFormat conversion = new SimpleDateFormat("yyyy-MM-dd", new Locale("ko-kr"));
	        
	        try {
				result = conversion.format(original.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        
    	}
    	
        log.info("변환후 날짜 : "+result);
        
        return result;
    }
	
}