package hg.community;

import hg.community.domain.Category;
import hg.community.domain.Post;
import hg.community.domain.Member;
import hg.community.domain.Role;
import hg.community.dto.CategoryCreateDto;
import hg.community.dto.CategoryDto;
import hg.community.dto.MemberRegisterDto;
import hg.community.repository.CategoryRepository;
import hg.community.repository.MemberRepository;
import hg.community.repository.PostRepository;
import hg.community.service.CategoryService;
import hg.community.service.MemberService;
import hg.community.service.PostService;
import hg.community.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        MemberRegisterDto registerForm = new MemberRegisterDto("김철수", "kim", LocalDate.now(),
                "123123", "9876543", "test", "test", "test");
        memberService.register(registerForm);
        memberRepository.save(Member.createMember("이민수", "관리자에용", LocalDate.now(),
                "123123","1241412", "admin", PasswordEncryptor.encrypt("admin"), Role.ADMIN));

        CategoryDto root = categoryService.save(new CategoryCreateDto("유머/시사", null, null));
        CategoryDto one = categoryService.save(new CategoryCreateDto("유머", "humor", root.getId()));
        CategoryDto one1 = categoryService.save(new CategoryCreateDto("시사", "sisa", root.getId()));
        CategoryDto two = categoryService.save(new CategoryCreateDto("공지", "notice", one.getId()));
        CategoryDto two1 = categoryService.save(new CategoryCreateDto("공지", "notice", one1.getId()));


        Member member = memberRepository.findByLoginId("test").get();
        Category category = categoryRepository.findById(two.getId()).get();
    }
}
