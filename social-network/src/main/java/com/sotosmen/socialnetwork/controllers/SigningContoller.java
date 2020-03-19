package com.sotosmen.socialnetwork.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.User;

@RestController
public class SigningContoller {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login/{username}/{password}")
	public User logIn(@PathVariable(value = "username") String username
					 ,@PathVariable(value = "password") String password) {
		if(userRepository.count()!=0) {
			Optional<User>user =userRepository.findById(username);
			if(user.isPresent()) {
				if(user.get().getPassword().equals(password)) {
					return user.get();
				}else {
					throw new ResourceException(HttpStatus.FORBIDDEN,Strings.wrongPassword);
				}
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	
	@PostMapping("/signup")
	public User signup(@Valid @RequestBody User user) {
		if(userRepository.count()!=0) {
			Optional<User> temp = userRepository.findById(user.getUsername());
			if(temp.isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN,Strings.userEx);
			}else {
				userRepository.save(user);
				return user;
			}
		}else {
			userRepository.save(user);
			return user;
		}
	}
	
}
