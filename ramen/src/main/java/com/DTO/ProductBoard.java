package com.DTO;

import java.util.List;

public class ProductBoard {

	private Product product;
	private Long writerId;
	private List<String> imgList;
	private String content;
	private String createdDate;
	private Integer hitCount;
	private Float rating;
	private Integer price;

	public ProductBoard() {
	}

	public ProductBoard(Product product, Long writerId, List<String> imgList, String content, String createdDate, Integer hitCount, Float rating, Integer price) {
		this.product = product;
		this.writerId = writerId;
		this.imgList = imgList;
		this.content = content;
		this.createdDate = createdDate;
		this.hitCount = hitCount;
		this.rating = rating;
		this.price = price;
	}

	public ProductBoard(Product product, Long writerId, List<String> imgList, String content, Integer price) {
		this.product = product;
		this.writerId = writerId;
		this.imgList = imgList;
		this.content = content;
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
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
