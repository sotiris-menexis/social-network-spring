package com.sotosmen.socialnetwork.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sotosmen.socialnetwork.validators.validPassword;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Setter @Getter
public class User {
	@Id
	@Size(min=3,message="Username should be at least 6 characters.")
	@Column(name="user_id")
    private String username;
	@Size(min=6,message="Password should be at least 8 characters.")
	@validPassword
    @Column(name="password")
    private String password;
	@Email(message="Email should be valid.")
    @Column(name="email")
    private String email;
    @Column(name="type")
    private String type;
    @Column(name="regNum")
    private String regNum;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="timestamp")
    private Date timestamp;
}