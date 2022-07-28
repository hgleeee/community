package hg.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryCreateDto {

    private String name;
    private String urlName;
    private Long parentCategoryId;
}
