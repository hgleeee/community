package hg.community.service;

import hg.community.SearchCondition;
import hg.community.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    public Page<PostPreviewDto> findBestPostOrderByBestTime(Pageable pageable, SearchCondition searchCondition);
    public Long save(PostCreateDto postCreateDto, String loginId);
    public Page<PostPreviewDto> findPostByCategoryOrderByTime(Pageable pageable, String categoryUrl, SearchCondition searchCondition);
    public PostDto findOneById(Long id) throws Exception;
    public Optional<String> findContentById(Long id);
    public List<PostSimplePreviewDto> findBestPostByCategoryNameOrderByLikeNum(String mainCategoryUrl, int days);
    public Integer clickLikeOrDislikeButton(Long postId, Boolean isLike);
}
