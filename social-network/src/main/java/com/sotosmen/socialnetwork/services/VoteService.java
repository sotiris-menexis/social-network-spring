package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.vote.RabbitMQReceiverVote;
import com.sotosmen.socialnetwork.amqp.vote.RabbitMQSenderVote;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.ThreadRepository;
import com.sotosmen.socialnetwork.repository.VoteRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.votes.Vote;

@Service
public class VoteService {
	@Autowired
	VoteRepository voteRepository;
	@Autowired
	ThreadRepository threadRepository;
	@Autowired
	RabbitMQSenderVote rabbitMQSender;
	@Autowired
	RabbitMQReceiverVote rabbitMQReceiver;
	
	public List<Vote> getVotesByThread(String threadname){
		if(voteRepository.count()!=0) {
			return voteRepository.findByThread(threadname);
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noVotesFound);
		}
	}
	
	public Vote createVote(Vote vote) {
		rabbitMQSender.sendToCreateVote(vote);
		return rabbitMQReceiver.receiveToCreateVote();
	}
	
	public String deleteVotesByThread(String threadName) {
		rabbitMQSender.sendToDeleteVote(threadName);
		return rabbitMQReceiver.receiveToDeleteVote();
	}
	
}
