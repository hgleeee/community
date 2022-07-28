package hg.community.domain.member;

import hg.community.config.Role;

import java.time.LocalDate;

public class Manager extends Member {

    protected Manager(String name, String nickname, LocalDate birthday, String frontSixSSR, String endSevenSSR, String loginId, String password) {
        super(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
        this.role = Role.MANAGER;
    }

    public static Manager createManager(String name, String nickname, LocalDate birthday, String frontSixSSR,
                                                String endSevenSSR, String loginId, String password) {
        return new Manager(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
    }
}
