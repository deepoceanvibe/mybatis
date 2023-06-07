package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BlogServiceTest_practice {

    @Autowired
    BlogService blogService;

    @Test
    @Transactional
    public void findAllTest() {
        // given
        // when
        List<Blog> blogList = blogService.findAll();

        // then
        assertEquals(3, blogList.size());
    }
    @Test
    @Transactional
    public void findByIdTest() {
        // given
        long blogId = 2;
        String blogTilte = "2번 제목";
        String blogContent = "2번 본문";

        Blog blog = Blog.builder()
                .blogId(blogId)
                        .blogTitle(blogTilte)
                                .blogContent(blogContent)
                                        .build();
        // when
        blogService.findByid(blogId);

        // then
        assertEquals(blogTilte, blogService.findByid(blogId).getBlogTitle());
        assertEquals(blogContent, blogService.findByid(blogId).getBlogContent());
    }
    @Test
    @Transactional
    public void deleteById() {
        // given
        long blogId = 2;
        // when
        blogService.deleteByid(blogId);
        // then
        assertNull(blogService.findByid(blogId));
    }
    @Test
    @Transactional
    public void save() {
        // given
        long blogId = 4;
        String writer = "글쓴이";
        String blogTitle = "제목";
        String blogContent = "본문";

        int lastBlogIndex = 3;

        Blog blog = Blog.builder()
                .blogId(blogId)
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        // when
        blogService.save(blog);

        // then

    }
    @Test
    @Transactional
    public void update() {
        // given

        // when

        // then
    }
}
