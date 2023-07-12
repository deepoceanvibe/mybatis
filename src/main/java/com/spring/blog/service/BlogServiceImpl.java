package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Blog> findAll() {
        // mybatis용 return blogRepository.findAll();
        return blogJPARepository.findAll();
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
        replyRepository.deleteAllByBlogId(blogId);
        blogRepository.deleteByid(blogId);
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
}
