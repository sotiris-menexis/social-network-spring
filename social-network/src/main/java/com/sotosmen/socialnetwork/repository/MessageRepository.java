package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.messaging.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
	List<Message> findByOwnerConversation(Long id);
	List<Message> findByReceiverUser(String username);
	List<Message> findBySenderUser(String username);
	void deleteByOwnerConversation(Long convId);
	void deleteByReceiverUserOrSenderUser(String username1,String username2);
}
