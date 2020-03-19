package com.sotosmen.socialnetwork.amqp.thread;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.thread.Thread;

@Service
public class RabbitMQSenderThread {
	@Autowired
	@Qualifier("rabbitTemplateThread")
	RabbitTemplate rabbitTemplate;
	
	@Value("${thread.rabbitmq.exchange}")
	private String exchange;
	@Value("${thread.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${thread.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${thread.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendToCreateThread(Thread thread) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,thread);
	}
	public void sendToUpdateThread(Thread thread) {
		rabbitTemplate.convertAndSend(exchange,routingkeyU,thread);
	}
	public void sendToDeleteThread(String username) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,username);
	}
	
}
