package com.sotosmen.socialnetwork.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.user.User;

@RestController
public class SearchContoller {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/search/users/{keyword}/{type}")
	public List<User> getUsers(@PathVariable(value="keyword") String keyword
							 , @PathVariable(value="type") String type){
		if(userRepository.count()!=0) {
			List<User> allusers = userRepository.findByType(type);
			List<User> temp = new ArrayList<User>();
			for(User u:allusers) {
				if(u.getUsername().contains(keyword)) {
					temp.add(u);
				}
			}
			if(temp.isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsersSearch);
			}
			return temp;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	@GetMapping("/search/threads/{keyword}/{type}")
	public List<Thread> getThreads(@PathVariable(value="keyword") String keyword
								 , @PathVariable(value="type") String type){
		if(userRepository.count()!=0) {
			List<Thread> allthreads = threadRepository.findByType(type);
			List<Thread> temp = new ArrayList<Thread>();
			for(Thread t:allthreads) {
				if(t.getId().getThreadName().contains(keyword)) {
					temp.add(t);
				}
			}
			if(temp.isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsersSearch);
			}
			return temp;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	@GetMapping("/search/posts/{keyword}/{type}")
	public List<Post> getPosts(@PathVariable(value="keyword") String keyword
							  ,@PathVariable(value="type") String type){
		if(postRepository.count()!=0) {
			List<Post> allposts = postRepository.findByType(type);
			List<Post> temp = new ArrayList<Post>();
			for(Post p:allposts) {
				if(p.getText().contains(keyword)) {
					temp.add(p);
				}
			}
			if(temp.isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPostsSearch);
			}
			return temp;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPosts);
		}
	}
}
