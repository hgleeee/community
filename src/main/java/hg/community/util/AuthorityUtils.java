package hg.community.util;

import hg.community.constant.SessionConst;
import hg.community.domain.Role;
import hg.community.vo.MyAuthentication;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class AuthorityUtils {

    public static void checkAuthority(HttpSession session, Model model) {
        MyAuthentication auth = (MyAuthentication) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (auth == null) {
            model.addAttribute("isAuthenticated", false);
            return;
        }
        model.addAttribute("isAuthenticated", true);
        if (auth.getRole() == Role.ADMIN) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }
    }
}
