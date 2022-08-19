package hg.community.domain;

import hg.community.constant.ExpConst;
import hg.community.domain.baseentity.DateBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
public class Member extends DateBaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String nickname;
    private LocalDate birthday;
    private String frontSixSSR;
    private String endSevenSSR;
    private String loginId;
    private String password;
    private int visitCount;
    private int level;
    private int exp;
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String addressDetail;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 비즈니스 로직
     * 경험치를 얻는 메소드, 레벨도 그에 맞춰 변화
     */
    public void addExp(int addedExp) {
        if (this.exp == ExpConst.maxExp) {
            return;
        }
        this.exp += addedExp;
        this.level = exp / 1000 + 1;
    }

    public void updateRoleByAdmin(String role) {
        if (role.equals("admin")) {
            this.role = Role.ADMIN;
        } else if (role.equals("manager")) {
            this.role = Role.MANAGER;
        } else {
            this.role = Role.USER;
        }
    }

    public static Member createMember(String name, String nickname, LocalDate birthday, String frontSixSSR, String endSevenSSR,
                                      String loginId, String password, Role role, Address address, String addressDetail) {
        Member member = new Member();
        member.name = name;
        member.nickname = nickname;
        member.birthday = birthday;
        member.frontSixSSR = frontSixSSR;
        member.endSevenSSR = endSevenSSR;
        member.loginId = loginId;
        member.password = password;
        member.role = role;
        if (role == Role.MANAGER) {
            member.exp = ExpConst.maxExp;
            member.level = 9999;
        } else if (role == Role.ADMIN) {
            member.exp = ExpConst.maxExp;
            member.level = 99999;
        } else {
            member.exp = 0;
            member.level = 1;
        }
        member.address = address;
        member.addressDetail = addressDetail;
        return member;
    }

    public int increaseVisitCount() {
        this.visitCount++;
        return visitCount;
    }
}
