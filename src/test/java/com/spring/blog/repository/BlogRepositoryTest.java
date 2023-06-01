package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.LocalTime.now;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("전체 행을 얻어오고 그중 1번 인덱스 행만 추출해 확인")
    public void findAllTest() {
        // given
        int blogId = 1;     // 인덱스는 0번부터라서 사람이 보기에 편하도록 blogId라는 변수명에 1을 넣어 밑에서 불러오자.

        // when
        List<Blog> blogList = blogRepository.findAll();

        // then
        assertEquals(3, blogList.size());
        assertEquals(2, blogList.get(blogId).getBlogId());       // 사람 기준 2번객체 id 2번
    }

    @Test
    public void findByidTest() {
        // given
        long id = 2;

        // when
        Blog blog = blogRepository.findByid(id);

        // then
        assertEquals("2번유저",blog.getWriter());
        assertEquals("2번제목",blog.getBlogTitle());
        assertEquals(2, blog.getBlogId());
    }

    @Test
    public void saveTest() {
        // given
        String writer = "작가명";
        String blogTitle = "타이틀";
        String blogContent = "본문";

        // 빌더 패턴으로 객체 생성하기. ( = setter) 대신 장점은 파라미터 순서 상관없이 막 쓸 수 있고 가독성 좋음
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        int blogId  = 3;

        // when
        blogRepository.save(blog);
        List<Blog> blogList = blogRepository.findAll();

        // then
        assertEquals(4, blogList.size());
        assertEquals(writer, blogList.get(blogId).getWriter());
        assertEquals(blogTitle, blogList.get(blogId).getBlogTitle());
        assertEquals(blogContent, blogList.get(blogId).getBlogContent());
    }

    @Test
    public void deleteByidTest() {
        // given
        long blogId = 3;

        // when
        blogRepository.deleteByid(blogId);

        // then
        assertEquals(2, blogRepository.findAll().size());
        assertNull(blogRepository.findByid(blogId));
    }

    @Test
    public void updateTest() throws InterruptedException {
        // given
        long id = 3;
        Blog blog = blogRepository.findByid(id);

        blog.setBlogTitle("제목");
        blog.setBlogContent("본문");

        // when
        blogRepository.update(blog);
        List<Blog> blogList = blogRepository.findAll();

        // then
        assertEquals("제목", blogList.get(2).getBlogTitle());
        assertEquals("본문", blogList.get(2).getBlogContent());
        assertNotEquals(blog.getUpdatedAt(), blogList.get(2).getUpdatedAt());
    }






    @AfterEach      // * 공통 * 각 테스트 후에 실행할 코드 -> 테이블 초기화
    public void dropBlogTable() {
        blogRepository.dropBlogTable();
    }


}
