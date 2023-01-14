package com.revature.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.services.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000",
        "http://codepipeline-us-west-2-791209503483.s3-website-us-west-2.amazonaws.com",
        "http://carrier-pigeon-client-not-pipline.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getAllTopPosts() {
        return ResponseEntity.ok(this.postService.getAllTop());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<Post>> getUsersPosts(@PathVariable int user_id) {
        return ResponseEntity.ok(postService.getByUserID(user_id));
    }

    @Authorized
    @DeleteMapping("/{post_id}")
    public ResponseEntity deleteUsersPost(@PathVariable int post_id) {
        return ResponseEntity.ok(postService.deletePostById(post_id));
    }



}
