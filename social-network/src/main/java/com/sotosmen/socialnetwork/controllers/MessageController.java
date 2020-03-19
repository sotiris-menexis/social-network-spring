package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.ConversationCompositeKey;
import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.repository.MessageRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@RestController
public class MessageController {
	@Autowired
	MessageRepository messageRepository;
	
	@GetMapping("/messages")
	public List<Message> getAllMessages(){
		if(messageRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}else {
			return messageRepository.findAll();
		}
	}
	
	@GetMapping("/conversations/{conv_id}/messages")
	public List<Message> getAllMessagesConv(@PathVariable ConversationCompositeKey conv_id){
		if(messageRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}else {
			if(messageRepository.findByOwnerConversation(conv_id).isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessageCon);
			}else {
				return messageRepository.findByOwnerConversation(conv_id);
			}
		}
	}
	
	@GetMapping("/users/{username}/messages")
	public List<Message> getAllMessagesUser(@PathVariable String username){
		if(messageRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}else {
			if(messageRepository.findByReceiverUser(username).isEmpty() && 
					messageRepository.findBySenderUser(username).isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessageUser);
			}else {
				if(messageRepository.findByReceiverUser(username).isEmpty()) {
					return messageRepository.findBySenderUser(username);
				}else {
					return messageRepository.findByReceiverUser(username);
				}
			}
		}
	}
	
	@PostMapping("/messages")
	public void createMessage(@RequestBody Message message){
		if(message != null) {
			messageRepository.save(message);
		}
	}
	
	@DeleteMapping("/messages")
	public void deleteAllMessages() {
		if(messageRepository.count()!=0) {
			messageRepository.deleteAll();
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}
	}
}
