package com.task.group.ignitis.service;

import com.task.group.ignitis.entity.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> getBlogByUser(String email);

    void deleteBlogByID(Integer id);

    void addBlog(Blog blog);
}
