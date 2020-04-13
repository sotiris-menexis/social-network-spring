package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.services.UserService;
import com.sotosmen.socialnetwork.user.User;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@DeleteMapping("/users")
	public String deleteAllUsers() {
		return userService.deleteAllUsers();
	}

	@GetMapping("/users/{username}")
	public User getUser(@PathVariable String username) {
		return userService.getUser(username);
	}

	@DeleteMapping("/users/{username}")
	public String deleteUser(@PathVariable String username) {
		return userService.deleteUser(username);
	}
}
