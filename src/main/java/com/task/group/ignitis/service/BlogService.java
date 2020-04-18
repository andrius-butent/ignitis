package com.task.group.ignitis.service;

import com.task.group.ignitis.dao.BlogDAO;
import com.task.group.ignitis.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogDAO blogDAO;

    @Transactional
    public void saveBlog(Blog blog) {
        blogDAO.save(blog);
    }

    @Transactional
    public List<Blog> findAllByUsername(String username) {
        return blogDAO.findAllByUsername(username);
    }

    @Transactional
    public void deleteBlogById(Integer id) {
        blogDAO.deleteById(id);
    }

    @Transactional
    public Blog findByIdAndUsername(Integer id, String username) {
        return blogDAO.findByIdAndUsername(id, username);
    }
}
