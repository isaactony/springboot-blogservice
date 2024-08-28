package com.springboot_blog_service.springbootblogservice.repository;

import com.springboot_blog_service.springbootblogservice.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByAuthor(String author);
}
