package hg.community.controller;

import com.sun.xml.bind.v2.TODO;
import hg.community.aop.LoginCheck;
import hg.community.domain.Member;
import hg.community.domain.Role;
import hg.community.dto.LoginDto;
import hg.community.dto.MemberDto;
import hg.community.service.MemberService;
import hg.community.util.AuthorityUtils;
import hg.community.util.SessionUtils;
import hg.community.vo.MyAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute LoginDto loginDto,
                               HttpSession session, Model model) {
        AuthorityUtils.checkAuthority(session, model);
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpSession session, Model model
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        try {
            MemberDto memberDto = memberService.login(loginDto.getLoginId(), loginDto.getPassword());
            SessionUtils.setLoginMember(session, new MyAuthentication(memberDto.getLoginId(), memberDto.getRole()));
        } catch (RuntimeException e) {
            model.addAttribute("error", true);
            log.info("로그인 실패");
            return "login/loginForm";
        } catch (Exception e) { // 예측할 수 없는 에러
            throw e;
        }
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.logout(session);
        return "redirect:/";
    }
}
