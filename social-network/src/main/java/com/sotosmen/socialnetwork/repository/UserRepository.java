package com.sotosmen.socialnetwork.repository;

import com.sotosmen.socialnetwork.user.User;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User,String> {

}
