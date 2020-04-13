package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.services.ConversationService;

@RestController
public class ConversationController {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	ConversationService conversationService;
	
	@GetMapping("/conversations/{username1}/{username2}")
	public Conversation getConversation(@PathVariable(value = "username1") String username1
									    ,@PathVariable(value = "username2") String username2) {
		return conversationService.getConversation(username1,username2);
	}
	
	@GetMapping("/conversations")
	public List<Conversation> getAllConversations(){
		return conversationService.getAllConversations();
	}
	
	@PostMapping("/conversations")
	public Conversation createConversation(@RequestBody Conversation conversation) {
		return conversationService.createConversation(conversation);
	}
	
	@DeleteMapping("/conversations/{username}")
	public String deleteUserConversations(@PathVariable String username) {
		return conversationService.deleteUserConversations(username);
	}
	
	@DeleteMapping("/conversation/{convId}")
	public String deleteUserConversation(@PathVariable String convId) {
		return conversationService.deleteUserConversation(convId);
	}
}
