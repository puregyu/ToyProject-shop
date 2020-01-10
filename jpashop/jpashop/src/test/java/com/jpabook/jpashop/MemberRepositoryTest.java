package com.jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test 
	@Transactional
	// @Transactional이 test case에 사용된다면 test후 rollback이 진행됨.
	@Rollback(false) // rollback를 false로 둔다면 적용 가능함
	public void testMember() {
		// TODO Auto-generated method stub
		Member member = new Member();
		member.setName("devyu");
		
		Long savedId = memberRepository.save(member);
		Member findMember = memberRepository.find(savedId);
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
	}
}
	
