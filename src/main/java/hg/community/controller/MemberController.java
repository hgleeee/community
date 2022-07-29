package hg.community.controller;

import hg.community.dto.MemberRegisterDto;
import hg.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("member", new MemberRegisterDto());
        return "register/registerForm";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("member") MemberRegisterDto form, BindingResult bindingResult) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.addError(new FieldError("member", "confirmPassword",
                    "비밀번호와 비밀번호 확인이 서로 다릅니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "register/registerForm";
        }

        //성공 로직
        memberService.register(form);
        return "redirect:/register/success";
    }

    @GetMapping("/register/success")
    public String getRegisterSuccess() {
        return "register/registerSuccess";
    }
}
