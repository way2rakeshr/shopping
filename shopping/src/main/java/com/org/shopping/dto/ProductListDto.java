package com.org.shopping.dto;

public class ProductListDto {

	private String productCode;
	private String productName;
	private String description;
	private double price;
	private double rating;
	private int quantity;
	private Long id;

	public ProductListDto(String productCode, String productName, String description, double price, double rating) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.rating = rating;
	}

	public ProductListDto() {
		
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
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
	
	

}
