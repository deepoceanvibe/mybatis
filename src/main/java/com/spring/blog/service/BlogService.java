package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BlogService {
    // 비즈니스 로직 정의

    // 전체 조회, 페이징 처리
//    Page<Blog> findAll(Long pageNum);
    Page<Blog> findAll(Long pageNum);
    // 개별 조회
    Blog findByid(long blogId);
    // 삭제
    void deleteByid(long blogId);
    // 저장
    void save(Blog blog);
    // 수정
    void update(Blog blog);
    // 전체댓삭제
    void deleteAllByBlogId(long blogId);

}
