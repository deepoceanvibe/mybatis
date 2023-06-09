package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번 글의 댓글 개수가 4개인지 확인")
    public void findAllByBlogIdTest() {
        // given
        long blogId = 2;

        // when
        List<ReplyFindByIdDTO> replyList = replyRepository.findAllByBlogId(blogId);

        // then
        assertEquals(4, replyList.size());
    }

    @Test
    @Transactional
    public void findByReplyIdTest() {
        // given
        long replyId = 3;

        // when
        ReplyFindByIdDTO replyFindByIdDTO = replyRepository.findByReplyId(replyId);

        // then
        assertEquals("나무", replyFindByIdDTO.getReplyWriter());
        assertEquals(3, replyFindByIdDTO.getReplyId());
    }

}
