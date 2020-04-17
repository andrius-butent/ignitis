package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.Blog;

import java.util.List;

public interface BlogDAO {

    List<Blog> getBlogByUser(String email);

    void deleteBlogByID(Integer id);

    void addBlog(Blog blog);
}
