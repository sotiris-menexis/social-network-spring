package com.sotosmen.socialnetwork.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="friend_requests")
@Getter @Setter
public class FriendRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long friend_request_id;
	@Column(name="from_user_id")
	private String fromUserId;
	@Column(name="to_user_id")
	private String toUserId;
}
