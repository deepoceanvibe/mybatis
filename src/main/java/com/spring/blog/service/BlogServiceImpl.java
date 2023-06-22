package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// Service 구현체는 blogRepository를 멤버변수로 가지고 있고, 호출한다.
// Controller -> Service -> blogRepository -> DB
@Service
public class BlogServiceImpl implements BlogService {

    BlogRepository blogRepository;
    ReplyRepository replyRepository;


    @Autowired     // 생성자 주입
    public BlogServiceImpl(BlogRepository blogRepository, ReplyRepository replyRepository) {
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }
    @Override
    public Blog findByid(long blogId) {
        return blogRepository.findByid(blogId);
    }

    @Transactional
    @Override   // 둘다 실행되던지, 둘다 안되던지
    public void deleteAllByBlogId(long blogId) {
        replyRepository.deleteAllByBlogId(blogId);
        blogRepository.deleteByid(blogId);
    }

    @Override
    public void deleteByid(long blogId) {
        blogRepository.deleteByid(blogId);
    }
    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        blogRepository.update(blog);
    }
}
