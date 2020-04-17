package com.task.group.ignitis.controller;

import com.task.group.ignitis.entity.Blog;
import com.task.group.ignitis.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping(value = "/getBlogs")
    public List<Blog> test(@RequestBody Map<String, String> blogInfo) {

        List<Blog> result = new ArrayList<>();

        if (blogInfo.containsKey("email")) {
            result = blogService.getBlogByUser(blogInfo.get("email"));

        }

        return result;
    }

    @PostMapping(value = "/delete/{id}")
    public void deleteBlog(@PathVariable("id") Integer id) {

        blogService.deleteBlogByID(id);
    }
}
