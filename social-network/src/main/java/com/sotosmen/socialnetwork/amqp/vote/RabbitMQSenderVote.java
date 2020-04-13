package com.sotosmen.socialnetwork.amqp.vote;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.votes.Vote;

@Service
public class RabbitMQSenderVote {
	@Autowired
	@Qualifier("rabbitTemplateVote")
	RabbitTemplate rabbitTemplate;
	
	@Value("${vote.rabbitmq.exchange}")
	private String exchange;
	@Value("${vote.rabbitmq.routingkey.post}")
	private String routingkeyC;
	@Value("${vote.rabbitmq.routingkey.put}")
	private String routingkeyU;
	@Value("${vote.rabbitmq.routingkey.delete}")
	private String routingkeyD;
	
	public void sendToCreateVote(Vote vote) {
		rabbitTemplate.convertAndSend(exchange,routingkeyC,vote);
	}
	
	public void sendToDeleteVote(String threadName) {
		rabbitTemplate.convertAndSend(exchange,routingkeyD,threadName);
	}
	
}
