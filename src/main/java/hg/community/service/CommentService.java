package hg.community.service;

import hg.community.dto.CommentDto;
import hg.community.dto.CommentRegisterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    public Long save(CommentRegisterDto commentRegisterDto);
    public List<CommentDto> findCommentOrderByTimeDesc(Long postId);
}
