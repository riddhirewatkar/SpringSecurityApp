package com.example.SecurityApp.SpringSecurityApp.service;

import com.example.SecurityApp.SpringSecurityApp.dto.LoginDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.SignUpDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.UserDTO;
import com.example.SecurityApp.SpringSecurityApp.entity.UserEntity;
import com.example.SecurityApp.SpringSecurityApp.exception.ResourceNotFoundException;
import com.example.SecurityApp.SpringSecurityApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("User with mail" + email + " not found"));
    }

    public UserEntity getUserById(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with mail" + id + " not found"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new UsernameNotFoundException("User with mail" + signUpDTO.getEmail() + " already exists");
        }
        UserEntity userEntity = modelMapper.map(signUpDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
