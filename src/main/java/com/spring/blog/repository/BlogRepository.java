package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {

    // 사전에 정의해야 하는 테스트용 코드
    void createBlogTable();     // CREATE TABLE 쿼리 실행
    void insertTestData();      // 3개 더미데이터 입력
    void dropBlogTable();       // 단위테스트 종료후 DB 초기화


    // 전체 데이터 조회
    List<Blog> findAll();

}
