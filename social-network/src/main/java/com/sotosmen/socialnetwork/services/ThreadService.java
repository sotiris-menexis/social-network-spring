package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.thread.RabbitMQReceiverThread;
import com.sotosmen.socialnetwork.amqp.thread.RabbitMQSenderThread;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.thread.Thread;

@Service
public class ThreadService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	RabbitMQSenderThread rabbitMQSender;
	@Autowired
	RabbitMQReceiverThread rabbitMQReceiver;
	
	public Thread createThread(Thread thread) {
		rabbitMQSender.sendToCreateThread(thread);
		return rabbitMQReceiver.receiveToCreateThread();
	}
	public Thread updateThread(Thread thread) {
		rabbitMQSender.sendToUpdateThread(thread);
		return rabbitMQReceiver.receiveToUpdateThread();
	}
	public Thread getThreadByName(String threadName) {
		Thread result = threadRepository.findById(threadName).get();
		if (result != null) {
			return result;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThread);
		}
	}
	public List<Thread> getThreadByUsername(String username) {
		List<Thread> result = threadRepository.findByCreatorUser(username);
		if (result != null) {
			return result;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		}
	}
	public List<Thread> getThreads(){
		if (threadRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		} else {
			return threadRepository.findAll();
		}
	}
	public String deleteAllThreads() {
		if (threadRepository.count()!=0) {
			threadRepository.deleteAll();
			return Strings.deletionS;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		}
	}
	
	public String deleteAllThreadsOfUser(String username) {
		rabbitMQSender.sendToDeleteThread(username);
		return rabbitMQReceiver.receiveToDeleteAllThreadsOfUser();
	}
	public String deleteThread(String threadName) {
		rabbitMQSender.sendToDeleteThread(threadName);
		return rabbitMQReceiver.receiveToDeleteThread();
		
	}
	
}
