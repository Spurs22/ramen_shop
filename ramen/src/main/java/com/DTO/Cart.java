package com.DTO;

public class Cart {
	private Long productId;
	private Long memberId;
	private int quantity;
	private String createdDate;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long product_id) {
		this.productId = product_id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
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
