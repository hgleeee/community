package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import hg.community.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberDto {

    String name;
    String nickname;
    LocalDate birthday;
    String loginId;
    String password;
    Role role;

    @QueryProjection
    @Builder
    public MemberDto(String name, String nickname, LocalDate birthday, String loginId, String password, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }
}
