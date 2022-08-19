package hg.community.domain;

import hg.community.domain.baseentity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends TimeBaseEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String urlName;
    private int depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategory = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    /** 연관관계 편의 메서드 시작 */
    public void setParentCategory(Category category) {
        this.parentCategory = category;
        parentCategory.getSubCategory().add(this);
    }
    /** 연관관계 편의 메서드 끝 */

    /** 깊이가 0인, 메인 카테고리 */
    public static Category createCategory(String name, String urlName) {
        Category category = new Category();
        category.name = name;
        category.urlName = urlName;
        category.depth = 0;
        return category;
    }

    /** 서브 카테고리, 깊이가 2인 카테고리에서 게시물 포함 */
    public static Category createCategory(String name, String urlName, Category parentCategory) {
        Category category = new Category();
        category.name = name;
        category.urlName = urlName;
        category.depth = parentCategory.getDepth()+1;
        category.setParentCategory(parentCategory);
        return category;
    }

}
