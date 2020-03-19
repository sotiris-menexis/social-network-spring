package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

@RestController
public class PostController {
	
	@Autowired
	PostRepository postRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/posts")
	public List<Post> getAllPosts(){
		if(postRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPosts);
		}else {
			return postRepository.findAll();
		}
	}
	
	@GetMapping("/threads/{thread_name}/posts")
	public List<Post> getAllPostsThread(@PathVariable String thread_name){
		List<Thread> thread = threadRepository.findByIdThreadName(thread_name);
		ThreadCompositeKey id = thread.get(0).getId();
		if(postRepository.findByIdThreadId(id).isEmpty()) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsThread);
		}else {
			return postRepository.findByIdThreadId(id);
		}
	}
	
	@GetMapping("/users/{username}/posts")
	public List<Post> getAllPostsUser(@PathVariable String username){
		if(postRepository.findByIdUserIdP(username).isEmpty()) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsUser);
		}else {
			return postRepository.findByIdUserIdP(username);
		}
	}
	
	@PostMapping("/posts")
	public void createPost(@RequestBody Post post) {
		if(post != null) {
			if(postRepository.findById(post.getId()).isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN, Strings.exPost);
			}else {
				postRepository.save(post);
			}
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT, Strings.nullObj);
		}
	}
	
	@DeleteMapping("/posts")
	public void deleteAllPost() {
		if(postRepository.count()!=0) {
			postRepository.deleteAll();
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPosts);
		}
	}
	
}
