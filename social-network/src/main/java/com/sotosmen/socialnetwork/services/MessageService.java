package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.message.RabbitMQReceiverMessage;
import com.sotosmen.socialnetwork.amqp.message.RabbitMQSenderMessage;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.repository.MessageRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@Service
public class MessageService {
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	RabbitMQSenderMessage rabbitMQSender;
	@Autowired
	RabbitMQReceiverMessage rabbitMQReceiver;
	
	public List<Message> getAllMessages(){
		if(messageRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}else {
			return messageRepository.findAll();
		}
	}
	
	public List<Message> getAllMessagesConversation(Long convId){
		if(messageRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}else {
			if(messageRepository.findByOwnerConversation(convId).isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessageCon);
			}else {
				return messageRepository.findByOwnerConversation(convId);
			}
		}
	}
	
	public List<Message> getAllMessagesUser(String username){
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
	
	public Message createMessage(Message message) {
		if(message!=null) {
			rabbitMQSender.sendMessageToCreate(message);
			return rabbitMQReceiver.receiveMessageToCreate();
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT,Strings.noMessagesContent);
		}
	}
	
	public String deleteMessage(String messageId) {
		Message message = new Message();
		message.setId(Long.valueOf(messageId));
		rabbitMQSender.sendMessageToDelete(message);
		return rabbitMQReceiver.receiveMessageToDelete();
	}
	
	public String deleteByConversation(String convId) {
		Message message = new Message();
		message.setOwnerConversation(Long.valueOf(convId));
		rabbitMQSender.sendMessageToDelete(message);
		return rabbitMQReceiver.receiveMessagesConvToDelete();
	}
	
	public String deleteByUser(String username) {
		Message message = new Message();
		message.setSenderUser(username);
		rabbitMQSender.sendMessageToDelete(message);
		return rabbitMQReceiver.receiveMessagesUserToDelete();
	}
	
	public String deleteAllMessages() {
		if(messageRepository.count()!=0) {
			messageRepository.deleteAll();
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}
	}
}
