package com.task.group.ignitis.service;

import com.task.group.ignitis.dao.UserDAO;
import com.task.group.ignitis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional
    public void saveUser(User user) {
        userDAO.save(user);
    }
}