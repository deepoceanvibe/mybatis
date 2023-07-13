package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

// Service 구현체는 blogRepository를 멤버변수로 가지고 있고, 호출한다.
// Controller -> Service -> blogRepository -> DB
@Service
public class BlogServiceImpl implements BlogService {

    BlogRepository blogRepository;
    ReplyRepository replyRepository;
    BlogJPARepository blogJPARepository;
    ReplyJPARepository replyJPARepository;


    @Autowired     // 생성자 주입
    public BlogServiceImpl(BlogRepository blogRepository,
                           ReplyRepository replyRepository,
                           BlogJPARepository blogJPARepository,
                           ReplyJPARepository replyJPARepository) {
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
        this.blogJPARepository = blogJPARepository;
        this.replyJPARepository = replyJPARepository;
    }

    // 페이징 처리
    @Override
    public Page<Blog> findAll(Long pageNum) {

        // 페이징 처리 먼저 객체로 생성 (2페이지부터, 글을 10개씩 끊어서 보여줘라)  // 3번페이지 첫글번호:32
        Pageable pageable = PageRequest.of(getCalibratedPageNum(pageNum)-1, 10);

        // 생성된 페이징 정보를 파라미터로 제공해서 findAll 호출
        return blogJPARepository.findAll(pageable);
    }

    @Override
    public Blog findByid(long blogId) {
        // JPA의 findById는 Optional을 리턴하므로, 일반 객체로 만들기 위해 .get()을 사용한다
        return blogJPARepository.findById(blogId).get();
    }

    @Override
    @Transactional // 둘다 실행되던지, 둘다 안되던지
    public void deleteAllByBlogId(long blogId) {
        // 선댓삭후, 글삭제
        replyJPARepository.deleteAllByBlogId(blogId);
        blogRepository.deleteByid(blogId);   // JPA로 변환안됨 ???
    }

    @Override
    public void deleteByid(long blogId) {
        blogJPARepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
        blogJPARepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        // JPA의 수정은, 1)findById로 얻어와서 2)update 해서 3)save 해야함

        Blog updatedBlog = blogJPARepository.findById(blog.getBlogId()).get();

        updatedBlog.setBlogTitle(blog.getBlogTitle());
        updatedBlog.setBlogContent(blog.getBlogContent());

        blogJPARepository.save(updatedBlog);
    }


    // pageNum 보정
    public int getCalibratedPageNum(Long pageNum) {
        // 사용자가 아무것도 안 넣은 경우
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1L;
        } else {
            // 총 페이지 개수를 구하는 로직
            int totalPagesCount = (int) Math.ceil(blogJPARepository.count() / 10.0);
            pageNum = pageNum > totalPagesCount ? totalPagesCount : pageNum;
        }
        return pageNum.intValue();
    }
}
