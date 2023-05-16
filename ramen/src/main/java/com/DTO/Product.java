package com.DTO;

public class Product {
	private Long productId;
	private ProductCategory category;
	private String name;
	private int remainQuantity;
	private String picture;

	@Override
	public String toString() {
		return "Product{" +
				"productId=" + productId +
				", category=" + category +
				", name='" + name + '\'' +
				", remainQuantity=" + remainQuantity +
				", picture='" + picture + '\'' +
				'}';
	}

	public Product() {
	}

	public Product(Long productId) {
		this.productId = productId;
	}

	public Product(ProductCategory category, String name, int remainQuantity, String picture) {
		this.category = category;
		this.name = name;
		this.remainQuantity = remainQuantity;
		this.picture = picture;
	}

	public Product(Long productId, ProductCategory category, String name, int remainQuantity, String picture) {
		this.productId = productId;
		this.category = category;
		this.name = name;
		this.remainQuantity = remainQuantity;
		this.picture = picture;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(int remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
