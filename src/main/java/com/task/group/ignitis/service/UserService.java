package com.task.group.ignitis.service;

import com.task.group.ignitis.entity.User;

public interface UserService {

    User findUserByUsername(String username);

    Integer registerUser(User user);

    boolean loginUser(User user);
}
