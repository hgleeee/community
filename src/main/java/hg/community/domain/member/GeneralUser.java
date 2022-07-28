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
@DiscriminatorValue(value = "G")
public class GeneralUser extends Member {

    private int level;

    protected GeneralUser(String name, String nickname, LocalDate birthday, String frontSixSSR, String endSevenSSR, String loginId, String password) {
        super(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
        this.level = 1;
        this.role = Role.USER;
    }

    public static GeneralUser createGeneralUser(String name, String nickname, LocalDate birthday, String frontSixSSR,
                                                  String endSevenSSR, String loginId, String password) {
        return new GeneralUser(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password);
    }
}
