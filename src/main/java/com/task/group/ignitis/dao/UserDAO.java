package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.User;

public interface UserDAO {

    User findUserByEmail(String email);

    Integer registerUser(User user);

    boolean loginUser(User user);
}
