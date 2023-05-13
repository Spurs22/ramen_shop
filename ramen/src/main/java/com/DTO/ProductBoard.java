package com.DTO;

import java.util.List;

public class ProductBoard {
	private Long productId;
	private Long writerId;
	private List<String> imgList;
	private String content;
	private String createdDate;
	private Integer hitCount;
	private Double rating;

	public ProductBoard() {
	}

	@Override
	public String toString() {
		return "ProductBoard{" +
				"productId=" + productId +
				", writerId=" + writerId +
				", imgList=" + imgList +
				", content='" + content + '\'' +
				", createdDate='" + createdDate + '\'' +
				", hitCount=" + hitCount +
				", rating=" + rating +
				'}';
	}

	public ProductBoard(Long productId, Long writerId, List<String> imgList, String content, String createdDate, Integer hitCount) {
		this.productId = productId;
		this.writerId = writerId;
		this.imgList = imgList;
		this.content = content;
		this.createdDate = createdDate;
		this.hitCount = hitCount;
	}

	public ProductBoard(Long productId, Long writerId, List<String> imgList, String content, String createdDate, Integer hitCount, Double rating) {
		this.productId = productId;
		this.writerId = writerId;
		this.imgList = imgList;
		this.content = content;
		this.createdDate = createdDate;
		this.hitCount = hitCount;
		this.rating = rating;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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
}
