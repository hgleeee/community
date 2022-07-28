package hg.community.service;

import hg.community.domain.member.Member;
import hg.community.dto.MemberDto;
import hg.community.dto.MemberRegisterDto;

public interface MemberService {

    public void register(MemberRegisterDto memberDto);
    public MemberDto findMemberDtoByLoginId(String loginId);
}
