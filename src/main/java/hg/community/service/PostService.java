package hg.community.service;

import hg.community.SearchCondition;
import hg.community.dto.PostCreateDto;
import hg.community.dto.PostDto;
import hg.community.dto.PostPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    public Long save(PostCreateDto postCreateDto);
    public Page<PostPreviewDto> findPostByCategoryOrderByTime(Pageable pageable, String categoryUrl, SearchCondition searchCondition);
    public PostDto findOneById(Long id);

}
