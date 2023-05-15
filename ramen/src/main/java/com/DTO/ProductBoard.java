package com.DTO;

import java.util.List;

public class ProductBoard {
	private Long productId;
	private Long writerId;
	private String productName;
	private List<String> imgList;
	private String content;
	private String createdDate;
	private Integer hitCount;
	private Float rating;
	private Integer price;

	public ProductBoard() {
	}

	public ProductBoard(Long productId, Long writerId, String productName, List<String> imgList, String content, String createdDate, Integer hitCount, Float rating, Integer price) {
		this.productId = productId;
		this.writerId = writerId;
		this.productName = productName;
		this.imgList = imgList;
		this.content = content;
		this.createdDate = createdDate;
		this.hitCount = hitCount;
		this.rating = rating;
		this.price = price;
	}

	public ProductBoard(Long productId, Long writerId, List<String> imgList, String content) {
		this.productId = productId;
		this.writerId = writerId;
		this.imgList = imgList;
		this.content = content;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
