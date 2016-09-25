package ru.serdar.spring.mvc.entity;

public class Application {
	
	private String name;
	private Long createDate;
	private String phoneNumber;
	private String address;
	private Boolean isCitizen;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsCitizen() {
		return isCitizen;
	}
	public void setIsCitizen(Boolean isCitizen) {
		this.isCitizen = isCitizen;
	}
	
	
}
