package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.services.SearchService;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.user.User;

@RestController
public class SearchContoller {
	@Autowired
	SearchService searchService;
	
	@GetMapping("/search/users/{keyword}/{type}")
	public List<User> getUsers(@PathVariable(value="keyword") String keyword
							 , @PathVariable(value="type") String type){
		return searchService.getUsers(keyword, type);
	}
	@GetMapping("/search/threads/{keyword}/{type}")
	public List<Thread> getThreads(@PathVariable(value="keyword") String keyword
								 , @PathVariable(value="type") String type){
		return searchService.getThreads(keyword, type);
	}
	@GetMapping("/search/posts/{keyword}/{type}")
	public List<Post> getPosts(@PathVariable(value="keyword") String keyword
							  ,@PathVariable(value="type") String type){
		return searchService.getPosts(keyword, type);
	}
}
