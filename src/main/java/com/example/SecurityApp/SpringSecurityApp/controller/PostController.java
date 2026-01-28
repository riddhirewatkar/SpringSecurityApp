package com.example.SecurityApp.SpringSecurityApp.controller;

import com.example.SecurityApp.SpringSecurityApp.dto.PostDTO;
import com.example.SecurityApp.SpringSecurityApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable("postId") Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.createPost(postDTO);
    }
}
