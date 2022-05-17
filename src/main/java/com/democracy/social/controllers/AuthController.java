package com.democracy.social.controllers;

import com.democracy.social.Payload.JWTAuthResponse;
import com.democracy.social.Payload.LoginDto;
import com.democracy.social.Payload.SignUpDto;
import com.democracy.social.Security.JwtTokenProvider;
import com.democracy.social.entity.Role;
import com.democracy.social.entity.User;
import com.democracy.social.exceptions.ResourceNotFoundException;
import com.democracy.social.repository.RoleRepository;
import com.democracy.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail()
                ,loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtTokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JWTAuthResponse(token, loginDto.getUsernameOrEmail()), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("username already taken",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("user already exists",HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setIsActive(Boolean.FALSE);
        Role roles=roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User signed up successfully",HttpStatus.OK);
    }


}
