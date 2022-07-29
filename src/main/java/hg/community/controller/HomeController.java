package hg.community.controller;

import hg.community.service.CategoryService;
import hg.community.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<CategoryVo> categories = categoryService.findCategoryVo();
        model.addAttribute("categories", categories);
        return "index";
    }
}
