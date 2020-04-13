package com.sotosmen.socialnetwork.amqp.conversation;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.messaging.Conversation;


@Service
public class RabbitMQSenderConversation {
	@Autowired
	@Qualifier("rabbitTemplateConversation")
	RabbitTemplate rabbitTemplate;
	
	@Value("${conversation.rabbitmq.exchange}")
	private String exchange;
	@Value("${conversation.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${conversation.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${conversation.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendConversationToCreate(Conversation conversation) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,conversation);
	}
	public void sendConversationToDelete(Conversation conversation) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,conversation);
	}
}
