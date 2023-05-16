package com.DTO;

public class OrderItem {
	private Long orderItemId;
	
	private Long productId;
	private Long orderBundleId;
	private Long statusId;
	private int quantity;
	private Long price;
	private Long finalPrice;
	
	
	private String productName;
	private String statusName;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOrderBundleId() {
		return orderBundleId;
	}

	public void setOrderBundleId(Long orderBundleId) {
		this.orderBundleId = orderBundleId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Long finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}