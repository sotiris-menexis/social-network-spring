package com.sotosmen.socialnetwork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.messaging.Conversation;


@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long>{
	Optional<Conversation> findByCreatorUserAndReceiverUser(String CreatorUser, String ReceiverUser);
	void deleteByCreatorUserAndReceiverUser(String CreatorUser, String ReceiverUser);
	void deleteByCreatorUserOrReceiverUser(String username1,String username2);
}
