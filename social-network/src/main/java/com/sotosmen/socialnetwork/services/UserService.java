package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.user.RabbitMQReceiverUser;
import com.sotosmen.socialnetwork.amqp.user.RabbitMQSenderUser;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RabbitMQSenderUser rabbitMQSender;
	@Autowired
	RabbitMQReceiverUser rabbitMQReceiver;
	
	public List<User> getUsers(){
		if (userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUsers);
		}
		return userRepository.findAll();
	}
	
	public User createUser(User user) {
		rabbitMQSender.sendToCreateUser(user);
		return rabbitMQReceiver.receiveToCreateUser();
	}
	
	public User updateUser(User user) {
		rabbitMQSender.sendToUpdateUser(user);
		return rabbitMQReceiver.receiveToUpdateUser();
	}
	public String deleteAllUsers() {
		if (userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUsers);
		} else {
			userRepository.deleteAll();
		}
		return Strings.deletionS;
	}
	public User getUser(String username) {
		if (userExists(username)) {
			return userRepository.findById(username).get();
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
		}
	}
	
	public String deleteUser(String username) {
		rabbitMQSender.sendToDeleteUser(username);
		return rabbitMQReceiver.receiveToDeleteUser();
	}
	public boolean userExists(String username) {
		if (userRepository.findById(username).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
