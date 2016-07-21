package com.shopping.foundation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.shopping.core.domain.IdEntity;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "shopping_test")
public class Test extends IdEntity{
	//名称
	private String name;
//	//商店地址
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Area area;
	
//	//工厂外键
//	@OneToOne(mappedBy = "test", fetch = FetchType.LAZY)
//	private Storefactory storefactory;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
