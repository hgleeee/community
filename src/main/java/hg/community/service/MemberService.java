package hg.community.service;

import hg.community.dto.IdNameGradeOfMemberDto;
import hg.community.dto.MemberDto;
import hg.community.dto.MemberRegisterDto;

import java.util.List;

public interface MemberService {
    public MemberDto login(String loginId, String password) throws Exception;
    public void register(MemberRegisterDto memberDto);
    public MemberDto findMemberDtoByLoginId(String loginId);
    public List<IdNameGradeOfMemberDto> findIdNameGradeByRefer(String refer);
    public IdNameGradeOfMemberDto findMemberById(Long id);
    public String updateMemberGrade(Long id, String role);
}
