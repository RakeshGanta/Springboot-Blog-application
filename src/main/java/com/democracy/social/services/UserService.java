package com.democracy.social.services;

import com.democracy.social.Payload.PostRespDto;
import com.democracy.social.Payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
