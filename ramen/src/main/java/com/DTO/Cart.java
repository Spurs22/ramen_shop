package com.DTO;

public class Cart {
	private long productId;
	private long memberId;
	private int quantity;
	private String createdDate;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long product_id) {
		this.productId = product_id;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
