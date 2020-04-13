package com.sotosmen.socialnetwork.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.user.RabbitMQReceiverSigning;
import com.sotosmen.socialnetwork.amqp.user.RabbitMQSenderSigning;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.User;

@Service
public class SigningService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RabbitMQSenderSigning rabbitMQSender;
	@Autowired
	RabbitMQReceiverSigning rabbitMQReceiver;
	
	public User logIn(String username, String password) {
		if(userRepository.count()!=0) {
			Optional<User>user =userRepository.findById(username);
			if(user.isPresent()) {
				if(user.get().getPassword().equals(password)) {
					return user.get();
				}else {
					throw new ResourceException(HttpStatus.FORBIDDEN,Strings.wrongPassword);
				}
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	
	public User signUp(User user) {
		rabbitMQSender.sendeUserToPostForSigning(user);
		return rabbitMQReceiver.signUp();
	}
	
}
