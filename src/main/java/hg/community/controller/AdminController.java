package hg.community.controller;

import hg.community.aop.LoginCheck;
import hg.community.domain.Role;
import hg.community.dto.CategoryCreateDto;
import hg.community.dto.IdNameGradeOfMemberDto;
import hg.community.dto.MainCategoryNoticeIdDto;
import hg.community.dto.PostCreateDto;
import hg.community.service.CategoryService;
import hg.community.service.MemberService;
import hg.community.service.PostService;
import hg.community.service.PostServiceImpl;
import hg.community.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final MemberService memberService;
    private final PostService postService;


    @GetMapping
    @LoginCheck(role = Role.ADMIN)
    public String getAdminIndex(Model model) {
        return "admin/index";
    }

    /** 멤버별 권한 부여 */
    @GetMapping("/authorize")
    @LoginCheck(role = Role.ADMIN)
    public String getAuthorizePage() {
        return "admin/authorize";
    }

    @GetMapping("/authorize/getMemberList")
    @ResponseBody
    @LoginCheck(role = Role.ADMIN)
    public List<IdNameGradeOfMemberDto> getMemberList(@RequestParam(name = "refer") String refer) {
        if (refer.isEmpty()) {
            return null;
        }
        return memberService.findIdNameGradeByRefer(refer);
    }

    @GetMapping("/authorize/{memberId}")
    @LoginCheck(role = Role.ADMIN)
    public String getMemberAuthEdit(@PathVariable("memberId") Long memberId, Model model) {
        model.addAttribute("member", memberService.findMemberById(memberId));
        return "admin/editMemberAuth";
    }

    @PostMapping("/editMemberAuth")
    @LoginCheck(role = Role.ADMIN)
    public String postMemberAuthEdit(@RequestParam("memberId") Long memberId, @RequestParam("grade") String grade) {
        memberService.updateMemberGrade(memberId, grade);
        return "redirect:/admin/authorize";
    }

    @GetMapping("/notice")
    @LoginCheck(role = Role.ADMIN)
    public String getNoticeRegisterForm(Model model) {
        List<MainCategoryNoticeIdDto> mainCategories = categoryService.findMainCategoryNoticeId();
        model.addAttribute("mainCategories", mainCategories);
        return "admin/registerNotice";
    }

    @PostMapping("/notice/register")
    @LoginCheck(role = Role.ADMIN)
    public String postNoticeRegister(Principal principal, @ModelAttribute PostCreateDto postCreateDto) {
        String loginId = principal.getName();
        Long saveId = postService.save(postCreateDto, loginId);
        return "redirect:/post/" + saveId;
    }

    @GetMapping("/categories")
    @LoginCheck(role = Role.ADMIN)
    public String getCategoryStatus(Model model) {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @GetMapping("/categories/edit")
    @LoginCheck(role = Role.ADMIN)
    public String getEditCategories(@RequestParam(required = false) String parentCategoryId, Model model) {
        model.addAttribute("parentCategoryId", parentCategoryId);
        return "admin/categoryEdit";
    }

    @PostMapping("/categories/edit")
    @LoginCheck(role = Role.ADMIN)
    public String editCategories(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        log.info("parent id={}", categoryCreateDto.getParentCategoryId());
        categoryService.save(categoryCreateDto);
        return "redirect:/admin/categories";
    }

}
