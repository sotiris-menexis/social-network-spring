package com.sotosmen.socialnetwork.thread;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="threads")
public class Thread {
	@EmbeddedId @Getter @Setter
	private ThreadCompositeKey id;
	@MapsId("userId") @Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",insertable=false,updatable=false)
	private User creatorUser;
	@Column(name="description") @Getter @Setter
	private String description;
	@Column(name="timestamp") @Getter @Setter
	private Date timestamp;
	@Column(name="votes") @Getter @Setter
	private long votes;
	@OneToMany(mappedBy="ownerThread")
	List<Post> posts;
}
