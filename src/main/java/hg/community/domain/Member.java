package hg.community.domain.member;

import hg.community.ExpConst;
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
    private int level;
    private int exp;
    protected Role role;

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

    public Member(String name, String nickname, LocalDate birthday, String frontSixSSR,
                  String endSevenSSR, String loginId, String password, int level) {
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.frontSixSSR = frontSixSSR;
        this.endSevenSSR = endSevenSSR;
        this.loginId = loginId;
        this.password = password;
        this.visitCount = 0;
        this.level = level;
    }

    public int increaseVisitCount() {
        this.visitCount++;
        return visitCount;
    }
}
