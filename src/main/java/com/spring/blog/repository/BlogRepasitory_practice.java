package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepasitory_practice {
    // 테스트 세팅용 코드
    void createTable();
    void insertData();
    void dropTable();

    // 기능 추상화
    List<Blog> findAll();
    Blog findById(long blogId);
    void save(Blog blog);
    void deleteById(long blogId);
    void update(Blog blog);

}
