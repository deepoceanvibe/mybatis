package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class ReplyResponseDTO {

    private long replyId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    // DTO는 Entity 객체를 이용해서 생성하지만, 반대로 Entity는 DTO 내부를 알 필요가 없다.
    public ReplyResponseDTO(Reply reply) {
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
        this.publishedAt = reply.getPublishedAt();
        this.updatedAt = reply.getUpdatedAt();
    }
}
