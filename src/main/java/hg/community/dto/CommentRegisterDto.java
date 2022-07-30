package hg.community.dto;

import lombok.Data;

@Data
public class CommentRegisterDto {
    private String loginId;
    private Long postId;
    private String content;

    public CommentRegisterDto(String loginId, Long postId, String content) {
        this.loginId = loginId;
        this.postId = postId;
        this.content = content;
    }
}
