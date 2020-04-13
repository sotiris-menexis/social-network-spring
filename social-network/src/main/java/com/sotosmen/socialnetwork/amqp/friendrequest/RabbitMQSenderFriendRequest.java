package com.sotosmen.socialnetwork.amqp.friendrequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.user.FriendRequest;

@Service
public class RabbitMQSenderFriendRequest {
	@Autowired
	@Qualifier("rabbitTemplateFriendRequest")
	RabbitTemplate rabbitTemplate;
	
	@Value("${friendrequest.rabbitmq.exchange}")
	private String exchange;
	@Value("${friendrequest.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${friendrequest.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${friendrequest.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendToCreateFriendRequest(FriendRequest request) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,request);
	}
	public void sendToUpdateFriendRequest(FriendRequest request) {
		rabbitTemplate.convertAndSend(exchange,routingkeyU,request);
	}
	public void sendToDeleteFriendRequest(String friendRequestId) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,friendRequestId);
	}
}
