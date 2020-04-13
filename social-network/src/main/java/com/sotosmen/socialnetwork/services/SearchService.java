package com.sotosmen.socialnetwork.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.user.User;

@Service
public class SearchService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	PostRepository postRepository;
	
	public List<User> getUsers(String keyword, String type){
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
	public List<Thread> getThreads(String keyword, String type){
		if(userRepository.count()!=0) {
			List<Thread> allthreads = threadRepository.findByType(type);
			allthreads.addAll(threadRepository.findByType("All"));
			List<Thread> temp = new ArrayList<Thread>();
			for(Thread t:allthreads) {
				if(t.getThreadName().contains(keyword)) {
					temp.add(t);
				}
			}
			if(temp.isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreadsSearch);
			}
			return temp;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	public List<Post> getPosts(String keyword, String type){
		if(postRepository.count()!=0) {
			List<Post> allposts = postRepository.findByType(type);
			allposts.addAll(postRepository.findByType("All"));
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
