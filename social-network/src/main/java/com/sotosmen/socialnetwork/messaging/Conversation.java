package com.sotosmen.socialnetwork.messaging;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sotosmen.socialnetwork.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="conversations")
public class Conversation {
	@EmbeddedId
	@Getter @Setter
	private ConversationCompositeKey id;
	@MapsId("creator_user_id")
	@ManyToOne(fetch=FetchType.LAZY) @Setter
	@JoinColumn(name="creator_user_id",referencedColumnName="user_id")
	private User creatorUser;
	@MapsId("receiver_user_id")
	@ManyToOne(fetch=FetchType.LAZY) @Setter
	@JoinColumn(name="receiver_user_id",referencedColumnName="user_id")
	private User receiverUser;
	@OneToMany(mappedBy="ownerConversation")
	private List<Message> messages;
}
