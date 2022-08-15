package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String content;
    private Long memberId;
    private Integer memberLevel;
    private String memberNickname;
    private Integer likeNum;
    private LocalDateTime registerTime;

    @QueryProjection
    public CommentDto(String content, Long memberId, Integer memberLevel, String memberNickname, Integer likeNum, LocalDateTime registerTime) {
        this.content = content;
        this.memberId = memberId;
        this.memberLevel = memberLevel;
        this.memberNickname = memberNickname;
        this.likeNum = likeNum;
        this.registerTime = registerTime;
    }
}
