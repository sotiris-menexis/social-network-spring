package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.thread.Thread;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

public interface ThreadRepository extends JpaRepository<Thread, ThreadCompositeKey>{
	List<Thread> findByIdThreadName(String threadName);
	List<Thread> findByIdUserId(String username);
	List<Thread> findByType(String type);
	void deleteByIdUserId(String username);
}
