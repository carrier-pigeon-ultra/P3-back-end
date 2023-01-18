package com.revature.services;

import java.util.List;

import com.revature.annotations.Authorized;
import com.revature.models.PostType;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

	private PostRepository postRepository;
	private UserRepository userRepository;
	private ProfanityService profanityService;
	
	public PostService(PostRepository postRepository, UserRepository userRepository, ProfanityService profanityService) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.profanityService = profanityService;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	@Transactional
	public Post upsert(Post post) {
		profanityService.censorPostAndChildComments(post);
		return this.postRepository.save(post);
	}

	public List<Post> getAllTop() {
		return postRepository.findAllByPostType(PostType.Top);
	}


	public List<Post> getByUserID(int userID) {
		return postRepository.findAllByAuthor(
				userRepository.findById(userID).orElse(null)
		);
	}

	@Transactional
	public Post deletePostById(int postId) {

		postRepository.removeForeignKey(postId);
		postRepository.deleteById(postId);
		return null;
	}

}
