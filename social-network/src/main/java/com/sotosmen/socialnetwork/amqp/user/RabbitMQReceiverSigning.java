package com.sotosmen.socialnetwork.amqp.user;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.User;

@Service
public class RabbitMQReceiverSigning {
	@Autowired
	@Qualifier("rabbitTemplateUser")
	RabbitTemplate rabbitTemplate;
	@Autowired
	UserRepository userRepository;
	@Value("${user.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${user.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${user.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public User signUp() {
		User user = (User)rabbitTemplate.receiveAndConvert(queuePost);
		if(userRepository.count()!=0) {
			Optional<User> temp = userRepository.findById(user.getUsername());
			if(temp.isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN,Strings.userEx);
			}else {
				userRepository.save(user);
				return user;
			}
		}else {
			userRepository.save(user);
			return user;
		}
	}
	
}
