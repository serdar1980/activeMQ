package ru.serdar.spring.mvc.listener;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.jms.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.serdar.spring.mvc.ApplicationConfig;
import ru.serdar.spring.mvc.entity.Application;
import ru.serdar.spring.mvc.producer.Producer;

@Component
@Qualifier("ApplicationListener")
public class ApplicationListener {
	@Autowired
	@Qualifier("JmsTemplate")
	JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("Producer")
	Producer producer;
	
	public Application receiveMessage(String correlationId, Boolean filterCorrelationId) throws JMSException, JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Application app =null;

		if(filterCorrelationId != null && filterCorrelationId){
			String filter = "JMSCorrelationID = '" + correlationId  + "'";
			ObjectMessage message =(ObjectMessage) jmsTemplate.receiveSelected(ApplicationConfig.ORDER_RESPONCE_QUEUE, filter);
			try {
				Integer sleep = new Random().nextInt(7000);
				Thread.currentThread().sleep(sleep);
				app = mapper.readValue(message.getObject().toString(), Application.class);
				app.setPhoneNumber(sleep.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ObjectMessage message =(ObjectMessage) jmsTemplate.receive(ApplicationConfig.ORDER_QUEUE);
			app = mapper.readValue(message.getObject().toString(), Application.class);
			app.setName("Peter");
			app.setCreateDate(new Date().getTime());
			producer.sendMessage(app, ApplicationConfig.ORDER_RESPONCE_QUEUE, false, correlationId);
		}
		
		return app;
	}
	
	
}
