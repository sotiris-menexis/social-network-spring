package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.services.FriendRequestService;
import com.sotosmen.socialnetwork.user.FriendRequest;

@RestController
public class FriendRequestController {
	@Autowired
	FriendRequestService friendRequestService;
	
	@GetMapping("/users/{username}/friendrequest")
	public List<FriendRequest> getForUserFriendRequests(@PathVariable String username){
		return friendRequestService.getForUserFriendRequests(username);
	}
	@PostMapping("/friendrequest")
	public FriendRequest createFriendRequest(@RequestBody FriendRequest friendRequest) {
		return friendRequestService.createFriendRequest(friendRequest);
	}
	@PutMapping("/friendrequest")
	public FriendRequest updateFriendRequest(@RequestBody FriendRequest friendRequest) {
		return friendRequestService.updateFriendRequest(friendRequest);
	}
	@DeleteMapping("/friendrequest/{friendRequestId}")
	public String deleteFriendRequest(@PathVariable String friendRequestId) {
		return friendRequestService.deleteFriendRequest(friendRequestId);
	}
}
