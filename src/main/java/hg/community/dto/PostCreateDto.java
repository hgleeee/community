package hg.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class PostCreateDto {

    @NotEmpty
    private Long categoryId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
