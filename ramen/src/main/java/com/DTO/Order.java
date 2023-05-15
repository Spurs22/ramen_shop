package com.DTO;

public class Order {
	private Long orderBundleId;
	private Long orderItemId;
	private Long orderStatusId;
	
	private String statusName;
	
	private String receiveName;
	private String tel;
	private String postNum;
	private String address1;
	private String address2;
	
	private Long productId;
	private Long orderId;
	private Long statusId;
	private int quantity;
	private Long price;
	private Long finalPrice;
	
	private Long memberId;
	private Long deliveryId;
	private String createdDate;
	

	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPostNum() {
		return postNum;
	}
	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public Long getOrderBundleId() {
		return orderBundleId;
	}
	public void setOrderBundleId(Long orderBundleId) {
		this.orderBundleId = orderBundleId;
	}
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Long getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(Long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	
}