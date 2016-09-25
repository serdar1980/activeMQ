package ru.serdar.spring.mvc.producer;

import java.util.UUID;

import javax.jms.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.serdar.spring.mvc.ApplicationConfig;
import ru.serdar.spring.mvc.entity.Application;

@Component
@Qualifier("Producer")
public class Producer {

	@Autowired
	@Qualifier("JmsTemplate")
	JmsTemplate jmsTemplate;
	
	private String correlationId;
	
	public String sendMessage(final Application app, String queue, final Boolean flagReplay, final String id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		final String jsonApp = mapper.writeValueAsString(app);
		System.out.println("Producer sends " + jsonApp);
		jmsTemplate.send(queue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				if(id != null){
					final String correlationId =id;
				}else{
					final String correlationId = getCorrelationId();
				}
				final ObjectMessage objectMessage = session.createObjectMessage(jsonApp);
				objectMessage.setJMSCorrelationID(correlationId);
				if(flagReplay){
					objectMessage.setJMSReplyTo(session.createQueue(ApplicationConfig.ORDER_RESPONCE_QUEUE));
				}
				return objectMessage;
			}
		});
		return correlationId;
	}
	
	private String getCorrelationId(){
		correlationId = UUID.randomUUID().toString();
		return correlationId;
	}
}
