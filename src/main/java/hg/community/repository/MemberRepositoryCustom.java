package hg.community.repository;

import hg.community.domain.member.Member;
import hg.community.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {

    public Optional<MemberDto> findMemberDtoByLoginId(String loginId);
    public Optional<Member> findByLoginId(String loginId);
    public Long findMemberNumBySSR(String sixSSR, String sevenSSR);
}
