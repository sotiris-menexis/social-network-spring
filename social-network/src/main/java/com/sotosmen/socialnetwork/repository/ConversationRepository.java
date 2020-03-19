package com.sotosmen.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.messaging.ConversationCompositeKey;

public interface ConversationRepository extends JpaRepository<Conversation,ConversationCompositeKey>{

}
