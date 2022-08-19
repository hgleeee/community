package hg.community.domain;

import hg.community.domain.baseentity.TimeBaseEntity;
import hg.community.dto.PostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeBaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String content;
    private int likeNum;
    private int disLikeNum;
    private int views;

    private LocalDateTime hotIssueTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    /** 연관관계 편의 메서드 시작 */
    private void setMember(Member member) {
        this.member = member;
        //member.getPosts().add(this);
    }

    private void setCategory(Category category) {
        this.category = category;
        //category.getPosts().add(this);
    }
    /** 연관관계 편의 메서드 끝 */

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
    public void setDisLikeNum(int disLikeNum) {
        this.disLikeNum = disLikeNum;
    }
    public void setHotIssueTime(LocalDateTime localDateTime) {
        this.hotIssueTime = localDateTime;
    }
    public static Post createPost(String title, String content, Member member, Category category) {
        Post post = new Post();
        post.title = title;
        post.content = content;
        post.setCategory(category);
        post.setMember(member);
        return post;
    }

    public PostDto toPostDto() {
        return new PostDto(id, title, content, member.getLoginId(), member.getNickname(), getCreatedDateTime(), views, likeNum, disLikeNum,
                category.getParentCategory().getUrlName(), category.getParentCategory().getName(), comments.size());
    }
}
