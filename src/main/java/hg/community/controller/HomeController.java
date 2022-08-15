package hg.community.controller;

import hg.community.constant.PagingConst;
import hg.community.SearchCondition;
import hg.community.constant.SessionConst;
import hg.community.dto.PostPreviewDto;
import hg.community.service.CategoryService;
import hg.community.service.PostService;
import hg.community.util.AuthorityUtils;
import hg.community.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping("/")
    public String getHome(@PageableDefault(page = 1) Pageable pageable,
                          @ModelAttribute SearchCondition searchCondition,
                          Model model, HttpSession session) {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        Page<PostPreviewDto> bestPosts = postService.findBestPostOrderByBestTime(pageable, searchCondition);
        model.addAttribute("posts", bestPosts);
        model.addAttribute("categories", categories);
        int startPage = PagingConst.startPage(pageable);
        int endPage = PagingConst.endPage(startPage, bestPosts);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        AuthorityUtils.checkAuthority(session, model);
        return "index";
    }
}
