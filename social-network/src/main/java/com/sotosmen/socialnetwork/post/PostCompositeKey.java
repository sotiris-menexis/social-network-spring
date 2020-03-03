package com.sotosmen.socialnetwork.post;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

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
public class PostCompositeKey implements Serializable {
	private static final long serialVersionUID = 7049453470103266284L;
	@Column(name="post_id")@Getter @Setter
	private String id = UUID.randomUUID().toString();
	@Column(name="post_creator_user_id")
	private String userIdP;
	@Column(name="thread_id")
	private ThreadCompositeKey threadId;
	
}
