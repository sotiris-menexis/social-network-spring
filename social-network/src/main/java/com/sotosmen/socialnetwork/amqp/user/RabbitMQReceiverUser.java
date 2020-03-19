package com.sotosmen.socialnetwork.amqp.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.user.User;

@Service
public class RabbitMQReceiverUser {	
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	UserRepository userRepository;
	@Value("${user.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${user.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${user.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public void receiveToCreateUser() {
		User user = (User)rabbitTemplate.receiveAndConvert(queuePost);
		userRepository.save(user);
	}
	public void receiveToUpdateUser() {
		User user = (User)rabbitTemplate.receiveAndConvert(queuePut);
		userRepository.deleteById(user.getUsername());
		userRepository.save(user);
	}
	public void receiveToDeleteUser() {
		String username = (String)rabbitTemplate.receiveAndConvert(queueDelete);
		userRepository.deleteById(username);
	}
	
}
