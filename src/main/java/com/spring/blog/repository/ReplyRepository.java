package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);
    ReplyFindByIdDTO findByReplyId(long replyId);
    void deleteByReplyId(long replyId);
    void save(ReplyInsertDTO replyInsertDTO);
    void update(ReplyUpdateDTO replyUpdateDTO);

    // blogId를 받아 그 글의 전체 댓글을 싹다 삭제하기
    void deleteAllByBlogId(long blogId);

}
