package com.democracy.social.services;

import com.democracy.social.Payload.PostRespDto;
import com.democracy.social.Payload.UserDto;
import com.democracy.social.entity.Post;
import com.democracy.social.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostService {

    public PostRespDto createPost(PostRespDto postDto);

    List<PostRespDto> getAllPost();

    String deletePost(Long id);


    Optional<Object> updatePost(Long id , PostRespDto postRespDto);


}
