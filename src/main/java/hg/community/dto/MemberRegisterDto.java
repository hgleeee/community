package hg.community.dto;

import hg.community.domain.Member;
import hg.community.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotEmpty
    private String frontSixSSR;
    @NotEmpty
    private String endSevenSSR;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

    public Member toEntity() {
        return Member.createMember(name, nickname, birthday, frontSixSSR, endSevenSSR, loginId, password, Role.USER);
    }
}
