package hg.community.util;

import hg.community.constant.SessionConst;
import hg.community.vo.MyAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

//    /**
//     * 로그인한 관리자의 id를 세션에서 꺼냄
//     * @param session
//     * @return 로그인한 관리자의 id, 만일 없다면 null 반환
//     */
//    public static String getLoginAdminId(HttpSession session) {
//        return (String) session.getAttribute(SessionConst.LOGIN_ADMIN);
//    }
//
//    /**
//     * 로그인한 관리자의 id를 세션에 저장
//     * @param session
//     * @param id 로그인 id
//     */
//    public static void setLoginAdminId(HttpSession session, String id) {
//        session.setAttribute(SessionConst.LOGIN_ADMIN, id);
//    }
//
//
//    /**
//     * 로그인한 게시판지기의 id를 세션에서 꺼냄
//     * @param session
//     * @return 로그인한 게시판지기의 id, 만일 없다면 null 반환
//     */
//    public static String getLoginManagerId(HttpSession session) {
//        return (String) session.getAttribute(SessionConst.LOGIN_MANAGER);
//    }
//
//    /**
//     * 로그인한 게시판지기의 id를 세션에 저장
//     * @param session
//     * @param id 로그인 id
//     */
//    public static void setLoginManagerId(HttpSession session, String id) {
//        session.setAttribute(SessionConst.LOGIN_MANAGER, id);
//    }

    /**
     * 로그인한 유저의 id를 세션에서 꺼냄
     * @param session
     * @return 로그인한 유저의 id, 만일 없다면 null 반환
     */
    public static MyAuthentication getLoginMember(HttpSession session) {
        return (MyAuthentication) session.getAttribute(SessionConst.LOGIN_MEMBER);
    }

    /**
     * 로그인한 유저의 id를 세션에 저장
     * @param session
     * @param auth 권한정보 객체
     */
    public static void setLoginMember(HttpSession session, MyAuthentication auth) {
        session.setAttribute(SessionConst.LOGIN_MEMBER, auth);
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
    }
}
