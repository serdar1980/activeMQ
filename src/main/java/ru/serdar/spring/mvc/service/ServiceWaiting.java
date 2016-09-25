package ru.serdar.spring.mvc.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.serdar.spring.mvc.entity.Application;

public interface ServiceWaiting {
		Application waitService(String method, Application application) throws InterruptedException, ExecutionException, JsonProcessingException, JMSException, IOException;
}
