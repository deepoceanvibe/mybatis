package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BlogRepositoryTest_practice {
    @Autowired
    BlogRepasitory_practice blogRepasitoryPractice;

    @BeforeEach
    public void setTable() {
        blogRepasitoryPractice.createTable();
        blogRepasitoryPractice.insertData();
    }


    @Test
    public void findAllTest() {
        // given
        long blogId = 1;
        // when
        List<Blog> blogList = blogRepasitoryPractice.findAll();
        // then
        assertEquals(3, blogList.size());

    }
    @Test
    public void findByIdTest() {
    }
    @Test
    public void saveTest() {
    }
    @Test
    public void deleteByIdTest() {
    }
    @Test
    public void updateTest() {
    }


    @AfterEach
    public void cleanTable() {
        blogRepasitoryPractice.dropTable();
    }
}
