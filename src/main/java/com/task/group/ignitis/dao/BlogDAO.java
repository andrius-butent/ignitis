package com.task.group.ignitis.dao;

import com.task.group.ignitis.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogDAO extends JpaRepository<Blog, Integer> {

    List<Blog> findAllByUsername(String username);

    Blog findByIdAndUsername(Integer id, String username);

    void deleteByIdAndUsername(Integer id, String username);
}
