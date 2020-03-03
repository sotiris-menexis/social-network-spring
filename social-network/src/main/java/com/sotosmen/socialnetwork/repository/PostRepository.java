package com.sotosmen.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.post.Post;
import com.sotosmen.socialnetwork.post.PostCompositeKey;

public interface PostRepository extends JpaRepository<Post,PostCompositeKey> {
	
}
