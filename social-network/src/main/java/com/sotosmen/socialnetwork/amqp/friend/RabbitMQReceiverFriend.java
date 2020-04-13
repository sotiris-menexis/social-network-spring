package com.sotosmen.socialnetwork.amqp.friend;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.FriendRepository;
import com.sotosmen.socialnetwork.strings.Strings;
import com.sotosmen.socialnetwork.user.Friend;

@Service
public class RabbitMQReceiverFriend {
	@Autowired
	@Qualifier("rabbitTemplateFriend")
	RabbitTemplate rabbitTemplate;
	@Autowired
	FriendRepository friendRepository;
	@Value("${friend.rabbitmq.queuename.post}")
	String queuePost;
	@Value("${friend.rabbitmq.queuename.put}")
	String queuePut;
	@Value("${friend.rabbitmq.queuename.delete}")
	String queueDelete;
	
	public Friend receiveFriendToCreate() {
		Friend friend = (Friend) rabbitTemplate.receiveAndConvert(queuePost);
		if(friendRepository.count()!=0) {
			if(friendRepository.findByFriendUser1AndFriendUser2
					(friend.getFriendUser1(), friend.getFriendUser2()).isPresent() ||
					friendRepository.findByFriendUser1AndFriendUser2
					(friend.getFriendUser1(), friend.getFriendUser2()).isPresent()) {
				throw new ResourceException(HttpStatus.FORBIDDEN,Strings.exFriend);
			}else {
				friendRepository.save(friend);
				return friend;
			}
		}else {
			friendRepository.save(friend);
			return friend;
		}
	}
	@Transactional
	public String receiveFriendToDelete() {
		Friend friend = (Friend) rabbitTemplate.receiveAndConvert(queueDelete);
		if(friendRepository.count()!=0) {
			friendRepository
			.deleteByFriendUser1AndFriendUser2(friend.getFriendUser1(),friend.getFriendUser2());
			return Strings.deletionS;
		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noFriends);
		}
	}
	@Transactional
	public String receiveFriendsToDelete() {
		Friend friend = (Friend) rabbitTemplate.receiveAndConvert(queueDelete);
		if(friendRepository.count()!=0) {
			friendRepository
			.deleteByFriendUser1OrFriendUser2(friend.getFriendUser1(),friend.getFriendUser2());
			return Strings.deletionS;

		}else {
			throw new ResourceException(HttpStatus.NOT_FOUND,Strings.noFriends);
		}
	}
}
