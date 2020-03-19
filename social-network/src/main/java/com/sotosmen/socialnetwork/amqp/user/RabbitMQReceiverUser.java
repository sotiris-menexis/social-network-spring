package com.sotosmen.socialnetwork.amqp.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
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
	
	public User receiveToCreateUser() {
		User user = (User)rabbitTemplate.receiveAndConvert(queuePost);
		if(userRepository.count()!=0) {
			if (userExists(user.getUsername())) {
				throw new ResourceException(HttpStatus.FORBIDDEN, Strings.userEx);
			}
			userRepository.save(user);
			return user;
		}else {
			userRepository.save(user);
			return user;
		}
	}
	public User receiveToUpdateUser() {
		User user = (User)rabbitTemplate.receiveAndConvert(queuePut);
		if(userRepository.count()!=0) {
			if (userExists(user.getUsername())) {
				userRepository.deleteById(user.getUsername());
				userRepository.save(user);
				return user;
			} else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	public String receiveToDeleteUser() {
		String username = (String)rabbitTemplate.receiveAndConvert(queueDelete);
		if(userRepository.count()!=0) {
			if (userExists(username)) {
				userRepository.deleteById(username);
				return Strings.deletionS;
			} else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	
	
	public boolean userExists(String username) {
		if (userRepository.findById(username).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
