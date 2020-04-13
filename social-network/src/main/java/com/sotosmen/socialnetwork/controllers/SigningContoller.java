package com.sotosmen.socialnetwork.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.services.SigningService;
import com.sotosmen.socialnetwork.user.User;

@RestController
public class SigningContoller {
	@Autowired
	SigningService signingService;
	
	@GetMapping("/login/{username}/{password}")
	public User logIn(@PathVariable(value = "username") String username
					 ,@PathVariable(value = "password") String password) {
		return signingService.logIn(username, password);
	}
	
	@PostMapping("/signup")
	public User signup(@Valid @RequestBody User user) {
		return signingService.signUp(user);
	}
	
}
