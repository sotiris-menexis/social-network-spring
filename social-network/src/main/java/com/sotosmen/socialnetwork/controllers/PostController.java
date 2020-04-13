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

import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.services.PostService;

@RestController
public class PostController {
	@Autowired
	PostService postService;
	
	@GetMapping("/posts")
	public List<Post> getAllPosts(){
		return postService.getAllPosts();
	}
	
	@GetMapping("/threads/{thread_name}/posts")
	public List<Post> getAllPostsOfThread(@PathVariable(value="thread_name") String threadName){
		return postService.getAllPostsOfThread(threadName);
	}
	
	@GetMapping("/users/{username}/posts")
	public List<Post> getAllPostsOfUser(@PathVariable(value="username") String username){
		return postService.getAllPostsOfUser(username);
	}
	
	@PostMapping("/posts/users/{username}/threads/{thread_name}")
	public Post createPost(@PathVariable(value="username") String username,
			@PathVariable(value="thread_name") String threadName, @RequestBody Post post) {
		return postService.createPost(username, threadName, post);
	}
	@PutMapping("/posts")
	public Post updatePost(@RequestBody Post post) {
		return postService.updatePost(post);
	}
	@DeleteMapping("/posts/users/{username}")
	public String deleteAllPostsOfUser(@PathVariable String username) {
		return postService.deleteAllPostsOfUser(username);
	}
	@DeleteMapping("/posts/threads/{threadName}")
	public String deleteAllPostsOfThread(@PathVariable String threadName) {
		return postService.deleteAllPostsOfThread(threadName);
	}
	@DeleteMapping("/posts")
	public String deleteAllPosts() {
		return postService.deleteAllPosts();
	}
	@DeleteMapping("/post/{postId}")
	public String deletePost(@PathVariable String postId) {
		return postService.deletePost(postId);
	}
	
}
