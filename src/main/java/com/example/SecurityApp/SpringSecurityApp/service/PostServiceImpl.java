package com.example.SecurityApp.SpringSecurityApp.service;


import com.example.SecurityApp.SpringSecurityApp.dto.PostDTO;
import com.example.SecurityApp.SpringSecurityApp.entity.PostEntity;
import com.example.SecurityApp.SpringSecurityApp.entity.UserEntity;
import com.example.SecurityApp.SpringSecurityApp.exception.ResourceNotFoundException;
import com.example.SecurityApp.SpringSecurityApp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Getting post by id {}", user);
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post Id not found"));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }
}
