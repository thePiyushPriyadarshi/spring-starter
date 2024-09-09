package com.thePiyushPriyadarshi.springStarter.services;

import com.thePiyushPriyadarshi.springStarter.dto.UserDto;
import com.thePiyushPriyadarshi.springStarter.entities.User;
import com.thePiyushPriyadarshi.springStarter.exceptions.ResourceNotFoundException;
import com.thePiyushPriyadarshi.springStarter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository.findByEmail(username)
                .orElseThrow(()->
                new BadCredentialsException("User with email "+username+" not found"));
    }


    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User with id "+id+" not found"));
    }
}
