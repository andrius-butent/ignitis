package com.task.group.ignitis.controller;

import com.task.group.ignitis.entity.Blog;
import com.task.group.ignitis.security.SecurityValidator;
import com.task.group.ignitis.service.BlogService;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private SecurityValidator validator;



    @PostMapping(value = "/addBlog")
    public ResponseEntity test(@Valid @RequestBody Blog blog, Authentication auth, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.validatePayload(bindingResult));
        }

        blog.setUsername(auth.getName());

        blogService.saveBlog(blog);

        return ResponseEntity.status(HttpStatus.CREATED).body("User " + auth.getName() + " added a record to blog.");
    }

    @GetMapping(value = "/list")
    public List<Blog> getUserBlogList(Authentication auth) {
        return blogService.findAllByUsername(auth.getName());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteBlog(@PathVariable("id") Integer id) {
       blogService.deleteBlogById(id);

       return ResponseEntity.ok().body("Record is deleted.");
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity updateBlog(@PathVariable("id") Integer id, @RequestBody Blog blog, Authentication auth) {

        Blog blogFromDB = blogService.findByIdAndUsername(id, auth.getName());

        if (blogFromDB != null) {

            if (StringUtils.isNotEmpty(blog.getText())) {
                blogFromDB.setText(blog.getText());
            }

            if (StringUtils.isNotEmpty(blog.getTitle())) {
                blogFromDB.setTitle(blog.getTitle());
            }

            blogService.saveBlog(blogFromDB);

            return ResponseEntity.ok().body("Blog is updated.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such blog.");
    }
}
