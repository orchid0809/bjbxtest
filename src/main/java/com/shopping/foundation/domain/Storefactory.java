package com.shopping.foundation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.shopping.core.domain.IdEntity;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "shopping_store_factory")
public class Storefactory extends IdEntity {
	//工厂编码
	private String code;
	//工厂名称
	private String name;
	//所在街道
	private String address;
	//邮政编码
	private String postcode;
	//联系人
	private String link_name;
	//联系电话
	private String phone;
	//银行名称
	private String bank_name;
	//银行地址
	private String bank_address;
	//支行名称
	private String bank_khname;
	//账户名
	private String bank_user;
	//账号
	private String bank_username;
	
	//用户
	@OneToOne(mappedBy = "factory", fetch = FetchType.LAZY)
	private User user;
	
	//商店地址
	@ManyToOne(fetch = FetchType.LAZY)
	private Area area;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getLink_name() {
		return link_name;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getBank_khname() {
		return bank_khname;
	}

	public void setBank_khname(String bank_khname) {
		this.bank_khname = bank_khname;
	}

	public String getBank_user() {
		return bank_user;
	}

	public void setBank_user(String bank_user) {
		this.bank_user = bank_user;
	}

	public String getBank_username() {
		return bank_username;
	}

	public void setBank_username(String bank_username) {
		this.bank_username = bank_username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
}
