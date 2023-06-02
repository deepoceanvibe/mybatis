package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogservice;

    @Autowired      // 이거 이해 못함 ???
    public BlogController(BlogService blogService) {
        this.blogservice = blogService;
    }

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        List<Blog> blogList = blogservice.findAll();
        model.addAttribute("blogList", blogList);

        // /WEB-INF/views/ 이후에 올 경로 .jsp
        return "blog/list";
    }


}
