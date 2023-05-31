package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     // DROP TABLE시 필요한 어노테이션
public class BlogRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach     // * 공통 * 각 테스트 전에 실행할 코드 -> 테이블 생성 후 더미데이터 3개 넣음
    public void setBlogTable() {
        blogRepository.createBlogTable();
        blogRepository.insertTestData();
    }

    @Test
    public void findAllTest() {
        // given

        // when
        List<Blog> blogList = blogRepository.findAll();

        // then
        assertEquals(3, blogList.size());
    }

    @AfterEach      // * 공통 * 각 테스트 후에 실행할 코드 -> 테이블 초기화
    public void dropBlogTable() {
        blogRepository.dropBlogTable();
    }


}
