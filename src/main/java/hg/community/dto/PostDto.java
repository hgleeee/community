package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createTime;
    private int views;
    private int likeNum;
    private int dislikeNum;
    private String mainCategoryUrlName;
    private String mainCategoryName;

    @QueryProjection
    public PostDto(Long id, String title, String content, String nickname, LocalDateTime createTime,
                          int views, int likeNum, int dislikeNum, String mainCategoryUrlName, String mainCategoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createTime = createTime;
        this.views = views;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
        this.mainCategoryUrlName = mainCategoryUrlName;
        this.mainCategoryName = mainCategoryName;
    }
}
