package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

    User findByUsername(String username);
}
