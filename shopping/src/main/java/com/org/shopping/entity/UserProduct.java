package com.org.shopping.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_product")
public class UserProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userProductId;

	private double price;
	private int quantity;
	private String purchaseddate;

	@ManyToOne
	@JoinColumn
	User user;

	@ManyToOne
	@JoinColumn
	private Product product;
	
	private double rating;	
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Long getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Long userProductId) {
		this.userProductId = userProductId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPurchaseddate() {
		return purchaseddate;
	}

	public void setPurchaseddate(String purchaseddate) {
		this.purchaseddate = purchaseddate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
