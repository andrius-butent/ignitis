package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.Blog;
import com.task.group.ignitis.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BlogDAOImpl implements BlogDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Blog> getBlogByUser(String email) {

        Session session = getSession();

        Query<Blog> query = session.createQuery("FROM Blog WHERE email=:email AND email=:email", Blog.class);
        query.setParameter("email", email);

        return query.getResultList();
    }

    @Override
    public void deleteBlogByID(Integer id) {

        Session session = getSession();

        Query q = session.createQuery("DELETE Blog WHERE id=:id").setParameter("id", id);
        q.executeUpdate();
    }

    @Override
    public void addBlog(Blog blog) {

    }

    private Session getSession() {
        return entityManager.unwrap(Session.class).getSession();
    }
}
