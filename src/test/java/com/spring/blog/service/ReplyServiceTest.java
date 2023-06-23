package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    @Transactional
    public void findAllByBlogIdTest() {
        // given
        long blogId = 2;
        // when
        List<ReplyResponseDTO> replyList = replyService.findAllByBlogId(blogId);
        // then
        assertEquals(4, replyList.size());
    }

    @Test
    @Transactional
    public void findByReplyIdTest() {
        // given (내 코드 : 더미데이터를 지정해서 변수에 저장하지 않고 service메서드 호출을 통해 비교)
        long replyId = 5;

        // when
        replyService.findByReplyId(replyId);

        // then
        assertEquals("치즈", replyService.findByReplyId(replyId).getReplyWriter());
        assertEquals("퇴근하고싶어요", replyService.findByReplyId(replyId).getReplyContent());
    }
    @Test
    @Transactional
    public void findByReplyIdTest2() {
        // given (강사샘코드 : 빌더로 객체를 만들어서 db랑 비교)
        long replyId = 5;
        String replyWriter = "치즈";

        // when
        ReplyResponseDTO result = replyService.findByReplyId(replyId);

        // then
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyId, result.getReplyId());
    }

    @Test
    @Transactional
    public void deleteByReplyIdTest() {
        // given
        long replyId = 2;
        long blogId = 2;

        // when
        replyService.deleteByReplyId(replyId);

        // then
        assertEquals(3, replyService.findAllByBlogId(blogId).size());
        assertNull(replyService.findByReplyId(replyId));
    }

    @Test
    @Transactional
    public void save() {
        // given
        long blogId = 1;
        String replyWriter = "작성자";
        String replyContent = "댓글내용";

        ReplyCreateRequestDTO replyCreateRequestDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                        .replyWriter(replyWriter)
                                .replyContent(replyContent)
                                        .build();

        // when
        replyService.save(replyCreateRequestDTO);

        // then
        List<ReplyResponseDTO> resultList = replyService.findAllByBlogId(blogId);
        ReplyResponseDTO result = resultList.get(resultList.size() - 1);

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    public void update() {
        // given
        long replyId = 2;
        String replyWriter = "개소리하면짖는야옹이";
        String replyContent = "미야옹";

        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyId(replyId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyService.update(replyUpdateRequestDTO);

        // then
        ReplyResponseDTO updateResult = replyService.findByReplyId(replyId);

        assertNotEquals(updateResult.getUpdatedAt(), updateResult.getPublishedAt());
        assertEquals(replyWriter, updateResult.getReplyWriter());
        assertEquals(replyContent, updateResult.getReplyContent());
    }





}
