package hg.community.controller;

import hg.community.dto.CategoryCreateDto;
import hg.community.dto.CategoryDto;
import hg.community.service.CategoryService;
import hg.community.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategoryStatus(Model model) {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @GetMapping("/categories/edit")
    public String getEditCategories(@RequestParam(required = false) String parentCategoryId, Model model) {
        model.addAttribute("parentCategoryId", parentCategoryId);
        return "admin/categoryEdit";
    }

    @PostMapping("/categories/edit")
    public String editCategories(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        log.info("parent id={}", categoryCreateDto.getParentCategoryId());
        categoryService.save(categoryCreateDto);
        return "redirect:/categories";
    }

}
