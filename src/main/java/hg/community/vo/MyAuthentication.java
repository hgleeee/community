package hg.community.vo;

import hg.community.domain.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * 인증 객체, 세션에 저장되는 용도
 * 로그인 id와 권한 정보 담긴 객체
 */
@Data
public class MyAuthentication implements Serializable {

    private String loginId;
    private Role role;

    public MyAuthentication(String loginId, Role role) {
        this.loginId = loginId;
        this.role = role;
    }
}
