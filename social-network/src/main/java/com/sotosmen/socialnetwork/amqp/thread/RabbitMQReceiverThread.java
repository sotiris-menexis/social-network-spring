package com.sotosmen.socialnetwork.amqp.thread;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

@Service
public class RabbitMQReceiverThread {
	@Autowired
	@Qualifier("rabbitTemplateThread")
	RabbitTemplate rabbitTemplate;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	UserRepository userRepository;
	@Value("${thread.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${thread.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${thread.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Thread receiveToCreateThread(){
		Thread thread = (Thread) rabbitTemplate.receiveAndConvert(queuePost);
		if (threadRepository.findById(thread.getId()).isPresent()) {
			throw new ResourceException(HttpStatus.FORBIDDEN, Strings.threadEx);
		} else {
			threadRepository.save(thread);
			return thread;
		}
	}
	public Thread receiveToUpdateThread() {
		Thread thread = (Thread) rabbitTemplate.receiveAndConvert(queuePut);
		if (threadRepository.findById(thread.getId()).isPresent()) {
			threadRepository.deleteById(thread.getId());
			threadRepository.save(thread);
			return thread;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThread);
		}
	}
	public String receiveToDeleteAllThreadsOfUser() {
		String username = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		if (userRepository.count() != 0 && threadRepository.count() != 0) {
			if (userRepository.findById(username).isPresent()) {
				threadRepository.deleteByIdUserId(username);
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
	
	public String receiveToDeleteThreadOfUser() {
		String result = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		String[] splitted = result.split("/$#@");
		String username = splitted[0];
		String threadName = splitted[1];
		if (userRepository.count() != 0 && threadRepository.count() != 0) {
			if (userRepository.findById(username).isPresent()) {
				ThreadCompositeKey threadId = new ThreadCompositeKey(threadName,username);
				if(threadRepository.findById(threadId).isPresent()) {
					threadRepository.deleteById(threadId);
					return Strings.deletionS;
				}else {
					throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThread);
				}
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else if(userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	
}
