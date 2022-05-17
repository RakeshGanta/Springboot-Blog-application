package com.democracy.social.services.impl;

import com.democracy.social.Payload.UserDto;
import com.democracy.social.entity.User;
import com.democracy.social.repository.UserRepository;
import com.democracy.social.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserDto userDto = mapToUserDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers(){
        List<User>users=userRepository.findAll();
        return users.stream().map(user -> mapToUserDto(user)).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private User mapToUserEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
