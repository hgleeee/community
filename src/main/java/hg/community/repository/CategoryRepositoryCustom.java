package hg.community.repository;

import hg.community.dto.CategoryDto;
import hg.community.dto.MainCategoryNoticeIdDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {

    List<CategoryDto> findAllOrderByCreatedDateAsc();
    Optional<CategoryDto> findOneByUrlName(String urlName);
    List<CategoryDto> findSubCategoriesByMainCategoryOrderByCreatedTimeAsc(String mainCategoryName);
    List<MainCategoryNoticeIdDto> findMainCategoryNoticeId();
}
