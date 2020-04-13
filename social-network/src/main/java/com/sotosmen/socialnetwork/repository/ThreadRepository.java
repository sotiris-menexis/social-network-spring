package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.thread.Thread;



@Repository
public interface ThreadRepository extends JpaRepository<Thread, String>{
	List<Thread> findByCreatorUser(String username);
	List<Thread> findByType(String type);
	void deleteByCreatorUser(String username);
}
