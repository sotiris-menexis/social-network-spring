package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.conversation.RabbitMQReceiverConversation;
import com.sotosmen.socialnetwork.amqp.conversation.RabbitMQSenderConversation;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.repository.ConversationRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@Service
public class ConversationService {
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	RabbitMQSenderConversation rabbitMQSender;
	@Autowired
	RabbitMQReceiverConversation rabbitMQReceiver;
	
	public Conversation getConversation(String username1,String username2) {
		if(conversationRepository.count()!=0) {
			if(conversationRepository
			.findByCreatorUserAndReceiverUser(username1, username2).isPresent()) {
				return conversationRepository
						.findByCreatorUserAndReceiverUser(username1, username2).get();
			}else if(conversationRepository
					.findByCreatorUserAndReceiverUser(username2, username1).isPresent()) {
				return conversationRepository
						.findByCreatorUserAndReceiverUser(username2, username1).get();
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noConversation);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noConversation);
		}
	}
	
	public List<Conversation> getAllConversations(){
		if(conversationRepository.count()!=0) {
			return conversationRepository.findAll();
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noConversations);
		}
	}
	
	public Conversation createConversation(Conversation conversation) {
		if(conversationRepository.count()!=0) {
			if(conversationRepository
			.findByCreatorUserAndReceiverUser(conversation.getCreatorUser(), conversation.getReceiverUser()).isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN,Strings.exConversation);
			}else if(conversationRepository
					.findByCreatorUserAndReceiverUser(conversation.getReceiverUser(), conversation.getCreatorUser()).isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN,Strings.exConversation);
			}else {
				rabbitMQSender.sendConversationToCreate(conversation);
				return rabbitMQReceiver.receiveConversationToCreate();
			}
		}else {
			rabbitMQSender.sendConversationToCreate(conversation);
			return rabbitMQReceiver.receiveConversationToCreate();
		}
	}
	
	public String deleteUserConversations(String username) {
		Conversation conversation = new Conversation();
		conversation.setCreatorUser(username);
		rabbitMQSender.sendConversationToDelete(conversation);
		return rabbitMQReceiver.receiveConversationsToDelete();
	}
	public String deleteUserConversation(String convId) {
		Conversation conversation = new Conversation();
		conversation.setId(Long.valueOf(convId));
		rabbitMQSender.sendConversationToDelete(conversation);
		return rabbitMQReceiver.receiveConversationToDelete();
	}
}
