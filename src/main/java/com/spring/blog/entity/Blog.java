package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder
@Entity     // CREATE TABLE
@DynamicInsert      // null인 필드값에 기본값 지정된 요소를 집어넣어줌
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId;

    @Column(nullable = false)   // not null
    private String writer;
    @Column(nullable = false)
    private String blogTitle;
    @Column(nullable = false)
    private String blogContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    @ColumnDefault("0")     // 조회수 기본값 0으로 세팅
    private Long blogCount;     // Wrapper Long형 사용, 기본형변수는 null을 가질수없기 때문

    // insert 되기 전 실행할 로직
    @PrePersist
    public void setDefaultCount() {
        this.blogCount = this.blogCount == null ? 0 : this.blogCount;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    // update 되기 전 실행할 로직
    @PreUpdate
    public void setUpdateValue() {
        this.updatedAt = LocalDateTime.now();
    }

}
