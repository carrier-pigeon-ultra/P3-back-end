package com.revature.services;

import java.util.List;

import com.revature.models.PostType;
import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	private ProfanityService profanityService;
	
	public PostService(PostRepository postRepository, ProfanityService profanityService) {
		this.postRepository = postRepository;
		this.profanityService = profanityService;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public Post upsert(Post post) {
		post.setText(profanityService.censorString(post.getText()));
		return this.postRepository.save(post);
	}

	public List<Post> getAllTop() {
		return postRepository.findAllByPostType(PostType.Top);
	}
}
