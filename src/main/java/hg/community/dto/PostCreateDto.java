package hg.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDto {

    private Long categoryId;
    private String loginId;
    private String title;
    private String content;
}
