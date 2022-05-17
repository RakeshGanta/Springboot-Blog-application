package com.democracy.social.controllers;

import com.democracy.social.Payload.PostRespDto;
import com.democracy.social.Payload.UserDto;
import com.democracy.social.entity.Post;
import com.democracy.social.entity.User;
import com.democracy.social.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("posts")
    public ResponseEntity<PostRespDto> createPost(@RequestBody PostRespDto post){
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostRespDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.deletePost(id),HttpStatus.OK);
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<Optional<Object>> updatePostById(@RequestBody PostRespDto post, @PathVariable(name="id") Long id){
        return new ResponseEntity<>(postService.updatePost(id,post),HttpStatus.OK);
    }



}

