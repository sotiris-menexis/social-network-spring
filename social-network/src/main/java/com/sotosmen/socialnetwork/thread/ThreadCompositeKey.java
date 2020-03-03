package com.sotosmen.socialnetwork.thread;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ThreadCompositeKey implements Serializable {
	private static final long serialVersionUID = 8634724943315752191L;
	@Column(name="user_id") @Getter @Setter
	private String userId;
	@Column(name="thread_name") @Getter @Setter
	private String threadName;
}
