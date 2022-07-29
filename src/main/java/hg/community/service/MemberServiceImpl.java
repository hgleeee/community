package hg.community.service;

import hg.community.domain.member.GeneralUser;
import hg.community.domain.member.Member;
import hg.community.dto.MemberDto;
import hg.community.dto.MemberRegisterDto;
import hg.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void register(MemberRegisterDto memberRegisterDto) {
        if (!memberRepository.findByLoginId(memberRegisterDto.getLoginId()).isEmpty()) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
        if (memberRepository.findMemberNumBySSR(memberRegisterDto.getFrontSixSSR(), memberRegisterDto.getEndSevenSSR()) != 0) {
            throw new IllegalStateException("이미 가입하신 회원입니다.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberRegisterDto.setPassword(passwordEncoder.encode(memberRegisterDto.getPassword()));
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
}
