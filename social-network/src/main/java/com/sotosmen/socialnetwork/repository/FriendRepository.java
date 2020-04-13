package com.sotosmen.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.user.Friend;

public interface FriendRepository extends JpaRepository<Friend,Long> {
	Optional<Friend> findByFriendUser1AndFriendUser2(String username1,String username2);
	List<Friend> findByFriendUser1OrFriendUser2(String username1,String username2);
	void deleteByFriendUser1(String username);
	void deleteByFriendUser1AndFriendUser2(String username1,String username2);
	void deleteByFriendUser1OrFriendUser2(String username1,String username2);
}
