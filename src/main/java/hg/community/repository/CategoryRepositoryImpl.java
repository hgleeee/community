package hg.community.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.domain.QCategory;
import hg.community.dto.CategoryDto;
import hg.community.dto.MainCategoryNoticeIdDto;
import hg.community.dto.QCategoryDto;
import hg.community.dto.QMainCategoryNoticeIdDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hg.community.domain.QCategory.*;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CategoryDto> findAllOrderByCreatedDateAsc() {
        QCategory parent = new QCategory("p");
        QCategory child = new QCategory("c");
        return queryFactory
                .select(new QCategoryDto(
                        child.id,
                        child.name,
                        child.urlName,
                        child.depth,
                        child.parentCategory.id,
                        child.parentCategory.name
                ))
                .from(child)
                .leftJoin(child.parentCategory, parent)
                .orderBy(child.createdDateTime.asc())
                .fetch();
    }

    @Override
    public Optional<CategoryDto> findOneByUrlName(String urlName) {
        QCategory parent = new QCategory("p"), child = new QCategory("c");
        return Optional.ofNullable(queryFactory
                .select(new QCategoryDto(
                        child.id,
                        child.name,
                        child.urlName,
                        child.depth,
                        child.parentCategory.id,
                        child.parentCategory.name
                ))
                .from(child)
                .leftJoin(child.parentCategory, parent)
                .where(child.depth.eq(1).and(child.urlName.eq(urlName)))
                .fetchOne());
    }

    @Override
    public List<CategoryDto> findSubCategoriesByMainCategoryOrderByCreatedTimeAsc(String mainCategoryName) {
        QCategory parent = new QCategory("p"), child = new QCategory("c");
        return queryFactory
                .select(new QCategoryDto(
                        child.id,
                        child.name,
                        child.urlName,
                        child.depth,
                        child.parentCategory.id,
                        child.parentCategory.name
                ))
                .from(child)
                .leftJoin(child.parentCategory, parent)
                .where(child.parentCategory.urlName.eq(mainCategoryName).and(child.depth.eq(2)))
                .orderBy(child.createdDateTime.asc())
                .fetch();
    }

    @Override
    public List<MainCategoryNoticeIdDto> findMainCategoryNoticeId() {
        QCategory parent = new QCategory("p"), child = new QCategory("c");
        return queryFactory
                .select(new QMainCategoryNoticeIdDto(
                        child.parentCategory.name,
                        child.id
                ))
                .from(child)
                .leftJoin(child.parentCategory, parent)
                .where(child.urlName.eq("notice").and(child.depth.eq(2)))
                .orderBy(child.createdDateTime.asc())
                .fetch();
    }
}
