package com.javateam.SpringBootMember.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.SpringBootMember.domain.MemberDTO;
import com.javateam.SpringBootMember.domain.MemberVO;
import com.javateam.SpringBootMember.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("member_join.do")
	public String memberJoinForm() {
	
		log.info("회원가입폼");
		return "/member/member_join";
	}
	
	@RequestMapping("join_proc.do")
	public String joinProc(@ModelAttribute("memberDTO") MemberDTO memberDTO, Model model) {
		
		log.info("회원가입 처리");
		String path = "/member/join_proc";
		
		log.info("회원정보 : " + memberDTO);
		
		MemberVO memberVO = new MemberVO(memberDTO);
		log.info("저장 회원 정보 : " + memberVO);
		
		try {
			
			memberService.insertMember(memberVO);
			
		} catch (Exception e) {
			log.error("회원 가입 db 에러 발생 : " + e.getMessage());
			model.addAttribute("db_error", e.getMessage());
			path = "/error/db-err";
		} //
		
		return path;
	}
	
	// @Secured({"ROLE_ADMIN"}) // 추가 : role 접근 제한 기능  
	@RequestMapping("member_view.do")
	public String memberViewForm(@RequestParam("memberId") String memberId,
					Model model) {
	
		log.debug("회원조회(보기)폼");

		MemberVO memberVO = memberService.getMember(memberId);
		log.info("회원 정보 : " + memberVO);
		
		log.info("회원 정보 : VO -> DTO 변환");
		MemberDTO member = new MemberDTO(memberVO);
		
		model.addAttribute("member", member);
		model.addAttribute("birth", memberVO.getMemberBirth());

		return "/member/member_view";
	}
	

	@RequestMapping("member_update.do")
	public String memberUpdateForm(@RequestParam("memberId") String memberId,
					Model model) {
	
		log.debug("회원수정폼");
		
		MemberVO memberVO = memberService.getMember(memberId);
		
		log.info("회원 정보 : VO -> DTO 변환");
		MemberDTO member = new MemberDTO(memberVO);		
		
		model.addAttribute("member", member);
		model.addAttribute("birth", memberVO.getMemberBirth());
		
		return "/member/member_update";
	}

	@RequestMapping(value="update_proc.do", method=RequestMethod.POST)
	public String updateProc(MemberDTO newMemberDTO,
							@RequestParam("memberPassword1") String newMemberPassword) {
		
		log.debug("회원수정 처리");
		
		log.info("#### 회원정보 : " + newMemberDTO);
			
		MemberDTO legacyMember = new MemberDTO(memberService.getMember(newMemberDTO.getMemberId()));
		
		log.debug("##########################################");
		log.debug("기존 회원정보 : " + legacyMember);
		log.debug("수정 회원정보 : " + newMemberDTO);
		log.debug("신규 패쓰워드 : " + newMemberPassword);
				
		MemberVO memberVO = new MemberVO(newMemberDTO);
		
		// 패쓰워드 갱신 버그 패치
		String newMemberPw = newMemberPassword.trim().equals("") ? memberVO.getMemberPassword()
						   : newMemberPassword;
		memberVO.setMemberPassword(newMemberPw);
		
		log.debug("저장 회원 정보 : " + memberVO);		
		
		memberService.updateMember(memberVO);
		
		return "redirect:/welcome.do";
	}
	
	@RequestMapping("member_delete.do")
	public String memberDeleteForm() {
	
		log.debug("회원삭제폼");
		return "/member/member_delete";
	}
	
	@RequestMapping(value="delete_proc.do", method=RequestMethod.POST)
	public String memberDeleteProc(@RequestParam("memberId") String memberId,
					Model model) {
	
		log.debug("회원삭제 처리 : " + memberService.isMember(memberId));
		
		if (memberService.isMember(memberId)==true) {
			memberService.deleteMember(memberId);
		} else {
			model.addAttribute("db_error", "회원 정보가 존재하지 않습니다.");
			return "/error/db-err";
		}
		
		return "redirect:/login.do";
	}
	
	
}