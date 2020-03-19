package com.sotosmen.socialnetwork.amqp.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.user.User;

@Service
public class RabbitMQSenderUser {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value("${user.rabbitmq.exchange}")
	private String exchange;
	@Value("${user.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${user.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${user.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendToCreateUser(User user) {
		rabbitTemplate.convertAndSend(exchange, routingkeyC, user);
		System.out.println("User sent to post queue.");
	}
	public void sendToUpdateUser(User user) {
		rabbitTemplate.convertAndSend(exchange,routingkeyU,user);
		System.out.println("User sent to put queue.");
	}
	public void sendToDeleteUser(String username) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,username);
		System.out.println("User sent to delete queue.");
	}
}
