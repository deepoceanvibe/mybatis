package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import com.spring.blog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyJPARepository extends JpaRepository<Reply, Long> {

    // blogId를 기준으로 전체 댓글을 얻어오기
    List<Reply> findAllByBlogId(long blogId);

    // blogId를 기준으로 그 댓글을 전부 삭제
    void deleteAllByBlogId(long blogId);

}
