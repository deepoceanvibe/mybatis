package com.spring.blog.controller;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.exception.NotFoundReplyByReplyIdException;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .ok()               // 상태 코드 안에 body 데이터를 같이 넣어도 된다
                .body(AllReplies);
    }
    @RequestMapping(value = "/{replyId}", method = RequestMethod.GET)
    public ResponseEntity<?> findByReplyId(@PathVariable long replyId) {        // 예외가 터졌을 때 에러메세지를 리턴해줘야하므로, 리턴타입을 ?로 둔다

        ReplyFindByIdDTO replyFindByIdDTO = replyService.findByReplyId(replyId);

        // 예외
        if (replyFindByIdDTO == null) {
            try {
                throw new NotFoundReplyByReplyIdException("없는 댓글 번호를 조회했습니다.");
            } catch (NotFoundReplyByReplyIdException e) {
                return new ResponseEntity<>("찾는 번호가 없습니다.", HttpStatus.NOT_FOUND);
            }
        }

            // return new ResponseEntity<ReplyFindByIdDTO>(replyFindByIdDTO, HttpStatus.OK);
            return ResponseEntity
                    .ok(replyFindByIdDTO);

    }
    // @RequestBody : json으로 들어온 데이터를 -> 자바객체(에 저장)로 역직렬화 해주는 역할
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> insertReply(@RequestBody ReplyInsertDTO replyInsertDTO) {

        replyService.save(replyInsertDTO);

        return ResponseEntity
                .ok("댓글 등록 성공!");
    }
    @RequestMapping(value = "/{replyId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReply(@PathVariable long replyId) {

        replyService.deleteByReplyId(replyId);

        return ResponseEntity
                .ok("댓글 삭제가 완료되었습니다.");
    }



}
