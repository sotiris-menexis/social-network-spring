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
@Table(name="friends")
public class Friend {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) @Getter
	private Long id;
	@Column(name="friend_user_id_1") @Getter @Setter
	private String friendUser1;
	@Column(name="friend_user_id_2") @Getter @Setter
	private String friendUser2;
}
