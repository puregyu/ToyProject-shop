package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	@Transactional(readOnly = false)
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> members = memberRepository.findByName(member.getName());
		if(!members.isEmpty()) {
			throw new IllegalStateException("회원명이 중복됩니다.");
		}
	}
	
	public Member findOne(Long id) {
		return memberRepository.findOne(id);
	}
	
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
}
