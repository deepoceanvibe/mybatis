package com.spring.blog.controller;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    private ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // REST 서버는 응답시 응답코드와 응답객체를 '같이' 넘기기 때문에 'ResponseEntity<자료형>' 을 리턴함
    // @PathVariable : {blogId} 파라미터에 2 적혀있으면 -> 생성자 파라미터에도 2로 보내준다는 뜻
    @RequestMapping(value = "/{blogId}/all", method = RequestMethod.GET)
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplies(@PathVariable long blogId) {

        List<ReplyFindByIdDTO> AllReplies = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok()
                .body(AllReplies);
    }



}
