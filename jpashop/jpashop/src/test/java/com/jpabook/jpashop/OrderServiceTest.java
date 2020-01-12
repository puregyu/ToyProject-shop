package com.jpabook.jpashop;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.service.OrderService;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void 상품주문() {
		Member member = new Member();
		member.setName("민규");
		member.setAddress(new Address("seoul", "omok-streets", "491-1"));
		
		em.persist(member);
		
		Book book = new Book();
		book.setAuthor("홍성대");
		book.setName("표준 orm 프레임워크의 기초와 이해");
		book.setPrice(34000);
		book.setStockQuantity(30);
		
		em.persist(book);
		
		Long orderId = orderService.order(member.getId(), book.getId(), 2);
		
		Order getOrder = orderRepository.findOne(orderId);
		
		Assert.assertEquals("상품주문 상태는 ORDER ", OrderStatus.ORDER, getOrder.getStatus());
		Assert.assertEquals("주문한 상품의 종류의 수가 정확해야 한다. ", 1, getOrder.getOrderItems().size());
		Assert.assertEquals("주문가격은 가격 x 수량이다. ", 34000 * 2, getOrder.getTotalPrice());
		Assert.assertEquals("주문수량만큼 재고가 줄어야 한다. ", 28, book.getStockQuantity());
	}
	
	@Test
	public void 주문취소() {

		Member member = new Member();
		member.setName("민규");
		member.setAddress(new Address("seoul", "omok-streets", "491-1"));
		
		em.persist(member);
		
		Book book = new Book();
		book.setAuthor("홍성대");
		book.setName("표준 orm 프레임워크의 기초와 이해");
		book.setPrice(34000);
		book.setStockQuantity(30);
		
		em.persist(book);
		
		int orderCount = 20;
		
		// 주문
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		
		Assert.assertEquals("주문을 했으니 수량이 감소해야한다.",  10, book.getStockQuantity());
		
		// 주문취소
		orderService.cancelOrder(orderId);
		
		// 조회
		Order getOrder = orderRepository.findOne(orderId);
		
		Assert.assertEquals("주문 취소상태는 Cancel 이다.",  OrderStatus.CANSEL, getOrder.getStatus());
		Assert.assertEquals("주문 취소하면 수량이 다시 증가해야 한다", 30, book.getStockQuantity());
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() {
		Member member = new Member();
		member.setName("민규");
		member.setAddress(new Address("seoul", "omok-streets", "491-1"));
		
		em.persist(member);
		
		Book book = new Book();
		book.setAuthor("홍성대");
		book.setName("표준 orm 프레임워크의 기초와 이해");
		book.setPrice(34000);
		book.setStockQuantity(30);
		
		em.persist(book);
		
		int orderCount = 31;
		
		// Exception 발생 예측 구간
		orderService.order(member.getId(), book.getId(), orderCount);
		
		fail("Exception 발생 예측을 실패하셨습니다.");
	}
}
