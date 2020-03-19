package com.sotosmen.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sotosmen.socialnetwork.user.User;

public interface UserRepository extends JpaRepository<User,String> {
	List<User> findByType(String type);
}
