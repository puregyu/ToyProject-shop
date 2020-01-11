package com.jpabook.jpashop;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	MemberService memberservice;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void 회원가입() {
		Member member = new Member();
		member.setName("devyu");
		
		Long memberId = memberservice.join(member);
		
		assertEquals(member, memberRepository.findOne(memberId));
	}
	
	//@Test(expected = IllegalStateException.class) // try-catch 대체 가능
	@Test
	public void 중복_회원_예외() {
		Member  member1 = new Member();
		member1.setName("min");
		Member  member2 = new Member();
		member2.setName("min");
		
		memberservice.join(member1);
		try {
			memberservice.join(member2); // 예측되는 Exception 라인
		} catch (IllegalStateException e) {
			return;
		}
		
		fail("중복_회원_예외 Exception test fail");
		
	}
}
