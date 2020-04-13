package com.sotosmen.socialnetwork.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.post.RabbitMQReceiverPost;
import com.sotosmen.socialnetwork.amqp.post.RabbitMQSenderPost;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.User;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RabbitMQSenderPost rabbitMQSender;
	@Autowired
	RabbitMQReceiverPost rabbitMQReceiver;
	
	public List<Post> getAllPosts(){
		if(postRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPosts);
		}else {
			return postRepository.findAll();
		}
	}
	
	public List<Post> getAllPostsOfThread(String threadName){
		if(postRepository.findByOwnerThread(threadName).isEmpty()) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsThread);
		}else {
			return postRepository.findByOwnerThread(threadName);
		}
	}
	public List<Post> getAllPostsOfUser(String username){
		Optional<User> user = userRepository.findById(username);
		if(user.isPresent()) {
			if(postRepository.findByCreatorUser(username).isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsUser);
			}else {
				return postRepository.findByCreatorUser(username);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
		}
	}
	public Post createPost(String username, String threadName, Post post) {
		rabbitMQSender.sendToCreatePost(post);
		return rabbitMQReceiver.receiveToCreatePost(username, threadName);
	}
	public Post updatePost(Post post) {
		rabbitMQSender.sendToUpdatePost(post);
		return rabbitMQReceiver.receiveToUpdatePost();
	}
	public String deleteAllPosts() {
		if(postRepository.count()!=0) {
			postRepository.deleteAll();
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPosts);
		}
	}
	public String deleteAllPostsOfUser(String username) {
		rabbitMQSender.sendToDeletePost(username);
		return rabbitMQReceiver.receiveToDeletePostsOfUser();
	}
	public String deleteAllPostsOfThread(String threadName) {
		rabbitMQSender.sendToDeletePost(threadName);
		return rabbitMQReceiver.receiveToDeletePostsOfThread();
	}
	
	public String deletePost(String postId) {
		rabbitMQSender.sendToDeletePost(postId);
		return rabbitMQReceiver.receiveToDeletePost();
	}
	
}
