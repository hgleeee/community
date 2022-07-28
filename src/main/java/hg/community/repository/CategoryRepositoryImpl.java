package hg.community.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.domain.QCategory;
import hg.community.dto.CategoryDto;
import hg.community.dto.QCategoryDto;
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
        return Optional.ofNullable(queryFactory
                .select(new QCategoryDto(
                        category.id,
                        category.name,
                        category.urlName,
                        category.depth,
                        category.parentCategory.id,
                        category.parentCategory.name
                ))
                .from(category)
                .leftJoin(category.parentCategory, category)
                .where(category.depth.eq(1).and(category.urlName.eq(urlName)))
                .fetchOne());
    }
}
