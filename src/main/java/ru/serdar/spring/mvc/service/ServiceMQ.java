package ru.serdar.spring.mvc.service;

import java.io.IOException;
import java.util.concurrent.Future;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.serdar.spring.mvc.dto.Message;
import ru.serdar.spring.mvc.entity.Application;

public interface ServiceMQ {
	 Future<Application> sendAndRecieve(Message message) throws InterruptedException, JsonProcessingException, JMSException, IOException;
}
