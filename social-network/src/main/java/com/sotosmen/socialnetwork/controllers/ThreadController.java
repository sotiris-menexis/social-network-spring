package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;

@RestController
public class ThreadController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ThreadRepository threadRepository;

	@GetMapping("/threads")
	public List<Thread> getThreads() {
		if (threadRepository.count()==0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		} else {
			return threadRepository.findAll();
		}
	}

	@GetMapping("/threads/{thread_name}")
	public List<Thread> getThreadByName(@PathVariable String threadName) {
		List<Thread> result = threadRepository.findByIdThreadName(threadName);
		if (result != null) {
			return result;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThread);
		}
	}
	
	@GetMapping("/threads/users/{username}")
	public List<Thread> getThreadByUsername(@PathVariable String username) {
		List<Thread> result = threadRepository.findByIdUserId(username);
		if (result != null) {
			return result;
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		}
	}

	@PostMapping("/threads")
	public void createThread(@RequestBody Thread thread) {
		if (threadRepository.findById(thread.getId()).isPresent()) {
			throw new ResourceException(HttpStatus.FORBIDDEN, Strings.threadEx);
		} else {
			threadRepository.save(thread);
		}
	}

	@PutMapping("/threads")
	public void updateThread(@RequestBody Thread thread) {
		if (threadRepository.findById(thread.getId()).isPresent()) {
			threadRepository.deleteById(thread.getId());
			threadRepository.save(thread);
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThread);
		}
	}

	@DeleteMapping("/threads")
	public void deleteAllThreads() {
		if (threadRepository.count()!=0) {
			threadRepository.deleteAll();
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noThreads);
		}
	}

	@DeleteMapping("/threads/users/{username}")
	public void deleteAllThreadsOfUser(@PathVariable String username) {
		if (userRepository.count() != 0 && threadRepository.count() != 0) {
			if (userRepository.findById(username).isPresent()) {
				threadRepository.deleteByIdUserId(username);
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else if(userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noThreads);
		}
	}
	
	@DeleteMapping("/threads/{thread_name}/users/{username}")
	public void deleteThreadOfUser(@PathVariable(value="thread_name") String threadName,
								   @PathVariable(value="username") String username) {
		if (userRepository.count() != 0 && threadRepository.count() != 0) {
			if (userRepository.findById(username).isPresent()) {
				ThreadCompositeKey threadId = new ThreadCompositeKey(threadName,username);
				if(threadRepository.findById(threadId).isPresent()) {
					threadRepository.deleteById(threadId);
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
