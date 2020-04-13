package com.sotosmen.socialnetwork.messaging;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="messages")
@Getter @Setter
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="message_id")
	private Long id;
	@Column(name="sender_user_id")
	private String senderUser;
	@Column(name="receiver_user_id")
	private String receiverUser;
	@Column(name="owner_conversation_id")
	private Long ownerConversation;
	@Column(name="timestamp")
	private Date timestamp;
	@Column(name="text")
	private String text;
}
