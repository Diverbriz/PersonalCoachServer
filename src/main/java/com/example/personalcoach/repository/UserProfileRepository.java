package com.example.personalcoach.repository;

import com.example.personalcoach.model.User;
import com.example.personalcoach.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

}
