package hg.community.domain.member;

import hg.community.config.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@DiscriminatorValue(value = "A")
public class Admin extends Member {

    protected Admin(String name, String nickname, LocalDate birthday, String frontSixSSR, String endSevenSSR, String loginId, String password) {
        super(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
        this.role = Role.ADMIN;
    }

    public static Admin createAdmin(String name, String nickname, LocalDate birthday, String frontSixSSR,
                                        String endSevenSSR, String loginId, String password) {
        return new Admin(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
    }
}
