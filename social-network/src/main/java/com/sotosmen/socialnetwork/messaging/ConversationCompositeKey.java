package com.sotosmen.socialnetwork.messaging;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ConversationCompositeKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3274374711886609584L;
	@Column(name="creator_user_id")
	private String creatorUser;
	@Column(name="receiver_user_id")
	private String receiverUser;
}
