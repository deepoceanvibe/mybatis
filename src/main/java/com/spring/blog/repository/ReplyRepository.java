package com.spring.blog.repository;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyResponseDTO> findAllByBlogId(long blogId);
    ReplyResponseDTO findByReplyId(long replyId);
    void deleteByReplyId(long replyId);
    void save(ReplyCreateRequestDTO replyCreateRequestDTO);
    void update(ReplyUpdateRequestDTO replyUpdateRequestDTO);

    // blogId를 받아 그 글의 전체 댓글을 싹다 삭제하기
    void deleteAllByBlogId(long blogId);

}
