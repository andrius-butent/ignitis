package com.task.group.ignitis.service;

import com.task.group.ignitis.dao.BlogDAO;
import com.task.group.ignitis.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDAO blogDAO;

    @Override
    @Transactional
    public List<Blog> getBlogByUser(String email) {
        return blogDAO.getBlogByUser(email);
    }

    @Override
    @Transactional
    public void deleteBlogByID(Integer id) {
        blogDAO.deleteBlogByID(id);
    }

    @Override
    @Transactional
    public void addBlog(Blog blog) {
        blogDAO.addBlog(blog);
    }
}
