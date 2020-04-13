package com.sotosmen.socialnetwork.amqp.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.repository.MessageRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@Service
public class RabbitMQReceiverMessage {
	@Autowired
	@Qualifier("rabbitTemplateMessage")
	RabbitTemplate rabbitTemplate;
	@Autowired
	MessageRepository messageRepository;
	@Value("${message.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${message.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${message.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Message receiveMessageToCreate() {
		Message message = (Message) rabbitTemplate.receiveAndConvert(queuePost);
		messageRepository.save(message);
		return message;
	}
	
	public String receiveMessageToDelete() {
		Message message = (Message) rabbitTemplate.receiveAndConvert(queueDelete);
		if(messageRepository.count()!=0) {
			messageRepository.deleteById(message.getId());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}
	}
	
	public String receiveMessagesConvToDelete() {
		Message message = (Message) rabbitTemplate.receiveAndConvert(queueDelete);
		if(messageRepository.count()!=0) {
			messageRepository.deleteByOwnerConversation(message.getOwnerConversation());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}
	}
	
	public String receiveMessagesUserToDelete() {
		Message message = (Message) rabbitTemplate.receiveAndConvert(queueDelete);
		if(messageRepository.count()!=0) {
			messageRepository.deleteByReceiverUserOrSenderUser(message.getSenderUser(), message.getSenderUser());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noMessages);
		}
	}
	
}
