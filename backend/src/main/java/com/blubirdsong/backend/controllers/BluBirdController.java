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

    @PostMapping("/reply/{id}")
    public ResponseEntity<String> createReply(@RequestBody Reply reply, @PathVariable String id, @RequestParam boolean isPost) {
        if (reply.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("Reply cannot have an empty body.");
        }
        Reply createdReply;
        try {
            // request param will always be required by default
            createdReply = isPost ? replyService.createReply(reply, Long.parseLong(id))
                                  : replyService.createReplyOfReply(reply, Long.parseLong(id));
        } catch (Exception e) {
            // we could not find the post/reply
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("A new reply with id: " + createdReply.getReplyId().toString() + " created successfully.");
    }

    @GetMapping("/reply/{id}")
    public ResponseEntity<List<Reply>> getAllReplies(@PathVariable String id, @RequestParam boolean isPost) {
        return ResponseEntity.ok(
                isPost ? replyService.getAllReplies(Long.parseLong(id))
                       : replyService.getAllRepliesOfReply(Long.parseLong(id)).stream().toList()
        );
    }

}
