package com.DTO;

public class Cart {
	private String productName;
	private Long productId;
	private Long memberId;
	private int quantity;	
	private int	remainQuantity;		// 남은 개수
	private String createdDate;
	private String picture;
	private Long price;
	

	public int getRemainQuantity() {
		return remainQuantity;
	}
	public void setRemainQuantity(int remainQuantity) {
		this.remainQuantity = remainQuantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
}
