package com.jpabook.jpashop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + "]";
	}
}
