package com.DTO;

public class OrderStatistics {
	private Long sumquantity;
	private Long sumfinal_price;
	private Long productid;
	private String productname;
	private String picture;
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Long getSumquantity() {
		return sumquantity;
	}
	public void setSumquantity(Long sumquantity) {
		this.sumquantity = sumquantity;
	}
	public Long getSumfinal_price() {
		return sumfinal_price;
	}
	public void setSumfinal_price(Long sumfinal_price) {
		this.sumfinal_price = sumfinal_price;
	}
	public Long getProductid() {
		return productid;
	}
	public void setProductid(Long productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	
}
