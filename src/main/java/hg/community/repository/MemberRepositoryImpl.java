package hg.community.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.domain.Member;
import hg.community.domain.QMember;
import hg.community.dto.IdNameGradeOfMemberDto;
import hg.community.dto.MemberDto;
import hg.community.dto.QIdNameGradeOfMemberDto;
import hg.community.dto.QMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static hg.community.domain.QMember.member;


@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<MemberDto> findMemberDtoByLoginId(String loginId) {
        return Optional.ofNullable(queryFactory.select(new QMemberDto(
                    member.name,
                    member.nickname,
                    member.birthday,
                    member.loginId,
                    member.password,
                    member.role
                ))
                .from(member)
                .where(member.loginId.eq(loginId))
                .fetchOne());
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.loginId.eq(loginId))
                .fetchOne());
    }

    @Override
    public Long findMemberNumBySSR(String sixSSR, String sevenSSR) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(member.frontSixSSR.eq(sixSSR).and(member.endSevenSSR.eq(sevenSSR)))
                .fetchOne();
    }

    @Override
    public List<IdNameGradeOfMemberDto> findIdNameGradeByRefer(String refer) {
        return queryFactory
                .select(new QIdNameGradeOfMemberDto(
                        member.id,
                        member.loginId,
                        member.name,
                        member.role
                ))
                .from(member)
                .where(member.loginId.contains(refer).or(member.name.contains(refer)))
                .fetch();
    }
}
