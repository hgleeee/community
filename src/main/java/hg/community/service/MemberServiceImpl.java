package hg.community.service;

import hg.community.domain.Member;
import hg.community.dto.IdNameGradeOfMemberDto;
import hg.community.dto.MemberDto;
import hg.community.dto.MemberRegisterDto;
import hg.community.repository.MemberRepository;
import hg.community.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto login(String loginId, String password) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("해당 아이디가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if (!PasswordEncryptor.isMatch(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return MemberDto.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .birthday(member.getBirthday())
                .role(member.getRole())
                .build();
    }

    @Transactional
    @Override
    public void register(MemberRegisterDto memberRegisterDto) {
        if (!memberRepository.findByLoginId(memberRegisterDto.getLoginId()).isEmpty()) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
        if (memberRepository.findMemberNumBySSR(memberRegisterDto.getFrontSixSSR(), memberRegisterDto.getEndSevenSSR()) != 0) {
            throw new IllegalStateException("이미 가입하신 회원입니다.");
        }
        memberRegisterDto.setPassword(PasswordEncryptor.encrypt(memberRegisterDto.getPassword())); // 비밀번호 encrypt
        memberRepository.save(memberRegisterDto.toEntity());
    }

    @Override
    public MemberDto findMemberDtoByLoginId(String loginId) {
        Optional<MemberDto> optionalMemberDto = memberRepository.findMemberDtoByLoginId(loginId);
        if (optionalMemberDto.isEmpty()) {
            throw new IllegalStateException("해당 아이디를 가진 회원이 없습니다.");
        }
        return optionalMemberDto.get();
    }
    @Override
    public List<IdNameGradeOfMemberDto> findIdNameGradeByRefer(String refer) {
        return memberRepository.findIdNameGradeByRefer(refer);
    }

    @Override
    public IdNameGradeOfMemberDto findMemberById(Long id) {
       return memberRepository.findById(id).map(m -> new IdNameGradeOfMemberDto(
                m.getId(), m.getLoginId(), m.getName(), m.getRole())).get();
    }

    @Transactional
    @Override
    public String updateMemberGrade(Long id, String role) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("해당 회원이 없습니다.");
        }
        optionalMember.get().updateRoleByAdmin(role);
        return role;
    }
}
