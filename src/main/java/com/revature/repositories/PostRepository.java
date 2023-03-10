package com.revature.repositories;

import com.revature.models.PostType;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findAllByPostType(PostType postType);

    List<Post> findAllByAuthor(User author);

    @Modifying
    @Query(value = "DELETE FROM posts_comments WHERE posts_comments.comments_id= :id", nativeQuery = true)
    Integer removeForeignKey(@Param("id") int id);




}
