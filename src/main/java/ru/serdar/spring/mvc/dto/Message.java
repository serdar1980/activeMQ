package ru.serdar.spring.mvc.dto;

import ru.serdar.spring.mvc.entity.Application;

public class Message {
	private Application application;
	private String method;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Message(Application application, String method){
		this.application= application;
		this.method=method;
	}
}	
