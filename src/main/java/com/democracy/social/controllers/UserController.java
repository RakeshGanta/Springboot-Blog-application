package com.democracy.social.controllers;

import com.democracy.social.Payload.UserDto;
import com.democracy.social.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
    }

    @GetMapping("users/{username}")
    public ResponseEntity<UserDto> findUserByUsername(@PathVariable(name = "username") String username){
        return new ResponseEntity<>(userService.findUserByUsername(username),HttpStatus.OK);
    }
}
