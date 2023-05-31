package com.spring.blog.entity;

import lombok.*;

import java.sql.Date;
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder   // 빌더 패턴 생성자를 쓸 수 있게 해줌
public class Blog {

    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;
    private Date publishedAt;
    private Date updatedAt;
    private long blogCount;
}
