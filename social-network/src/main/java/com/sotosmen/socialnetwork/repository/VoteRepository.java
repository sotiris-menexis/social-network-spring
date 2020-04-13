package com.sotosmen.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.votes.Vote;


@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
	List<Vote> findByThread(String threadName);
	Optional<Vote> findByThreadAndUser(String threadName, String username);
	void deleteByThread(String threadName);
}
