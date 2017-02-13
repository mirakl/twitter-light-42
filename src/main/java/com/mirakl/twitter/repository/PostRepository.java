package com.mirakl.twitter.repository;

import com.mirakl.twitter.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findAllByOrderByCreationDateDesc();

}
