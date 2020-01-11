package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;

	@Transactional(readOnly = false)
	public void register(Item item) {
		itemRepository.save(item);
	}
	
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}
	
	public List<Item> findAll(){
		return itemRepository.findAll();
	}
}
