package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
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

    @QueryProjection
    public MemberDto(String name, String nickname, LocalDate birthday, String loginId, String password) {
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.loginId = loginId;
        this.password = password;
    }
}
