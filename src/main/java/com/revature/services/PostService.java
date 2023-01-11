package com.revature.services;

import java.util.List;

import com.revature.annotations.Authorized;
import com.revature.models.PostType;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	private ProfanityService profanityService;
	private UserRepository userRepository;
  
	public PostService(PostRepository postRepository, ProfanityService profanityService, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.profanityService = profanityService;
		this.userRepository = userRepository;
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

	@Authorized
	public List<Post> getByUserID(int userID) {
		return postRepository.findAllByAuthor(
				userRepository.findById(userID).orElse(null)
		);
	}
}
