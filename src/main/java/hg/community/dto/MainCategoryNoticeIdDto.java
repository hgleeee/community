package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

@Data
public class MainCategoryNoticeIdDto {

    private String mainCategoryName;
    private Long noticeId;

    @QueryProjection
    public MainCategoryNoticeIdDto(String mainCategoryName, Long noticeId) {
        this.mainCategoryName = mainCategoryName;
        this.noticeId = noticeId;
    }
}
