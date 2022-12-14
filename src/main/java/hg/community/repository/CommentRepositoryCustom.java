package hg.community.repository;

import hg.community.SearchCondition;
import hg.community.dto.CommentDto;
import hg.community.dto.PostPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    public List<CommentDto> findCommentOrderByTimeDesc(Long postId);
}
