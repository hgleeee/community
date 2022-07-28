package hg.community.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.SearchCondition;
import hg.community.domain.QCategory;
import hg.community.dto.PostPreviewDto;
import hg.community.dto.QPostPreviewDto;
import hg.community.enumtype.SearchTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static hg.community.domain.QCategory.*;
import static hg.community.domain.QPost.*;
import static hg.community.domain.member.QMember.*;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostPreviewDto> findBestPostOrderByBestTime(Pageable pageable, String categoryName, SearchCondition searchCondition) {
        List<PostPreviewDto> content = queryFactory
                .select(new QPostPreviewDto(
                        post.id,
                        post.title,
                        post.member.nickname,
                        post.createdDateTime,
                        post.views,
                        post.likeNum,
                        post.category.name
                ))
                .from(post)
                .join(post.member, member)
                .join(post.category, category)
                .where(
                        post.likeNum.goe(100),
                        post.category.name.eq(categoryName),
                        searchConditionFit(searchCondition)
                )
                .orderBy(post.hotIssueTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .join(post.member, member)
                .join(post.category, category)
                .where(
                        post.category.name.eq(categoryName),
                        searchConditionFit(searchCondition)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PostPreviewDto> findPostByCategoryOrderByTimeDesc(Pageable pageable, String categoryUrl, SearchCondition searchCondition) {
        QCategory depthTwo = new QCategory("t");
        QCategory depthOne = new QCategory("o");
        List<PostPreviewDto> content = queryFactory
                .select(new QPostPreviewDto(
                        post.id,
                        post.title,
                        post.member.nickname,
                        post.createdDateTime,
                        post.views,
                        post.likeNum,
                        post.category.name
                ))
                .from(post)
                .join(post.category, depthTwo)
                .join(post.category.parentCategory, depthOne)
                .join(post.member, member)
                .orderBy(post.createdDateTime.desc())
                .where(
                        post.category.parentCategory.urlName.eq(categoryUrl),
                        searchConditionFit(searchCondition)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .join(post.category, depthTwo)
                .join(post.category.parentCategory, depthOne)
                .join(post.member, member)
                .where(
                        post.category.parentCategory.urlName.eq(categoryUrl),
                        searchConditionFit(searchCondition)
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchConditionFit(SearchCondition searchCondition) {
        String keyword = searchCondition.getSearchKeyword();
        if (!StringUtils.hasText(keyword) || searchCondition.getSearchTarget().equals(null)) {
            return null;
        }
        if (searchCondition.getSearchTarget().equals(SearchTarget.CONTENT)) {
            return post.content.contains(keyword);
        }
        if (searchCondition.getSearchTarget().equals(SearchTarget.TITLE)) {
            return post.title.contains(keyword);
        }
        if (searchCondition.getSearchTarget().equals(SearchTarget.NICKNAME)) {
            return post.member.nickname.contains(keyword);
        }
        return post.title.contains(keyword).or(post.content.contains(keyword));
    }


}
