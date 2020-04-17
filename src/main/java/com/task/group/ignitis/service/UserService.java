package com.task.group.ignitis.service;

import com.task.group.ignitis.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    Integer registerUser(User user);

    boolean loginUser(User user);
}
