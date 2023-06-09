package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Blog {

    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private long blogCount;

}
