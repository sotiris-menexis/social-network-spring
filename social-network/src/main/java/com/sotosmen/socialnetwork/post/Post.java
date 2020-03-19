package com.sotosmen.socialnetwork.post;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="posts")
public class Post {
	@EmbeddedId @Getter @Setter
	private PostCompositeKey id;
	@Column(name="text") @Getter @Setter
	private String text;
	@Column(name="votes") @Getter @Setter
	private long votes;
	@Column(name="timestamp") @Getter @Setter
	private Date timestamp;
	@Column(name="type") @Getter @Setter
	private String type;
	@MapsId("threadId")
	@ManyToOne @Setter
	@JoinColumns({@JoinColumn(name="thread_owner_name",referencedColumnName="thread_name"),
				  @JoinColumn(name="thread_creator_user_id",referencedColumnName="user_id")})
	private Thread ownerThread;
	@MapsId("userIdP")
	@ManyToOne @Setter
	@JoinColumn(name="post_creator_user_id",insertable=false,updatable=false)
	private User creatorUser;
	
}
