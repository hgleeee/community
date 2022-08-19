package hg.community.controller;

import hg.community.constant.PagingConst;
import hg.community.SearchCondition;
import hg.community.constant.SessionConst;
import hg.community.dto.*;
import hg.community.service.CategoryService;
import hg.community.service.PostService;
import hg.community.util.AuthorityUtils;
import hg.community.util.SessionUtils;
import hg.community.vo.CategoryVo;
import hg.community.vo.MyAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/category/{categoryUrlName}")
    public String getPostByCategory(@PageableDefault(page = 1) Pageable pageable,
                                    @PathVariable("categoryUrlName") String urlName,
                                    @ModelAttribute SearchCondition searchCondition,
                                    HttpSession session, Model model) {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        model.addAttribute("categories", categories);
        Optional<CategoryDto> optionalCategory = categoryService.findOneByUrlName(urlName);
        if (optionalCategory.isEmpty()) {
            throw new IllegalStateException("해당 url을 가진 카테고리가 존재하지 않습니다.");
        }

        Page<PostPreviewDto> posts = postService.findPostByCategoryOrderByTime(pageable, urlName, searchCondition);
        model.addAttribute("posts", posts);
        int startPage = PagingConst.startPage(pageable);
        int endPage = PagingConst.endPage(startPage, posts);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<PostSimplePreviewDto> bestPostsToday = postService.findBestPostByCategoryNameOrderByLikeNum(urlName, 1);
        List<PostSimplePreviewDto> bestPostsWeek = postService.findBestPostByCategoryNameOrderByLikeNum(urlName, 7);
        List<PostSimplePreviewDto> bestPostsMonth = postService.findBestPostByCategoryNameOrderByLikeNum(urlName, 30);
        model.addAttribute("bestPostsToday", bestPostsToday);
        model.addAttribute("bestPostsWeek", bestPostsWeek);
        model.addAttribute("bestPostsMonth", bestPostsMonth);
        model.addAttribute("mainCategory", urlName);
        AuthorityUtils.checkAuthority(session, model);

        return "post/postList";
    }

    @GetMapping("/category/{categoryUrlName}/register")
    public String getPostRegisterForm(@ModelAttribute(name = "post") PostCreateDto postCreateDto,
                                      @PathVariable("categoryUrlName") String categoryUrlName,
                                      Model model, HttpSession session) {
        List<CategoryDto> subCategories = categoryService.findSubCategoriesByMainCategoryOrderByCreatedTimeAsc(categoryUrlName);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("categoryUrlName", categoryUrlName);
        AuthorityUtils.checkAuthority(session, model);
        return "post/postRegister";
    }

    @PostMapping("/category/{categoryUrlName}/register")
    public String postRegister(@ModelAttribute(name = "post") PostCreateDto postCreateDto,
                               @PathVariable("categoryUrlName") String categoryUrlName,
                               HttpSession session) {
        if (session.getId() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        MyAuthentication auth = SessionUtils.getLoginMember(session);
        Long savedId = postService.save(postCreateDto, auth.getLoginId());
        return "redirect:/post/" + savedId;
    }

    @GetMapping("/post/{postId}")
    public String getPostDetail(@PathVariable("postId") String postId,
                                HttpSession session, Model model, HttpServletResponse response) throws Exception {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        model.addAttribute("categories", categories);
        PostDto post = postService.findOneById(Long.parseLong(postId));
        model.addAttribute("post", post);
        AuthorityUtils.checkAuthority(session, model);
        String loginId = SessionUtils.getLoginMember(session).getLoginId();
        model.addAttribute("loginId", loginId);
        return "post/postDetail";
    }

    @PostMapping("/post/{postId}/click")
    @ResponseBody
    public Integer clickLikeOrDislikeButton(@PathVariable("postId") Long postId,
                                            @RequestParam("isLike") Boolean isLike, Model model) {
        Integer result = postService.clickLikeOrDislikeButton(postId, isLike);
        return result;
    }

    @GetMapping("/getPostContent")
    @ResponseBody
    private String getPostContent(@RequestParam("id") Long id) {
        Optional<String> optionalContent = postService.findContentById(id);
        if (optionalContent.isEmpty()) {
            throw new IllegalStateException("해당 글을 찾을 수 없습니다.");
        }
        return optionalContent.get();
    }


}
