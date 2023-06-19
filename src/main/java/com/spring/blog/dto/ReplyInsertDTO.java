package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder @ToString
// 글 생성 DTO
public class ReplyInsertDTO {
    private long blogId;
    private String replyWriter;
    private String replyContent;

    // 엔터티를 DTO로 변환
    public ReplyInsertDTO(Reply reply) {
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
