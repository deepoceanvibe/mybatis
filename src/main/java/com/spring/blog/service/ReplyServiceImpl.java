package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.entity.Reply;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    ReplyRepository replyRepository;
    ReplyJPARepository replyJPARepository;


    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository, ReplyJPARepository replyJPARepository) {
        this.replyRepository = replyRepository;
        this.replyJPARepository = replyJPARepository;
    }


    @Override
    public List<Reply> findAllByBlogId(long blogId) {
        return replyJPARepository.findAllByBlogId(blogId);
    }

    @Override
    public Reply findByReplyId(long replyId) {
        return replyJPARepository.findById(replyId).get();
    }

    @Override
    public void deleteByReplyId(long replyId) {
        replyJPARepository.deleteById(replyId);
    }

    @Override
    public void save(Reply reply) {
        replyJPARepository.save(reply);
    }

    @Override
    public void update(Reply reply) {
        Reply updatedReply = replyJPARepository.findById(reply.getReplyId()).get();
        updatedReply.setReplyWriter(reply.getReplyWriter());
        updatedReply.setReplyContent(reply.getReplyContent());
        replyJPARepository.save(updatedReply);
    }
}
