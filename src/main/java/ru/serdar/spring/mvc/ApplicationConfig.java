package ru.serdar.spring.mvc;

import java.util.Arrays;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import ru.serdar.spring.mvc.service.ServiceWaiting;
import ru.serdar.spring.mvc.service.ServiceWaitngImpl;

@Configuration
public class ApplicationConfig {

	@Bean(name = "serviceWait")
	public ServiceWaiting getServiceWaiting() {
		return new ServiceWaitngImpl();
	}

	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

	public static final String ORDER_QUEUE = "order-queue";
	public static final String ORDER_RESPONCE_QUEUE = "order-responce-queue";

	@Bean(name="activeMQ")
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		
		return connectionFactory;
	}

	@Bean(name="JmsTemplate")
	public JmsTemplate jmsTemplate(@Qualifier("activeMQ")ActiveMQConnectionFactory activeMQ ) {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(activeMQ);
		template.setDefaultDestinationName(ORDER_QUEUE);
		template.setReceiveTimeout(10000L);
		return template;
	}

}
