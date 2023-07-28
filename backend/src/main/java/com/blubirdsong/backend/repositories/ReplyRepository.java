package com.blubirdsong.backend.repositories;

import com.blubirdsong.backend.models.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT reply FROM Reply as reply WHERE reply.post.id = :postId")
    List<Reply> findAllByPostId(Long postId);
}
