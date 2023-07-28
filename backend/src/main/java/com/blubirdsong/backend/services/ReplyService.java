package com.blubirdsong.backend.services;

import com.blubirdsong.backend.models.Reply;
import com.blubirdsong.backend.repositories.PostRepository;
import com.blubirdsong.backend.repositories.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public Reply createReply(Reply reply, Long postId) {
        reply.setPost(postRepository.findById(postId).orElseThrow());
        return replyRepository.save(reply);
    }

    public List<Reply> getAllReplies(Long id) {
        return replyRepository.findAllByPostId(id);
    }

    public Reply createReplyOfReply(Reply reply, Long parentReplyId) {
        Reply parent = replyRepository.findById(parentReplyId).orElseThrow();
        reply.setParentReplyId(parentReplyId);
        parent.getReplies().add(replyRepository.save(reply));
        return reply;
    }

    public Set<Reply> getAllRepliesOfReply(Long id) {
        return replyRepository.findAllByReplyId(id);
    }
}
