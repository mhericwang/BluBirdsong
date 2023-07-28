package com.blubirdsong.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id")
    private Long replyId;

    @NonNull
    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "parent_reply_id")
    private Long parentReplyId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_reply_id")
    private Set<Reply> replies;

}
