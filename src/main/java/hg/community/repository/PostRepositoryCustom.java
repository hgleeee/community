package hg.community.repository;

import hg.community.SearchCondition;
import hg.community.dto.PostPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    public Page<PostPreviewDto> findBestPostOrderByBestTime(Pageable pageable, String categoryName, SearchCondition searchCondition);
    public Page<PostPreviewDto> findPostByCategoryOrderByTimeDesc(Pageable pageable, String categoryUrl, SearchCondition searchCondition);
}
