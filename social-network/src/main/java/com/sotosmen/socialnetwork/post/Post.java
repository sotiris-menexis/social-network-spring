package com.sotosmen.socialnetwork.post;

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
@Table(name="posts")
@Getter @Setter
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="post_id")
	private Long id;
	@Column(name="text")
	private String text;
	@Column(name="timestamp")
	private Date timestamp;
	@Column(name="type")
	private String type;
	@Column(name="owner_thread_id")
	private String ownerThread;
	@Column(name="creator_user_id")
	private String creatorUser;
	
}
