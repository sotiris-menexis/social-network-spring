package com.sotosmen.socialnetwork.amqp.conversation;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.repository.ConversationRepository;
import com.sotosmen.socialnetwork.strings.Strings;


@Service
public class RabbitMQReceiverConversation {
	
	@Autowired
	@Qualifier("rabbitTemplateConversation")
	RabbitTemplate rabbitTemplate;
	@Autowired
	ConversationRepository conversationRepository;
	@Value("${conversation.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${conversation.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${conversation.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Conversation receiveConversationToCreate() {
		Conversation conversation = (Conversation) rabbitTemplate
				.receiveAndConvert(queuePost);
		conversationRepository.save(conversation);
		return conversation;
	}
	@Transactional
	public String receiveConversationsToDelete() {
		Conversation conversation = (Conversation) rabbitTemplate
				.receiveAndConvert(queueDelete);
		if(conversationRepository.count()!=0) {
			conversationRepository
			.deleteByCreatorUserOrReceiverUser(conversation.getCreatorUser(), conversation.getCreatorUser());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noConversations);
		}
	}
	@Transactional
	public String receiveConversationToDelete() {
		Conversation conversation = (Conversation) rabbitTemplate
				.receiveAndConvert(queueDelete);
		if(conversationRepository.count()!=0) {
			conversationRepository.deleteById(conversation.getId());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noConversation);
		}
	}

}
