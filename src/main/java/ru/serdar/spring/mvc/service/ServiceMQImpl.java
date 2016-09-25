package ru.serdar.spring.mvc.service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.serdar.spring.mvc.ApplicationConfig;
import ru.serdar.spring.mvc.dto.Message;
import ru.serdar.spring.mvc.entity.Application;
import ru.serdar.spring.mvc.listener.ApplicationListener;
import ru.serdar.spring.mvc.producer.Producer;

@Service
@Qualifier("ServiceMQ")
public class ServiceMQImpl implements ServiceMQ {

	@Autowired
	@Qualifier("Producer")
	Producer producer;
	
	
	@Autowired
	@Qualifier("ApplicationListener")
	ApplicationListener applicationListenerFrom;
	
	@Autowired
	@Qualifier("ApplicationListener")
	ApplicationListener applicationListenerTo;
	
	
	@Override
	@Async
	public Future<Application> sendAndRecieve(Message message) throws InterruptedException, JMSException, IOException {
		System.out.println("sendAndRecieve:" + Thread.currentThread().getName() + new Date());
		
		String correlationId= producer.sendMessage(message.getApplication(), ApplicationConfig.ORDER_QUEUE, true, null);
		applicationListenerTo.receiveMessage(correlationId, null);
		
		System.out.println("sendAndRecieve:" + Thread.currentThread().getName() + new Date());

		return new AsyncResult<Application>(applicationListenerFrom.receiveMessage(correlationId, true));
	}

}
