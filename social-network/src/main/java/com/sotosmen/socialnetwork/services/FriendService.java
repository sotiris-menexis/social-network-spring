package com.sotosmen.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.amqp.friend.RabbitMQReceiverFriend;
import com.sotosmen.socialnetwork.amqp.friend.RabbitMQSenderFriend;
import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.FriendRepository;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.Friend;

@Service
public class FriendService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	FriendRepository friendRepository;
	@Autowired
	RabbitMQSenderFriend rabbitMQSender;
	@Autowired
	RabbitMQReceiverFriend rabbitMQReceiver;
	
	public List<Friend> findFriends(String username) {
		if(userRepository.count()!=0) {
			if(userRepository.findById(username).isPresent()) {
				return friendRepository.findByFriendUser1OrFriendUser2(username,username);
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	public Friend createFriend(Friend friend) {
		rabbitMQSender.sendFriendToCreate(friend);
		return rabbitMQReceiver.receiveFriendToCreate();
	}
	public List<Friend> findAllFriendsOfUser(String username){
		if(userRepository.count()!=0) {
			if(userRepository.findById(username).isPresent()) {
				return friendRepository
						.findByFriendUser1OrFriendUser2(username,username);
			}else {
				throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUser);
			}
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noUsers);
		}
	}
	
	public String deleteFriendOfUser(String username1,String username2) {
		Friend friend = new Friend();
		friend.setFriendUser1(username1);
		friend.setFriendUser2(username2);
		rabbitMQSender.sendFriendToDelete(friend);
		return rabbitMQReceiver.receiveFriendToDelete();
	}
	
	public String deleteAllFriendsOfUser(String username) {
		Friend friend = new Friend();
		friend.setFriendUser1(username);
		friend.setFriendUser2(username);
		rabbitMQSender.sendFriendToDelete(friend);
		return rabbitMQReceiver.receiveFriendsToDelete();
	}
}
