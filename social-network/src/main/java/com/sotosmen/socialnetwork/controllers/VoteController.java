package com.sotosmen.socialnetwork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.services.VoteService;
import com.sotosmen.socialnetwork.votes.Vote;

@RestController
public class VoteController {
	@Autowired
	VoteService voteService;
	@Autowired
	ThreadRepository threadRepository;
	
	@GetMapping("/votes/threads/{threadName}")
	public List<Vote> getVotesByThread(@PathVariable String threadName) { 
		return voteService
				.getVotesByThread(threadName);
	}
	@PostMapping("/votes")
	public Vote createVote(@RequestBody Vote vote) {
		return voteService.createVote(vote);
	}
	@DeleteMapping("/votes/threads{threadName}")
	public String deleteVotesByThread(@PathVariable String threadName) {
		return voteService.deleteVotesByThread(threadName);
	}
}
