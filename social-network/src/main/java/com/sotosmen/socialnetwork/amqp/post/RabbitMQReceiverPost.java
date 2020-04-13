package com.sotosmen.socialnetwork.amqp.post;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@Service
public class RabbitMQReceiverPost {
	@Autowired
	@Qualifier("rabbitTemplatePost")
	RabbitTemplate rabbitTemplate;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Value("${post.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${post.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${post.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Post receiveToCreatePost(String username,String threadName) {
		Post post = (Post)rabbitTemplate.receiveAndConvert(queuePost);
		if(post.getId() == null) {
			post.setCreatorUser(username);
			post.setOwnerThread(threadName);
			postRepository.save(post);
			return post;
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT, Strings.nullObj);
		}
	}
	public Post receiveToUpdatePost() {
		Post post = (Post)rabbitTemplate.receiveAndConvert(queuePut);
		if(postRepository.findById(post.getId()).isPresent()) {
			postRepository.save(post);
			return post;
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT, Strings.nullObj);
		}
	}
	public String receiveToDeletePostsOfUser() {
		String username = (String)rabbitTemplate.receiveAndConvert(queueDelete);
		if(userRepository.count()!=0) {
			if(userRepository.findById(username).isPresent()) {
				if(postRepository.count()!=0) {
					postRepository.deleteByCreatorUser(username);
					return Strings.deletionS;
				}else {
					throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPosts);
				}
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	
	public String receiveToDeletePostsOfThread() {
		Post post = (Post)rabbitTemplate.receiveAndConvert(queueDelete);
		String threadName = post.getOwnerThread();
		if(threadRepository.count()!=0) {
			if(postRepository.count()!=0) {
				postRepository.deleteByOwnerThread(threadName);
				return Strings.deletionS;
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPosts);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	
	public String receiveToDeletePost() {
		String post = (String)rabbitTemplate.receiveAndConvert(queueDelete);
		if(postRepository.count()!=0) {
			postRepository.deleteById(Long.valueOf(post));
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPosts);
		}
	}
	
}
