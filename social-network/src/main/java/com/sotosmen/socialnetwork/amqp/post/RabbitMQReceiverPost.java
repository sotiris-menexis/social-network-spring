package com.sotosmen.socialnetwork.amqp.post;

import java.util.List;

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
import com.sotosmen.socialnetwork.thread.Thread;

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
	
	public Post receiveToCreatePost() {
		Post post = (Post)rabbitTemplate.receiveAndConvert(queuePost);
		if(post != null) {
			if(postRepository.findById(post.getId()).isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN, Strings.exPost);
			}else {
				postRepository.save(post);
				return post;
			}
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT, Strings.nullObj);
		}
	}
	public Post receiveToUpdatePost() {
		Post post = (Post)rabbitTemplate.receiveAndConvert(queuePut);
		if(post != null) {
			if(postRepository.findById(post.getId()).isPresent()) {
				postRepository.deleteById(post.getId());
				postRepository.save(post);
				return post;
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noPost);
			}
		}else {
			throw new ResourceException(HttpStatus.NO_CONTENT, Strings.nullObj);
		}
	}
	public String receiveToDeletePostsOfUser() {
		String username;
		while(true) {
			username = (String)rabbitTemplate.receiveAndConvert(queueDelete);
			if(username.contains("/#@/")) {
				break;
			}
		}
		String[] result = username.split("/#@/");
		username = result[0];
		if(userRepository.count()!=0) {
			if(userRepository.findById(username).isPresent()) {
				if(postRepository.count()!=0) {
					postRepository.deleteByIdUserIdP(username);
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
		String threadName;
		while(true) {
			threadName = (String)rabbitTemplate.receiveAndConvert(queueDelete);
			if(threadName.contains("/@#/")) {
				break;
			}
		}
		String[] result = threadName.split("/@#/");
		threadName = result[0];
		if(threadRepository.count()!=0) {
			List<Thread> threads = threadRepository.findByIdThreadName(threadName);
			if(postRepository.count()!=0) {
				postRepository.deleteByIdThreadId(threads.get(0).getId());
				return Strings.deletionS;
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noPosts);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	
}
