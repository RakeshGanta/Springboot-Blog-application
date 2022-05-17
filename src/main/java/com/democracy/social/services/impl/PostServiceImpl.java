package com.democracy.social.services.impl;

import com.democracy.social.Payload.PostRespDto;
import com.democracy.social.Payload.UserDto;
import com.democracy.social.entity.Post;
import com.democracy.social.entity.User;
import com.democracy.social.exceptions.ResourceNotFoundException;
import com.democracy.social.repository.PostRepository;
import com.democracy.social.repository.UserRepository;
import com.democracy.social.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,UserRepository userRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostRespDto createPost(PostRespDto postRespDto) {
        User user=getUserFromAuth();
        Post post=mapToEntity(postRespDto);
        post.setUser(user);
        post.setPostedBy(user.getUsername());
        Post newPost=postRepository.save(post);
        PostRespDto postRespDto1=mapToDto(newPost);
        return postRespDto1;

    }

    @Override
    public List<PostRespDto> getAllPost() {
        List<Post> posts=postRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public String deletePost(Long id) {
        User user=getUserFromAuth();
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
        if(post.getUser().getUsername()==user.getUsername() || post.getUser().getUsername()==user.getEmail()){
            postRepository.deleteById(id);
        }
        else{
            return "Post doesn't belongs to you";
        }
        return "Deleted Successfully";
    }

    @Override
    public Optional<Object> updatePost(Long id, PostRespDto postRespDto) {
        User user=getUserFromAuth();
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
        if(post.getUser().getUsername()==user.getUsername() || post.getUser().getUsername()==user.getEmail()){
            post.setTitle(postRespDto.getTitle());
            post.setContent(postRespDto.getContent());
            Post updatedPost=postRepository.save(post);
            PostRespDto postRespDto1=mapToDto(updatedPost);
            return Optional.ofNullable(postRespDto1);
        }
        String msg="Post  does not belongs to you";
        return Optional.of(msg);
    }


    private PostRespDto mapToDto(Post newPost){
        PostRespDto postRespDto=modelMapper.map(newPost,PostRespDto.class);
        /*postRespDto.setId(newPost.getId());
        postRespDto.setTitle(newPost.getTitle());*/
        return postRespDto;
    }
    private Post mapToEntity(PostRespDto postRespDto){
        Post post=modelMapper.map(postRespDto,Post.class);
        /*post.setId(postRespDto.getId());
        post.setTitle(postRespDto.getTitle());
        post.setContent(postRespDto.getContent());*/
        return post;
    }

    private User getUserFromAuth(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username:"+username));
        return user;
    }


}

