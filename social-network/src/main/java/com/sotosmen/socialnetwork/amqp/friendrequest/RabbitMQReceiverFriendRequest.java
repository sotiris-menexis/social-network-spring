package com.sotosmen.socialnetwork.amqp.friendrequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.FriendRequestRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.FriendRequest;

@Service
public class RabbitMQReceiverFriendRequest {
	@Autowired
	@Qualifier("rabbitTemplateFriendRequest")
	RabbitTemplate rabbitTemplate;
	@Autowired
	FriendRequestRepository friendRequestRepository;
	@Value("${friendrequest.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${friendrequest.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${friendrequest.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public FriendRequest receiveToCreateFriendRequest() {
		FriendRequest request = (FriendRequest) rabbitTemplate.receiveAndConvert(queuePost);
		if(friendRequestRepository.
		findByToUserIdAndFromUserId(request.getToUserId(),request.getFromUserId()).isPresent() ||
		friendRequestRepository.
		findByToUserIdAndFromUserId(request.getFromUserId(), request.getToUserId()).isPresent()) {
			throw new ResourceException(HttpStatus.FORBIDDEN,Strings.exFriendRequest);
		}else {
			friendRequestRepository.save(request);
			return request;
		}
	}
	
	public FriendRequest receiveToUpdateFriendRequest() {
		FriendRequest request = (FriendRequest) rabbitTemplate.receiveAndConvert(queuePut);
		if(friendRequestRepository.
		findByToUserIdAndFromUserId(request.getToUserId(),request.getFromUserId()).isPresent()) {
			friendRequestRepository.save(request);
			return request;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noFriendRequestsUser);
		}
	}
	public String receiveToDeleteFriendRequest() {
		String friendRequestId = (String) rabbitTemplate.receiveAndConvert(queueDelete);
		if(friendRequestRepository.findById(Long.valueOf(friendRequestId)).isPresent()) {
			friendRequestRepository.deleteById(Long.valueOf(friendRequestId));
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noFriendRequestsUser);
		}
	}
	
}
