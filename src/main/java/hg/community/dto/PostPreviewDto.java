package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostPreviewDto {

    private Long id;
    private String title;
    private int commentNum;
    private String nickname;
    private LocalDateTime createTime;
    private int views;
    private int likeNum;
    private String parentCategory;

    @QueryProjection
    public PostPreviewDto(Long id, String title, int commentNum, String nickname, LocalDateTime createTime,
                          int views, int likeNum, String parentCategory) {
        this.id = id;
        this.title = title;
        this.commentNum = commentNum;
        this.nickname = nickname;
        this.createTime = createTime;
        this.views = views;
        this.likeNum = likeNum;
        this.parentCategory = parentCategory;
    }
}
