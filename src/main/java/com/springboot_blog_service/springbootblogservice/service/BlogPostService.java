package com.springboot_blog_service.springbootblogservice.service;

import com.springboot_blog_service.springbootblogservice.model.BlogPost;
import com.springboot_blog_service.springbootblogservice.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogRepository blogPostRepository;

    public List<BlogPost> findAll() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> findById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost save(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    public void deleteById(Long id) {
        blogPostRepository.deleteById(id);
    }

    public List<BlogPost> findByAuthor(String author) {
        return blogPostRepository.findByAuthor(author);
    }
}

