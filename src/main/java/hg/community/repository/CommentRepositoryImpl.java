package hg.community.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.domain.QComment;
import hg.community.domain.QPost;
import hg.community.domain.member.QMember;
import hg.community.dto.CommentDto;
import hg.community.dto.QCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static hg.community.domain.QComment.*;
import static hg.community.domain.QPost.*;
import static hg.community.domain.member.QMember.*;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommentDto> findCommentOrderByTimeDesc(Long postId) {
        return queryFactory
                .select(new QCommentDto(
                        comment.content,
                        comment.member.id,
                        comment.member.nickname,
                        comment.likeNum,
                        comment.createdDateTime
                ))
                .from(comment)
                .join(comment.member, member)
                .join(comment.post, post)
                .where(comment.post.id.eq(postId))
                .fetch();
    }

}
