package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.post.RabbitMQReceiverPost;
import com.sotosmen.socialnetwork.amqp.post.RabbitMQSenderPost;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	ThreadRepository threadRepository;
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
		List<Thread> thread = threadRepository.findByIdThreadName(threadName);
		ThreadCompositeKey id = thread.get(0).getId();
		if(postRepository.findByIdThreadId(id).isEmpty()) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsThread);
		}else {
			return postRepository.findByIdThreadId(id);
		}
	}
	public List<Post> getAllPostsOfUser(String username){
		if(postRepository.findByIdUserIdP(username).isEmpty()) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPostsUser);
		}else {
			return postRepository.findByIdUserIdP(username);
		}
	}
	public Post createPost(Post post) {
		rabbitMQSender.sendToCreatePost(post);
		return rabbitMQReceiver.receiveToCreatePost();
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
		rabbitMQSender.sendToDeletePost(username+"/#@/  ");
		return rabbitMQReceiver.receiveToDeletePostsOfUser();
	}
	public String deleteAllPostsOfThread(String threadName) {
		rabbitMQSender.sendToDeletePost(threadName+"/@#/  ");
		return rabbitMQReceiver.receiveToDeletePostsOfThread();
	}
	
}
