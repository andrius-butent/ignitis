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
    public User findUserByEmail(String email) {

        Session session = getSession();

        Query<User> query = session.createQuery("FROM User WHERE email=:email", User.class);
        query.setParameter("email", email);

        List<User> users = query.getResultList();

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public Integer registerUser(User user) {

        Session session = getSession();

        User theUser = findUserByEmail(user.getEmail());

        if (theUser == null) {
            return (Integer) session.save(user);
        }

        return -1;
    }

    @Override
    public boolean loginUser(User user) {

        Session session = getSession();

        Query<User> query = session.createQuery("FROM User WHERE email=:email AND password=:password", User.class);
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());

        List<User> users = query.getResultList();

        return !users.isEmpty();
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class).getSession();
    }
}
