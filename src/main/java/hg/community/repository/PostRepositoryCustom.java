package hg.community.repository;

import hg.community.SearchCondition;
import hg.community.dto.PostPreviewDto;
import hg.community.dto.PostSimplePreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    public Page<PostPreviewDto> findBestPostOrderByBestTime(Pageable pageable, SearchCondition searchCondition);
    public Page<PostPreviewDto> findPostByCategoryOrderByTimeDesc(Pageable pageable, String categoryUrl, SearchCondition searchCondition);
    public Optional<String> findContentById(Long id);
    public List<PostSimplePreviewDto> findBestPostByCategoryNameOrderByLikeNum(String mainCategoryUrl, int days);
    public Integer findLikeOrDislikeNumById(Long postId, Boolean isLike);

}
