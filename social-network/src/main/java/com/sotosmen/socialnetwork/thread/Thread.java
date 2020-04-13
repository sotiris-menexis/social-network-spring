package com.sotosmen.socialnetwork.thread;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="threads")
@Setter
public class Thread {
	@Id @Getter
	@Column(name="thread_name")
	private String threadName;
	@Column(name="user_id") @Getter
	private String creatorUser;
	@Column(name="description") @Getter
	private String description;
	@Column(name="timestamp") @Getter
	private Date timestamp;
	@Column(name="votes") @Getter
	private long votes;
	@Column(name="type") @Getter
	private String type;
}
