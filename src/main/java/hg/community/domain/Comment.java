package hg.community.domain;

import hg.community.domain.baseentity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeBaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Lob
    private String content;

    private int likeNum;
    private int dislikeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> subComments = new ArrayList<>();

    /** 연관관계 편의 메서드 시작 */
    public void setPost(Post post) {
        this.post = post;
        this.post.getComments().add(this);
    }
    public void setMember(Member member) {
        this.member = member;
        this.member.getComments().add(this);
    }
    public void setParentComment(Comment comment) {
        this.parentComment = comment;
        parentComment.getSubComments().add(this);
    }
    /** 연관관계 편의 메서드 끝 */

    public static Comment createComment(String content, Post post, Member member) {
        Comment comment = new Comment();
        comment.content = content;
        comment.setPost(post);
        comment.setMember(member);
        return comment;
    }

    public static Comment createComment(String content, Post post, Member member, Comment parentComment) {
        Comment comment = new Comment();
        comment.content = content;
        comment.setPost(post);
        comment.setMember(member);
        comment.setParentComment(parentComment);
        return comment;
    }

}
