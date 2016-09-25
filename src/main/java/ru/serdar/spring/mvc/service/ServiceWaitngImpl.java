package ru.serdar.spring.mvc.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.serdar.spring.mvc.dto.Message;
import ru.serdar.spring.mvc.entity.Application;

@Service
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
@Qualifier("ServiceWaiting")
public class ServiceWaitngImpl implements ServiceWaiting {
	
	@Autowired
	@Qualifier("ServiceMQ")
	private ServiceMQ sender;
	
	public Application waitService(String method, Application application) throws InterruptedException, ExecutionException, JMSException, IOException {
		 System.out.println("waitService:"+Thread.currentThread().getName());
		// Artificial delay of 1s for demonstration purposes
        application.setName("Robbert");
        
        Message message = new Message(application, "test");
        Future<Application> res = sender.sendAndRecieve(message); 
		
		return res.get();
	}

}
