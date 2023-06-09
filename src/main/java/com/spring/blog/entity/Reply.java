package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDateTime;

// Entity는 불변성을 지키기 위해 Setter를 쓰지 않는다.
// 한 번 생성된 데이터가 변경될 가능성을 없앤다

@Getter @NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class Reply {
    private long replyId;
    private long blogId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}
