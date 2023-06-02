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
public class BlogServiceTest {

    // blogService변수 안에 BlogService객체 넣어서 저장
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
    public void findByidTest() {
        // given
        // when
        Blog blog = blogService.findByid(2);
        // then
        assertEquals(2, blog.getBlogId());
        assertEquals("2번제목", blog.getBlogTitle());
        assertEquals("2번본문", blog.getBlogContent());
    }
    @Test
    @Transactional
    public void deleteByidTest() {
        // given
        // when
        blogService.deleteByid(2);
        // then
        assertNull(blogService.findByid(2));
    }
    @Test
    @Transactional
    public void saveTest() {
        // given
        int blogId = 4;
        String writer = "작가";
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
        assertEquals(4, blogService.findAll().size());
        assertEquals(writer, blogService.findAll().get(lastBlogIndex).getWriter());
        assertEquals(blogTitle, blogService.findAll().get(lastBlogIndex).getBlogTitle());
        assertEquals(blogContent, blogService.findAll().get(lastBlogIndex).getBlogContent());
    }
    @Test
    @Transactional
    public void updateTest() {
        // given
        long blogId = 2;

        String blogTitle = "수정제목";
        String blogContent = "수정콘텐츠";

        Blog blog = blogService.findByid(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);

        // when
        blogService.update(blog);
        // then
        assertEquals(blogTitle, blogService.findByid(blogId).getBlogTitle());
        assertEquals(blogContent, blogService.findByid(blogId).getBlogContent());
    }

}
