package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.services.FriendService;
import com.sotosmen.socialnetwork.user.Friend;

@RestController
public class FriendController {
	@Autowired
	FriendService friendService;
	
	@GetMapping("/friends/{username}")
	public List<Friend> findFriends(@PathVariable String username) {
		return friendService.findFriends(username);
	}
	@PostMapping("/friends")
	public Friend createFriend(@RequestBody Friend friend) {
		return friendService.createFriend(friend);
	}
	@GetMapping("/friends/users/{username}")
	public List<Friend> findAllFriendsOfUser(String username){
		return friendService.findAllFriendsOfUser(username);
	}
	@DeleteMapping("/friends/users/{username1}/{username2}")
	public String deleteFriendOfUser(@PathVariable String username1
			,@PathVariable String username2) {
		return friendService.deleteFriendOfUser(username1,username2);
	}
	@DeleteMapping("/friends/users/{username}")
	public String deleteAllFriendsOfUser(@PathVariable String username) {
		return friendService.deleteAllFriendsOfUser(username);
	}
}
