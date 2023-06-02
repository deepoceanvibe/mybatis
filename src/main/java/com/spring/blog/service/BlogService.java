package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;

import java.util.List;


public interface BlogService {
    // 비즈니스 로직 정의

    // 전체 조회
    List<Blog> findAll();
    // 개별 조회
    Blog findByid(long blogId);
    // 삭제
    void deleteByid(long blogId);
    // 저장
    void save(Blog blog);
    // 수정
    void update(Blog blog);


}
