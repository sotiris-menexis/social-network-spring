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

import com.sotosmen.socialnetwork.services.ThreadService;
import com.sotosmen.socialnetwork.thread.Thread;

@RestController
public class ThreadController {
	@Autowired
	ThreadService threadService;
	
	@GetMapping("/threads")
	public List<Thread> getThreads() {
		return threadService.getThreads();
	}

	@GetMapping("/threads/{thread_name}")
	public List<Thread> getThreadByName(@PathVariable String threadName) {
		return threadService.getThreadByName(threadName);
	}
	
	@GetMapping("/threads/users/{username}")
	public List<Thread> getThreadByUsername(@PathVariable String username) {
		return threadService.getThreadByUsername(username);
	}

	@PostMapping("/threads")
	public Thread createThread(@RequestBody Thread thread) {
		return threadService.createThread(thread);
	}

	@PutMapping("/threads")
	public Thread updateThread(@RequestBody Thread thread) {
		return threadService.updateThread(thread);
	}

	@DeleteMapping("/threads")
	public String deleteAllThreads() {
		return threadService.deleteAllThreads();
	}

	@DeleteMapping("/threads/users/{username}")
	public String deleteAllThreadsOfUser(@PathVariable String username) {
		return threadService.deleteAllThreadsOfUser(username);
	}
	
	@DeleteMapping("/threads/{thread_name}/users/{username}")
	public String deleteThreadOfUser(@PathVariable(value="thread_name") String threadName,
								   @PathVariable(value="username") String username) {
		return threadService.deleteThreadOfUser(username, threadName);
	}
}
