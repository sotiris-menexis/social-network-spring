package com.sotosmen.socialnetwork.messaging;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="conversations")
@Setter @Getter
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="creator_user_id")
	private String creatorUser;
	@Column(name="receiver_user_id")
	private String receiverUser;
}
