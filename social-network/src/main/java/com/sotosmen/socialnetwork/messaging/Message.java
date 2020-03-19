package com.sotosmen.socialnetwork.messaging;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sotosmen.socialnetwork.user.User;

import lombok.Setter;

@Entity
@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue
	@Column(name="message_id")
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY) @Setter
	@JoinColumn(name="sender_user",insertable=false,updatable=false)
	private User senderUser;
	@ManyToOne(fetch=FetchType.LAZY) @Setter
	@JoinColumn(name="receiver_user",insertable=false,updatable=false)
	private User receiverUser;
	@ManyToOne(fetch=FetchType.LAZY) @Setter
	private Conversation ownerConversation;
	@Column(name="timestamp")
	private Date timestamp;

}
