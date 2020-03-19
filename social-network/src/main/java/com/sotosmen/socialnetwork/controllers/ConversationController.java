package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.messaging.ConversationCompositeKey;
import com.sotosmen.socialnetwork.repository.ConversationRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@RestController
public class ConversationController {
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired 
	UserRepository userRepository;
	
	@GetMapping("/conversations/{username1}/{username2}")
	public Conversation getConversation(@PathVariable(value = "username1") String username1
									    ,@PathVariable(value = "useraname2") String username2) {
		ConversationCompositeKey key = new ConversationCompositeKey(username1,username2);
		if(conversationRepository.count()!=0) {
			return conversationRepository.findById(key).get();
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noConversation);
		}
	}
	
	@GetMapping("/conversations")
	public List<Conversation> getAllConversations(){
		if(conversationRepository.count()!=0) {
			return conversationRepository.findAll();
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noConversations);
		}
	}
	
	@PostMapping("/conversations/{username1}/{username2}")
	public void createConversation(@PathVariable(value = "username1")String username1
								  ,@PathVariable(value = "username2")String username2) {
		ConversationCompositeKey key = new ConversationCompositeKey(username1,username2);
		Conversation conv = new Conversation();
		conv.setCreatorUser(userRepository.findById(username1).get());
		conv.setReceiverUser(userRepository.findById(username2).get());
		conv.setId(key);
		conversationRepository.save(conv);
	}
	@DeleteMapping("/conversations")
	public void deleteConversation(String username1,String username2) {
		ConversationCompositeKey key = new ConversationCompositeKey(username1,username2);
		conversationRepository.deleteById(key);
	}
}
