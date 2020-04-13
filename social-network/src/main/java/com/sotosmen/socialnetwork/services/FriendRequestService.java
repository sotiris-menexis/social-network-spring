package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.friendrequest.RabbitMQReceiverFriendRequest;
import com.sotosmen.socialnetwork.amqp.friendrequest.RabbitMQSenderFriendRequest;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.FriendRequestRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.FriendRequest;

@Service
public class FriendRequestService {
	@Autowired
	FriendRequestRepository friendRequestRepository;
	@Autowired
	RabbitMQSenderFriendRequest rabbitMQSender;
	@Autowired
	RabbitMQReceiverFriendRequest rabbitMQReceiver;
	
	public List<FriendRequest> getForUserFriendRequests(String username){
		if(friendRequestRepository.count()!=0) {
			return friendRequestRepository.findByToUserId(username);
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noFriendRequests);
		}
	}
	
	public FriendRequest createFriendRequest(FriendRequest friendRequest) {
		rabbitMQSender.sendToCreateFriendRequest(friendRequest);
		return rabbitMQReceiver.receiveToCreateFriendRequest();
	}
	
	public FriendRequest updateFriendRequest(FriendRequest friendRequest) {
		rabbitMQSender.sendToUpdateFriendRequest(friendRequest);
		return rabbitMQReceiver.receiveToUpdateFriendRequest();
	}
	
	public String deleteFriendRequest(String friendRequestId) {
		rabbitMQSender.sendToDeleteFriendRequest(friendRequestId);
		return rabbitMQReceiver.receiveToDeleteFriendRequest();
	}
}
