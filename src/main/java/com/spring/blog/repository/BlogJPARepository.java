package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 엔터티, 주요식별자
// 제네릭에는 참조주소만 넣을수있어서 기본형자료못넣음, Long으로 해야됨
public interface BlogJPARepository extends JpaRepository<Blog, Long> {

    // 페이징 처리 : 페이징 정보를 받는 findAll '오버로딩'
    Page<Blog> findAll(Pageable pageable);
}
