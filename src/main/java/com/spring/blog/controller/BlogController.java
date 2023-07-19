package com.spring.blog.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogIdException;
import com.spring.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.event.PaintEvent;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // 페이징 처리, 와 스프링하면서 처음으로 이해안된다
    @RequestMapping({"/list/{pageNum}", "/list"})

    // required = false : pageNum 정보를 안넘겨주면, null값으로 들어감

    public String list(@PathVariable(required = false) Long pageNum, Model model) {
        Page<Blog> pageInfo = blogService.findAll(pageNum);


        // 한 페이지에 보여야 하는 페이징 버튼 그룹의 개수
        final int PAGE_BTN_NUM = 10;

        // 현재 조회중인 페이지 번호 (0부터 세니까 1더함)
        int currentPageNum = pageInfo.getNumber() + 1;  // 현재 조회 중인 페이지에 강조하기 위해 필요

        // 현재 조회중인 페이지 그룹의 끝번호
        int endPageNum = (int) Math.ceil(currentPageNum / (double)PAGE_BTN_NUM) * PAGE_BTN_NUM;

        // 현재 조회중인 페이지 그룹의 시작번호
        int startPageNum = endPageNum - PAGE_BTN_NUM + 1;

        // 마지막 그룹 번호 보정
        endPageNum = endPageNum > pageInfo.getTotalPages() ? pageInfo.getTotalPages() : endPageNum;

        // prev(이전페이지) 버튼
        boolean prevBtn = startPageNum != 1;    // startPageNum이 1이 아님


        model.addAttribute("currentPageNum", currentPageNum);
        model.addAttribute("endPageNum", endPageNum);
        model.addAttribute("startPageNum", startPageNum);
        model.addAttribute("pageInfo", pageInfo);

        // /WEB-INF/views/ 이후에 올 경로 .jsp
        return "blog/list";
    }
    @RequestMapping(value = "/detail/{blogId}")
    public String detail(@PathVariable long blogId, Model model, Principal principal) {

        model.addAttribute("username", principal.getName());
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
    public String insert(Model model, Principal principal) {

        // SecurityContext, Principal은 둘 다 인증정보를 담고 있는 객체이다. 둘 중 편한거 쓰면됨

        model.addAttribute("userName", principal.getName());
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
