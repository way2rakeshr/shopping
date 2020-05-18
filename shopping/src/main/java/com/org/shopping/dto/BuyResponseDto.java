package com.org.shopping.dto;

public class BuyResponseDto {
	
	private int statusCode;
	private String message;
	private Long receiptId;
	
	public BuyResponseDto(Long receiptId, String message, int statusCode) {
		super();
		this.receiptId = receiptId;
		this.message = message;
		this.statusCode = statusCode;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	} 	

}
