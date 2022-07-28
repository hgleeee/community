package hg.community.service;

import hg.community.domain.Category;
import hg.community.dto.CategoryCreateDto;
import hg.community.dto.CategoryDto;
import hg.community.repository.CategoryRepository;
import hg.community.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAllOrderByCreatedDateAsc() {
        return categoryRepository.findAllOrderByCreatedDateAsc();
    }

    @Transactional
    @Override
    public CategoryDto save(CategoryCreateDto categoryCreateDto) {
        Category category = null;
        if (categoryCreateDto.getParentCategoryId() == null) {
            category = Category.createCategory(categoryCreateDto.getName(), categoryCreateDto.getUrlName());
        } else {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryCreateDto.getParentCategoryId());
            if (optionalCategory.isEmpty()) {
                throw new IllegalStateException("존재하지 않는 카테고리에 접근하였습니다.");
            }
            if (optionalCategory.get().getDepth() == 2) {
                throw new IllegalStateException("더 이상 서브 카테고리를 만들 수 없습니다.");
            }
            category = Category.createCategory(categoryCreateDto.getName(), categoryCreateDto.getUrlName(), optionalCategory.get());
        }
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDto(savedCategory.getId(), savedCategory.getName(), savedCategory.getUrlName(),
                savedCategory.getDepth(), savedCategory.getParentCategory() == null ? null : savedCategory.getParentCategory().getId(),
                savedCategory.getParentCategory() == null ? null : savedCategory.getParentCategory().getName());
    }

    @Override
    public Optional<CategoryDto> findOneByUrlName(String urlName) {
        return categoryRepository.findOneByUrlName(urlName);
    }

    @Override
    public List<CategoryVo> findCategoryVo() {
        List<CategoryDto> categoryList = categoryRepository.findAllOrderByCreatedDateAsc();
        List<CategoryVo> categories = new ArrayList<>();
        for (CategoryDto category : categoryList) {
            System.out.println(category.getId() + " " + category.getName() + " " + category.getDepth());
            if (category.getDepth() == 0) {
                categories.add(new CategoryVo(category.getId(), category.getName(), category.getUrlName()));
            } else if (category.getDepth() == 1) {
                for (CategoryVo depthZero : categories) {
                    if (depthZero.getId() == category.getParentCategoryId()) {
                        depthZero.beSub(new CategoryVo(category.getId(), category.getName(), category.getUrlName()));
                        break;
                    }
                }
            } else {
                boolean isBreak = false;
                for (CategoryVo depthZero : categories) {
                    for (CategoryVo depthOne : depthZero.getSubCategory()) {
                        if (depthOne.getId() == category.getParentCategoryId()) {
                            isBreak = true;
                            depthOne.beSub(new CategoryVo(category.getId(), category.getName(), category.getUrlName()));
                            break;
                        }
                    }
                    if (isBreak) break;
                }
            }
        }
        return categories;
    }
}
