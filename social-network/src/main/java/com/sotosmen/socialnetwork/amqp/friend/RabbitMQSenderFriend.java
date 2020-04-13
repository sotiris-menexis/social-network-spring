package com.sotosmen.socialnetwork.amqp.friend;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.user.Friend;

@Service
public class RabbitMQSenderFriend {
	@Autowired
	@Qualifier("rabbitTemplateFriend")
	RabbitTemplate rabbitTemplate;
	
	@Value("${friend.rabbitmq.exchange}")
	private String exchange;
	@Value("${friend.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${friend.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${friend.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendFriendToCreate(Friend friend) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,friend);
	}
	public void sendFriendToDelete(Friend friend) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,friend);
	}

}
