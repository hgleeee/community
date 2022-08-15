package hg.community.aop.aspect;

import hg.community.aop.AdminLoginCheck;
import hg.community.aop.LoginCheck;
import hg.community.domain.Role;
import hg.community.exception.NoAuthorityException;
import hg.community.util.SessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class AuthCheckAspect {

    /**
     * 로그인 체크 AOP
     * 이 메서드를 통해 관리자, 게시판지기, 일반유저로 각각 메서드 실행
     * @param joinPoint
     * @param loginCheck, 권한 체크 용도
     * @throws Throwable
     */

    @Before("@annotation(hg.community.aop.LoginCheck) && @annotation(loginCheck)")
    public void loginCheck(LoginCheck loginCheck) throws Throwable {
        log.info("로그인 체크 시작");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if (loginCheck.role().equals(Role.ADMIN)) {
            try {
                adminLoginCheck();
            } catch (Exception e) {
                response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
            }
        } else if (loginCheck.role().equals(Role.MANAGER)) {
            try {
                managerLoginCheck();
            } catch (Exception e) {
                response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
            }
        } else {
            try {
                userLoginCheck();
            } catch (Exception e) {
                response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
            }
        }
    }

    /**
     * 세션에서 관리자 로그인 체크
     * @throws Throwable
     */

    public void adminLoginCheck() throws Throwable {
        log.debug("관리자 권한 체크 시작");
        HttpSession session = ((ServletRequestAttributes)
                (RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);
        if (session == null || SessionUtils.getLoginMember(session).getRole() != Role.ADMIN) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "NO AUTHORITY") {};
        }
    }

    /**
     * 세션에서 게시판지기 로그인 체크
     * @throws Throwable
     */
    public void managerLoginCheck() throws Throwable {
        log.debug("게시판지기 권한 체크 시작");
        HttpSession session = ((ServletRequestAttributes)
                (RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);
        if (session == null || SessionUtils.getLoginMember(session).getRole() != Role.MANAGER) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "NO AUTHORITY") {};
        }
    }

    /**
     * 세션에서 관리자 로그인 체크
     * @throws Throwable
     */
    public void userLoginCheck() throws Throwable {
        log.debug("유저 권한 체크 시작");
        HttpSession session = ((ServletRequestAttributes)
                (RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);
        if (session == null || SessionUtils.getLoginMember(session).getRole() != Role.USER) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "NO AUTHORITY") {};
        }
    }
}
