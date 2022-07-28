package hg.community;

import hg.community.dto.CategoryCreateDto;
import hg.community.dto.CategoryDto;
import hg.community.dto.MemberRegisterDto;
import hg.community.dto.PostCreateDto;
import hg.community.service.CategoryService;
import hg.community.service.MemberService;
import hg.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final PostService postService;

    @PostConstruct
    public void init() {
        MemberRegisterDto registerForm = new MemberRegisterDto("김철수", "kim", LocalDate.now(),
                "123123", "9876543", "test", "test");
        memberService.register(registerForm);

        CategoryDto root = categoryService.save(new CategoryCreateDto("유머/시사", null, null));
        CategoryDto one = categoryService.save(new CategoryCreateDto("유머", "humor", root.getId()));
        CategoryDto one1 = categoryService.save(new CategoryCreateDto("시사", "sisa", root.getId()));
        CategoryDto two = categoryService.save(new CategoryCreateDto("공지", "notice", one.getId()));
        CategoryDto two1 = categoryService.save(new CategoryCreateDto("공지", "notice", one1.getId()));
        postService.save(new PostCreateDto(two.getId(), "test", "안녕", "hi 요"));
        postService.save(new PostCreateDto(two.getId(), "test", "안녕하세요", "hello 입니다"));
    }
}
