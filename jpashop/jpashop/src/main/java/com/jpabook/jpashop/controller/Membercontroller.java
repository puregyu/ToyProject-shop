package com.jpabook.jpashop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Membercontroller {

	 private final MemberService memberService;
	 
	 @GetMapping("/members/new")
	 public String createForm(Model model) {
		 model.addAttribute("memberForm", new MemberForm());
		 return"members/createMemberForm"; 
	 }
	 
	 @PostMapping("/members/new")
	 public String createMember(@Valid MemberForm memberForm, BindingResult result) {
		 
		 if(result.hasErrors()) {
			 return "members/createMemberForm";
		 }
		 
		 Address address = new Address(memberForm.getCity(), memberForm.getName(), memberForm.getZipcode());
		 
		 Member member = new Member();
		 member.setName(memberForm.getName());
		 member.setAddress(address);
		 
		 memberService.join(member);
		 
		 return "redirect:/";
	 }
	 
	 @GetMapping("/members")
	 public String membersList(Model model) {
		 
		 List<Member> members = memberService.findMembers();
		 model.addAttribute("members", members);
		 
		 return "members/memberList";
	 }
}
