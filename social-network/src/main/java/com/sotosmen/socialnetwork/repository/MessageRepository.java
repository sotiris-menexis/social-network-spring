package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.messaging.ConversationCompositeKey;
import com.sotosmen.socialnetwork.messaging.Message;

public interface MessageRepository extends JpaRepository<Message,Long>{
	List<Message> findByOwnerConversation(ConversationCompositeKey id);
	List<Message> findByReceiverUser(String username);
	List<Message> findBySenderUser(String username);
}
