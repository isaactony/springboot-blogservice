package com.springboot_blog_service.springbootblogservice.controller;

import com.springboot_blog_service.springbootblogservice.dto.BlogPostDto;
import com.springboot_blog_service.springbootblogservice.model.BlogPost;
import com.springboot_blog_service.springbootblogservice.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        Optional<BlogPost> post = blogPostService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPostDto blogPostDto, Authentication authentication) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setAuthor(authentication.getName());

        return ResponseEntity.ok(blogPostService.save(blogPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPostDto blogPostDto, Authentication authentication) {
        Optional<BlogPost> existingPost = blogPostService.findById(id);

        if (existingPost.isPresent()) {
            BlogPost blogPost = existingPost.get();

            if (!blogPost.getAuthor().equals(authentication.getName())) {
                return ResponseEntity.status(403).build();
            }

            blogPost.setTitle(blogPostDto.getTitle());
            blogPost.setContent(blogPostDto.getContent());

            return ResponseEntity.ok(blogPostService.save(blogPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        Optional<BlogPost> existingPost = blogPostService.findById(id);

        if (existingPost.isPresent()) {
            BlogPost blogPost = existingPost.get();

            if (!blogPost.getAuthor().equals(authentication.getName())) {
                return ResponseEntity.status(403).build();
            }

            blogPostService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
