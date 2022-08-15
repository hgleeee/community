package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import hg.community.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdNameGradeOfMemberDto {

    private long id;
    private String loginId;
    private String name;
    private String role;

    @QueryProjection
    public IdNameGradeOfMemberDto(long id, String loginId, String name, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        if (role.equals(Role.MANAGER)) {
            this.role = "manager";
        } else if (role.equals(Role.USER)) {
            this.role = "user";
        } else {
            this.role = "admin";
        }
    }
}
