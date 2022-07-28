package hg.community.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String urlName;
    private int depth;
    private Long parentCategoryId;
    private String parentCategoryName;

    @QueryProjection
    public CategoryDto(Long id, String name, String urlName, int depth, Long parentCategoryId, String parentCategoryName) {
        this.id = id;
        this.name = name;
        this.urlName = urlName;
        this.depth = depth;
        this.parentCategoryId = parentCategoryId;
        this.parentCategoryName = parentCategoryName;
    }
}
