package com.example.personalcoach.controller;

import com.example.personalcoach.model.ERole;
import com.example.personalcoach.model.Role;
import com.example.personalcoach.model.User;
import com.example.personalcoach.repository.RoleRepository;
import com.example.personalcoach.repository.UserRepository;
import com.example.personalcoach.service.UserDetailsImpl;
import com.example.personalcoach.service.UserDetailsServiceImpl;
import com.example.personalcoach.utils.JwtUtils;
import com.example.pojo.JwtResponse;
import com.example.pojo.LoginRequest;
import com.example.pojo.MessageResponse;
import com.example.pojo.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwt(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        try{

            if(userRepository.existsByUsername(signupRequest.getUsername())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is exist"));
            }
            if(userRepository.existsByEmail(signupRequest.getEmail())){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is exist"));
            }

            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    passwordEncoder.encode(signupRequest.getPassword())
            );

            Set<String> reqRoles = signupRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (reqRoles == null) {
                Role employeeRole = roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException
                                ("Error: Role is not found."));
                roles.add(employeeRole);
            } else {
                reqRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository
                                    .findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException
                                            ("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException
                                            ("Error: Role is not found."));
                            roles.add(modRole);
                        default:
                            Role defaultRole = roleRepository
                                    .findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException
                                            ("Error: Role is not found."));
                            roles.add(defaultRole);
                    }
                });
            }

            user.setRoles(roles);

            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity("Непредвиденная ошибка" ,HttpStatus.BAD_REQUEST);
    }
}
