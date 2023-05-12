package com.DTO;

public class Order {
	private long orderBundleId;
	private long orderItemId;
	private long orderStatusId;
	
	private String statusName;
	private long memberId;
	private long deliveryId;
	private String createdDate;
	private long productId;
	private long orderId;
	private long statusId;
	private int quantity;
	private long price;
	private long finalPrice;
	
	public long getOrderBundleId() {
		return orderBundleId;
	}
	public void setOrderBundleId(long orderBundleId) {
		this.orderBundleId = orderBundleId;
	}
	public long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public long getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(long finalPrice) {
		this.finalPrice = finalPrice;
	}

	
}