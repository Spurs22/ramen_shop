package com.DTO;

public class Notice {
	private Long id;
	private Long memberId;
	private String subject;
	private String content;
	private String ip_address;
	private int category;
	private int hit_count;
	private int notice;
	private String create_date;
	private Long gap;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getmemberId() {
		return memberId;
	}
	public void setmemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getHit_count() {
		return hit_count;
	}
	public void setHit_count(int hitCount) {
		this.hit_count = hitCount;
	}
	public int getNotice() {
		return notice;
	}
	public void setNotice(int notice) {
		this.notice = notice;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	public Long getGap() {
		return gap;
	}
	public void setGap(Long gap) {
		this.gap = gap;
	}
	


	
	
	
}
