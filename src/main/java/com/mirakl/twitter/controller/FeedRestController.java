package com.mirakl.twitter.controller;

import com.mirakl.twitter.domain.Post;
import com.mirakl.twitter.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedRestController {

    private final PostRepository postRepository;

    @Autowired
    public FeedRestController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Post> getPostsResource() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

}
