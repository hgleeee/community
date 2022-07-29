package hg.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request, @RequestParam(value = "error", required = false) String error,
                               Model model) {
        String referer = request.getHeader("referer");
        if (error == null) {
            request.getSession().setAttribute("prevPage", referer);
        }
        model.addAttribute("error", error);
        return "login/loginForm";
    }


}
