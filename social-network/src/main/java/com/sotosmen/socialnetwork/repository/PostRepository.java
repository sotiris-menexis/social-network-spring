package com.sotosmen.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sotosmen.socialnetwork.post.Post;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
	List<Post> findAllByOrderByTimestampDesc();
	List<Post> findByCreatorUser(String username);
	List<Post> findByOwnerThread(String threadName);
	List<Post> findByType(String type);
	Optional<Post> findByCreatorUserAndOwnerThread(String username, String threadName);
	void deleteByCreatorUser(String username);
	void deleteByOwnerThread(String threadName);
	@Modifying
	@Query("UPDATE Post p set p.type = :type where owner_thread_id = :threadName")
	void updatePostTypeByThread(String threadName, String type);
}
