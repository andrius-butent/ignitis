package com.task.group.ignitis.service;

import com.task.group.ignitis.dao.UserDAO;
import com.task.group.ignitis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    @Transactional
    public boolean loginUser(User user) {
        return userDAO.loginUser(user);
    }

    @Override
    @Transactional
    public Integer registerUser(User user) {
        return userDAO.registerUser(user);
    }
}
