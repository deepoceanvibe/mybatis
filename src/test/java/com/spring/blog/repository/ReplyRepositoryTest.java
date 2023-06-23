package com.spring.blog.repository;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
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
        List<ReplyResponseDTO> replyList = replyRepository.findAllByBlogId(blogId);

        // then
        assertEquals(4, replyList.size());
    }

    @Test
    @Transactional
    public void findByReplyIdTest() {
        // given
        long replyId = 3;

        // when
        ReplyResponseDTO replyResponseDTO = replyRepository.findByReplyId(replyId);

        // then
        assertEquals("나무", replyResponseDTO.getReplyWriter());
        assertEquals(3, replyResponseDTO.getReplyId());
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

        ReplyCreateRequestDTO replyCreateRequestDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyRepository.save(replyCreateRequestDTO);

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

        ReplyCreateRequestDTO replyCreateRequestDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // when
        replyRepository.save(replyCreateRequestDTO);

        // then

        // blogId번글의 전체 댓글을 가지고 온 다음 마지막 인덱스 요소만 변수에 저장 후,
        // getter를 이용해 위에서 넣은 * fixture와 일치하는지 검증!!! *
        // blogId에 해당하는 댓글 목록을 검색하고, 그 중에서 가장 최근(마지막) 댓글을 찾기

        // fixture 비교를 굳이 하는 이유 : 한글로 들어갔는지, DB에 정확하게 들어갔는지 알기 위해서
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        ReplyResponseDTO result = resultList.get(resultList.size() - 1);

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

        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyId(replyId)
                        .replyWriter(replyWriter)
                                .replyContent(replyContent)
                                        .build();

        // when
        replyRepository.update(replyUpdateRequestDTO);

        // then
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);

        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertNotEquals(result.getPublishedAt(), result.getUpdatedAt());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }

    @Test
    @Transactional
    @DisplayName("blogId가 2인 글을 삭제하면, 그 글의 전체 댓글이 다 0개")
    public void deleteAllByBlogIdTest() {
        // given
        long blogId = 2;

        // when
        replyRepository.deleteAllByBlogId(blogId);

        // then
        List<ReplyResponseDTO> replyList = replyRepository.findAllByBlogId(blogId);
        assertEquals(0, replyList.size());
    }


}
