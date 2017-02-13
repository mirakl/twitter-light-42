package com.mirakl.twitter.controller;

import com.mirakl.twitter.command.CreatePostCommand;
import com.mirakl.twitter.domain.Post;
import com.mirakl.twitter.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FeedWebController {

    private final PostRepository postRepository;

    @Autowired
    public FeedWebController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/post";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String listPosts(ModelMap model) {
        List<Post> posts = postRepository.findAllByOrderByCreationDateDesc();
        model.addAttribute("posts", posts);
        model.addAttribute("username", getLoggedUsername());
        model.computeIfAbsent("createPostCommand", key -> new CreatePostCommand());

        return "/feed";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String createPost(@Valid CreatePostCommand createPostCommand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "createPostCommand", bindingResult);
            redirectAttributes.addFlashAttribute("createPostCommand", createPostCommand);
        } else {
            postRepository.save(new Post(createPostCommand.getContent(), getLoggedUsername()));
        }
        return "redirect:/post";
    }

    @RequestMapping(value = "/post/delete", method = RequestMethod.GET)
    public String deletePost(long postId) {
        Post post = postRepository.findOne(postId);
        if (post != null && post.getAuthor().equals(getLoggedUsername())) {
            postRepository.delete(post);
        }
        return "redirect:/post";
    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
