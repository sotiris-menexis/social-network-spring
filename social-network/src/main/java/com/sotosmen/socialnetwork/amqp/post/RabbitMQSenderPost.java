package com.sotosmen.socialnetwork.amqp.post;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.post.Post;

@Service
public class RabbitMQSenderPost {
	@Autowired
	@Qualifier("rabbitTemplatePost")
	RabbitTemplate rabbitTemplate;
	
	@Value("${post.rabbitmq.exchange}")
	private String exchange;
	@Value("${post.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${post.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${post.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendToCreatePost(Post post) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,post);
	}
	public void sendToUpdatePost(Post post) {
		rabbitTemplate.convertAndSend(exchange,routingkeyU,post);
	}
	public void sendToDeletePost(String string) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,string);
	}
}
