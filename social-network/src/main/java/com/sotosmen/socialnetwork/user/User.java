package com.sotosmen.socialnetwork.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sotosmen.socialnetwork.messaging.Conversation;
import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.validators.validPassword;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class User {
	@Id
	@Size(min=6,message="Username should be at least 6 characters.")
	@Column(name="user_id") @Getter @Setter
    private String username;
	@Size(min=8,message="Password should be at least 8 characters.")
	@validPassword
    @Column(name="password") @Getter @Setter
    private String password;
	@Email(message="Email should be valid.")
    @Column(name="email") @Getter @Setter
    private String email;
    @Column(name="type") @Getter @Setter
    private String type;
    @Column(name="regNum") @Getter @Setter
    private String regNum;
    @Column(name="firstName") @Getter @Setter
    private String firstName;
    @Column(name="lastName") @Getter @Setter
    private String lastName;
    @Column(name="timestamp") @Getter @Setter
    private Date timestamp;
    @Column(name="profileImage")
    private String profileImage;
    @OneToMany(mappedBy="creatorUser") @Getter @Setter
    private List<Thread> threads;
    @OneToMany(mappedBy="creatorUser") @Getter @Setter
    private List<Post> posts;
    @OneToMany(mappedBy="senderUser") @Getter @Setter
    private List<Message> messagesSent;
    @OneToMany(mappedBy="receiverUser") @Getter @Setter
    private List<Message> messagesReceived;
    @OneToMany(mappedBy="creatorUser") @Getter @Setter
    private List<Conversation> conversationsCreated;
    @OneToMany(mappedBy="receiverUser") @Getter @Setter
    private List<Conversation> conversationsReceived;
}