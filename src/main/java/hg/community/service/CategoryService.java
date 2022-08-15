package hg.community.service;

import hg.community.dto.CategoryCreateDto;
import hg.community.dto.CategoryDto;
import hg.community.dto.MainCategoryNoticeIdDto;
import hg.community.vo.CategoryVo;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAllOrderByCreatedDateAsc();
    CategoryDto save(CategoryCreateDto categoryCreateDto);
    Optional<CategoryDto> findOneByUrlName(String urlName);
    List<CategoryVo> findCategoryVo();
    List<CategoryDto> findSubCategoriesByMainCategoryOrderByCreatedTimeAsc(String mainCategoryName);
    List<MainCategoryNoticeIdDto> findMainCategoryNoticeId();
}
