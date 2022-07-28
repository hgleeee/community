package hg.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto {

    private String name;
    private String nickname;
    private LocalDate birthday;
    private String frontSixSSR;
    private String endSevenSSR;
    private String loginId;
    private String password;

}
