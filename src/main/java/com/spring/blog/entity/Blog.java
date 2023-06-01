package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Blog {

    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;
    private Date publishedAt;
    private Date updatedAt;
    private long blogCount;

}
