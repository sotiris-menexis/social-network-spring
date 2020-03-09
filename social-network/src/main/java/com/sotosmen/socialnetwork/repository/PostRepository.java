package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.post.PostCompositeKey;
import com.sotosmen.socialnetwork.thread.ThreadCompositeKey;

public interface PostRepository extends JpaRepository<Post,PostCompositeKey> {
	List<Post> findByIdUserIdP(String username);
	List<Post> findByIdThreadId(ThreadCompositeKey id);
}
