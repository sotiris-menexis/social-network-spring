package com.sotosmen.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.user.FriendRequest;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
	List<FriendRequest> findByToUserId(String username);
	Optional<FriendRequest> findByToUserIdAndFromUserId(String username1,String username2);
	void deleteByToUserId(String username);
}
