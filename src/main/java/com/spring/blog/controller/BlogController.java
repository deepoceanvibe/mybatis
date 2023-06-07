package com.spring.blog.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogIdException;
import com.spring.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(value="/list")
    public String list(Model model) {
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);

        // /WEB-INF/views/ 이후에 올 경로 .jsp
        return "blog/list";
    }
    @RequestMapping(value = "/detail/{blogId}")
    public String detail(@PathVariable long blogId, Model model) {
        Blog blog = blogService.findByid(blogId);
        if(blog == null) {
            try {
                throw new NotFoundBlogIdException("없는 아이디로 조회했습니다.");
            } catch(NotFoundBlogIdException e) {       // 현업에서는 e 안씀
                return "blog/NotFoundBlogIdExceptionResultPage";
            }
        }
        model.addAttribute("blog", blog);
        // 리팩토링 -> model.addAttribute("blog", blogService.findByid(blogId));

        return "blog/detail";
    }
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert() {
        return "blog/form";
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(Blog blog) {
        blogService.save(blog);
        return "redirect:/blog/list";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(long blogId) {
        blogService.deleteByid(blogId);
        return "redirect:/blog/list";
    }
    @RequestMapping(value = "/update-form", method = RequestMethod.POST)
    public String update(long blogId, Model model) {
        Blog blog = blogService.findByid(blogId);
        model.addAttribute("blog", blog);
        return "blog/update-form";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Blog blog) {
        blogService.update(blog);

        return "redirect:/blog/detail/" + blog.getBlogId();
    }

}
