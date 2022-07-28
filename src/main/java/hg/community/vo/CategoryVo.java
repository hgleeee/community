package hg.community.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryVo {

    private Long id;
    private String name;
    private String urlName;
    private List<CategoryVo> subCategory = new ArrayList<>();

    public CategoryVo(Long id, String name, String urlName) {
        this.id = id;
        this.name = name;
        this.urlName = urlName;
    }

    public void beSub(CategoryVo categoryVo) {
        this.subCategory.add(categoryVo);
    }
}
