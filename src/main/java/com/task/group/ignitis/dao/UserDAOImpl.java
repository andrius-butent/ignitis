package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    EntityManager entityManager;


    @Override
    public User findUserByUsername(String username) {

        Session session = getSession();

        Query<User> query = session.createQuery("FROM User WHERE username=:username", User.class);
        query.setParameter("username", username);

        List<User> users = query.getResultList();

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public Integer registerUser(User user) {

        Session session = getSession();

        User theUser = findUserByUsername(user.getUsername());

        if (theUser == null) {
            return (Integer) session.save(user);
        }

        return -1;
    }

    @Override
    public boolean loginUser(User user) {

        Session session = getSession();

        Query<User> query = session.createQuery("FROM User WHERE username=:username AND password=:password", User.class);
        query.setParameter("username", user.getUsername());
        query.setParameter("password", user.getPassword());

        List<User> users = query.getResultList();

        return !users.isEmpty();
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class).getSession();
    }
}
