package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostSimplePreviewDto {

    private Long id;
    private String title;
    private int likeNum;

    @QueryProjection
    public PostSimplePreviewDto(Long id, String title, int likeNum) {
        this.id = id;
        this.title = title;
        this.likeNum = likeNum;
    }
}
