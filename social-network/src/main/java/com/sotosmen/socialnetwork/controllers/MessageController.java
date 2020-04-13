package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.repository.MessageRepository;
import com.sotosmen.socialnetwork.services.MessageService;

@RestController
public class MessageController {
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	MessageService messageService;
	
	@GetMapping("/messages")
	public List<Message> getAllMessages(){
		return messageService.getAllMessages();
	}
	
	@GetMapping("/conversations/{convId}/messages")
	public List<Message> getAllMessagesConv(@PathVariable Long convId){
		return messageService.getAllMessagesConversation(convId);
	}
	
	@GetMapping("/users/{username}/messages")
	public List<Message> getAllMessagesUser(@PathVariable String username){
		return messageService.getAllMessagesUser(username);
	}
	
	@PostMapping("/messages")
	public Message createMessage(@RequestBody Message message){
		if(message != null) {
			messageRepository.save(message);
		}
		return messageService.createMessage(message);
	}
	
	@DeleteMapping("/messages")
	public String deleteAllMessages() {
		return messageService.deleteAllMessages();
	}
}
