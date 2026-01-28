package com.example.SecurityApp.SpringSecurityApp.service;

import com.example.SecurityApp.SpringSecurityApp.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    PostDTO createPost(PostDTO postDTO);
}
