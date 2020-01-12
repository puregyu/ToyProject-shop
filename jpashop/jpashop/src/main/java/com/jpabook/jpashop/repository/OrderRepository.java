package com.jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Order;

@Repository
public class OrderRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}
	
	// 동적 QueryDSL 사용
	public List<Order> findAll(OrderSearch orderSearch){
		
		return null;
	}
}
