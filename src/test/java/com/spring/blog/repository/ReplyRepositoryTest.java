package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import com.spring.blog.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Transactional
    @DisplayName("댓글 번호 2번을 삭제한 다음, 전체 데이터양이 4개이고, 2번은 null이다.")
    public void deleteByReplyIdTest() {
        // given
        long replyId = 2;
        long blogId = 2;

        // when
        replyRepository.deleteByReplyId(replyId);

        // then
        assertEquals(3, replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    public void save() {
        // given
        long replyId = 5;
        long blogId = 1;
        String replyWriter = "작성자";
        String replyContent = "댓글내용";
        int lastBlogIndex = 0;

        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyRepository.save(replyInsertDTO);

        // then
        assertEquals(replyWriter, replyRepository.findAllByBlogId(blogId).get(lastBlogIndex).getReplyWriter());
        assertEquals(replyContent, replyRepository.findAllByBlogId(blogId).get(lastBlogIndex).getReplyContent());
    }
    @Test
    @Transactional
    @DisplayName("강사샘코드")
    public void save2() {
        // given
        long replyId = 5;
        long blogId = 1;
        String replyWriter = "작성자";
        String replyContent = "댓글내용";

        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyRepository.save(replyInsertDTO);

        // then

        // blogId번글의 전체 댓글을 가지고 온 다음 마지막 인덱스 요소만 변수에 저장 후,
        // getter를 이용해 위에서 넣은 * fixture와 일치하는지 검증!!! *
        // blogId에 해당하는 댓글 목록을 검색하고, 그 중에서 가장 최근(마지막) 댓글을 찾기

        // fixture 비교를 굳이 하는 이유 : 한글로 들어갔는지, DB에 정확하게 들어갔는지 알기 위해서
        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId);
        ReplyFindByIdDTO result = resultList.get(resultList.size() - 1);

        // 단언문
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글수정")
    public void updateTest() {
        // given
        long replyId = 3;
        String replyWriter = "코로그";
        String replyContent = "난코로그선인장이양";

        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                .replyId(replyId)
                        .replyWriter(replyWriter)
                                .replyContent(replyContent)
                                        .build();

        // when
        replyRepository.update(replyUpdateDTO);

        // then
        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertNotEquals(result.getPublishedAt(), result.getUpdatedAt());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }


}
