package hg.community.controller;

import hg.community.PagingConst;
import hg.community.SearchCondition;
import hg.community.dto.CategoryDto;
import hg.community.dto.PostPreviewDto;
import hg.community.service.CategoryService;
import hg.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/category/{categoryUrlName}")
    public String getPostByCategory(@PageableDefault(page = 1) Pageable pageable,
                                    @PathVariable("categoryUrlName") String urlName,
                                    @ModelAttribute SearchCondition searchCondition, Model model) {
        Optional<CategoryDto> optionalCategory = categoryService.findOneByUrlName(urlName);
        if (optionalCategory.isEmpty()) {
            throw new IllegalStateException("해당 url을 가진 카테고리가 존재하지 않습니다.");
        }
        Page<PostPreviewDto> posts = postService.findPostByCategoryOrderByTime(pageable, urlName, searchCondition);
        model.addAttribute("posts", posts);
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT+1;
        int endPage = (startPage+PagingConst.BLOCK_LIMIT-1 < posts.getTotalPages()) ? startPage+PagingConst.BLOCK_LIMIT-1 : posts.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("mainCategory", urlName);
        return "post/postList";
    }
}
