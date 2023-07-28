package com.blubirdsong.backend.controllers;

import com.blubirdsong.backend.models.Post;
import com.blubirdsong.backend.models.Reply;
import com.blubirdsong.backend.services.PostService;
import com.blubirdsong.backend.services.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BluBirdController {

    private final PostService postService;
    private final ReplyService replyService;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        if (post.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("Posts cannot have an empty body.");
        }
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok("A new post with id: " + createdPost.getId().toString() + " created successfully.");
    }

    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @PostMapping("/reply/{postId}")
    public ResponseEntity<String> createReply(@RequestBody Reply reply, @PathVariable String postId) {
        if (reply.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("Reply cannot have an empty body.");
        }
        Reply createdReply;
        try {
            createdReply = replyService.createReply(reply, Long.parseLong(postId));
        } catch (Exception e) {
            // we could not find the post
            return ResponseEntity.badRequest().body("Specified post with id: " + postId + " was not found.");
        }
        return ResponseEntity.ok("A new reply with id: " + createdReply.getReplyId().toString() + " created successfully.");
    }

    @GetMapping("/reply/{postId}")
    public ResponseEntity<List<Reply>> getAllReplies(@PathVariable String postId) {
        return ResponseEntity.ok(replyService.getAllReplies(Long.parseLong(postId)));
    }

}
