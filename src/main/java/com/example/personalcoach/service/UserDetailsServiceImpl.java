package com.example.personalcoach.service;

import com.example.personalcoach.model.User;
import com.example.personalcoach.model.UserProfile;
import com.example.personalcoach.repository.UserProfileRepository;
import com.example.personalcoach.repository.UserRepository;
import com.example.pojo.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public ResponseEntity<?> createUser(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        userRepository.save(user);
        userProfileRepository.save(new UserProfile(user));

        return ResponseEntity.ok(new MessageResponse
                ("Employee registered successfully!" +
                        "password " + user.getPassword()));
    }


    public UserProfile getUserProfileByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserProfile userProfile = userProfileRepository.findByUser(user).
                orElseThrow(() -> new UsernameNotFoundException("UserProfile not found with username: " + username));
        return userProfile;
    }

}
