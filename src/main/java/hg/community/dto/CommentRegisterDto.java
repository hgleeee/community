package hg.community.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentRegisterDto {
    private String loginId;
    private Long postId;
    private String content;
    private Long parentCommentId;

    @Builder
    public CommentRegisterDto(String loginId, Long postId, String content, Long parentCommentId) {
        this.loginId = loginId;
        this.postId = postId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
