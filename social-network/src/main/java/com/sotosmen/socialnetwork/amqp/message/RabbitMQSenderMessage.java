package com.sotosmen.socialnetwork.amqp.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.messaging.Message;

@Service
public class RabbitMQSenderMessage {
	@Autowired
	@Qualifier("rabbitTemplateMessage")
	RabbitTemplate rabbitTemplate;
	
	@Value("${message.rabbitmq.exchange}")
	private String exchange;
	@Value("${message.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${message.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${message.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendMessageToCreate(Message message) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,message);
	}
	public void sendMessageToDelete(Message message) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,message);
	}
}
