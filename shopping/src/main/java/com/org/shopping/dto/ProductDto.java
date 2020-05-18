package com.org.shopping.dto;

import javax.validation.constraints.NotBlank;


public class ProductDto {
	private Long id;
	@NotBlank(message = "not null product code")
	private String productCode;
	@NotBlank(message = "not null product name")
	private String productName;
	private String description;
	private double price;
	private int quantity;
	@NotBlank(message = "not null product name")
	private String priority;
	
	public ProductDto() {
		
	}

	public ProductDto(String productCode, String productName, String description, double price, int quantity,
			String priority) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.priority = priority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
