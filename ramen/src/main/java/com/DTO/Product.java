package com.DTO;

public class Product {
	private Long id;
	private int category;
	private String name;
	private int price;
	private int remainQuantity;
	private String picture;

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", category=" + category +
				", name='" + name + '\'' +
				", price=" + price +
				", remainQuantity=" + remainQuantity +
				", picture='" + picture + '\'' +
				'}';
	}

	public Product() {
	}

	public Product(Long id, int category, String name, int price, int remainQuantity, String picture) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.price = price;
		this.remainQuantity = remainQuantity;
		this.picture = picture;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(int remainQuantity) {
		this.remainQuantity = remainQuantity;
	}
}
