package com.org.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(name = "product_name", nullable = false, unique = true)
	private String productName;

	@Column(name = "product_code", nullable = false, unique = true)
	private String productCode;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private double price;

	private String priority;

	private int quantity;

	private double rating;

	public Product() {

	}

	public Product(Long id, String productName,
			@Length(min = 3, message = "*Product Code must have at least 3 characters") String productCode,
			String description,
			@DecimalMin(value = "0.00", message = "*Price has to be non negative number") double price,
			@NotNull String priority, int quantity, double rating) {
		super();
		this.id = id;
		this.productName = productName;
		this.productCode = productCode;
		this.description = description;
		this.price = price;
		this.priority = priority;
		this.quantity = quantity;
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
