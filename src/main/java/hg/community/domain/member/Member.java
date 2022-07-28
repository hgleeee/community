package hg.community.domain.member;

import hg.community.config.Role;
import hg.community.domain.Comment;
import hg.community.domain.Post;
import hg.community.domain.baseentity.DateBaseEntity;
import lombok.AccessLevel;
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
public abstract class Member extends DateBaseEntity {

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
    protected Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Member(String name, String nickname, LocalDate birthday, String frontSixSSR, String endSevenSSR, String loginId, String password) {
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.frontSixSSR = frontSixSSR;
        this.endSevenSSR = endSevenSSR;
        this.loginId = loginId;
        this.password = password;
        this.visitCount = 0;
    }

    public int increaseVisitCount() {
        this.visitCount++;
        return visitCount;
    }
}
