package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

// Entity는 불변성을 지키기 위해 Setter를 쓰지 않는다.
// 한 번 생성된 데이터가 변경될 가능성을 없앤다

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@Entity

public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long replyId;
    @Column(nullable = false)
    private long blogId;
    @Column(nullable = false)
    private String replyWriter;
    @Column(nullable = false)
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setDateTIme() {
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void setUpdateValue() {
        this.updatedAt = LocalDateTime.now();
    }

}
