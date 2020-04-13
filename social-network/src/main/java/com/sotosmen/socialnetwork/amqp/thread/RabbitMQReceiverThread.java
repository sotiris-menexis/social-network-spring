package com.sotosmen.socialnetwork.amqp.thread;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.PostRepository;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.repository.VoteRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;

@Service
public class RabbitMQReceiverThread {
	@Autowired
	@Qualifier("rabbitTemplateThread")
	RabbitTemplate rabbitTemplate;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VoteRepository voteRepository;
	@Autowired
	PostRepository postRepository;
	
	@Value("${thread.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${thread.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${thread.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Thread receiveToCreateThread(){
		Thread thread = (Thread) rabbitTemplate.receiveAndConvert(queuePost);
		if (threadRepository.findById(thread.getThreadName()).isPresent()) {
			throw new ResourceException(HttpStatus.FORBIDDEN, Strings.threadEx);
		} else {
			threadRepository.save(thread);
			return thread;
		}
	}
	@Transactional
	public Thread receiveToUpdateThread() {
		Thread thread = (Thread) rabbitTemplate.receiveAndConvert(queuePut);
		Optional<Thread> temp = threadRepository.findById(thread.getThreadName());
		if (temp.isPresent()) {
			threadRepository.save(thread);
			postRepository.updatePostTypeByThread(thread.getThreadName(), thread.getType());
			return thread;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThread);
		}
	}
	public String receiveToDeleteAllThreadsOfUser() {
		String username = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		if (userRepository.count() != 0 && threadRepository.count() != 0) {
			if (userRepository.findById(username).isPresent()) {
				threadRepository.deleteByCreatorUser(username);
				return Strings.deletionS;
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else if(userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	
	@Transactional
	public String receiveToDeleteThread() {
		String threadName = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		if(threadRepository.findById(threadName).isPresent()) {
			threadRepository.deleteById(threadName);
			postRepository.deleteByOwnerThread(threadName);
			voteRepository.deleteByThread(threadName);
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThread);
		}
	}
	
}
