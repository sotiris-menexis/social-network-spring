package com.sotosmen.socialnetwork.amqp.vote;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.VoteRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.votes.Vote;

@Service
public class RabbitMQReceiverVote {
	@Autowired
	@Qualifier("rabbitTemplateVote")
	RabbitTemplate rabbitTemplate;
	@Autowired
	VoteRepository voteRepository;
	@Value("${vote.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${vote.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${vote.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Vote receiveToCreateVote() {
		Vote vote = (Vote) rabbitTemplate.receiveAndConvert(queuePost);
		if(!voteRepository
			.findByThreadAndUser(vote.getThread(),vote.getUser()).isPresent()) {
			voteRepository.save(vote);
			return vote;
		}else {
			throw new ResourceException(HttpStatus.FORBIDDEN,Strings.exVote);
		}
	}
	
	public String receiveToDeleteVote() {
		String threadName = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		if(voteRepository.count()!=0) {
			voteRepository.deleteByThread(threadName);
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noVotesFound);
		}
	}
	
}
