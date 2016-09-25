package ru.serdar.spring.mvc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.serdar.spring.mvc.dto.Message;
import ru.serdar.spring.mvc.entity.Application;
import ru.serdar.spring.mvc.entity.User;
import ru.serdar.spring.mvc.service.GitHubLookupService;
import ru.serdar.spring.mvc.service.ServiceMQ;
import ru.serdar.spring.mvc.service.ServiceWaiting;

@RestController
public class HelloController {

	@Autowired
	@Qualifier("serviceWait")
	ServiceWaiting serviceWaiting;
	
	@Autowired
	@Qualifier("ServiceMQ")
	ServiceMQ serviceMQ;
	

	@Autowired
	@Qualifier("GitHub")
	GitHubLookupService service;

	@RequestMapping("/")
	public @ResponseBody Application index() throws InterruptedException, ExecutionException, JMSException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Application app = new Application();
		app.setAddress("Address");
		app.setCreateDate(2L);
		app.setIsCitizen(true);
		app.setName("Vasay");
		app.setPhoneNumber("+79032912492");
		System.out.println("HelloController:" + Thread.currentThread().getName() + "=" + new Date().getTime());

		//Future<User> page1 = service.findUser("PivotalSoftware");
		//Future<User> page2 = service.findUser("CloudFoundry");
		//Future<User> page3 = service.findUser("Spring-Projects");
		//Future<User> page4 = service.findUser("Spring-Projects");
		//Future<User> page5 = service.findUser("Spring-Projects");
		//Future<User> page6 = service.findUser("serdar1980");
			Message message = new Message(app, "2222");
			
			Future<Application> res = serviceMQ.sendAndRecieve(message);

		// Start the clock
		long start = System.currentTimeMillis();

		// Kick of multiple, asynchronous lookups

		// Wait until they are all done
		//while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
		//	Thread.sleep(10); // 10-millisecond pause between each check
		//}

		//System.out.println("HelloController:service");
		//System.out.println(page1.get());
		/*System.out.println(page2.get());
		System.out.println(page3.get());
		System.out.println(page4.get());
		System.out.println(page5.get());
		System.out.println(page6.get());
		 */
		
		System.out.println("HelloController:" + Thread.currentThread().getName() + "=" + new Date().getTime());
		return res.get();

	}

}
