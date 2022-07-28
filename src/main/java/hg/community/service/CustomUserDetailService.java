package hg.community.service;

import hg.community.config.Role;
import hg.community.domain.member.Member;
import hg.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "userDetailService")
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        Member findMember = optionalMember.orElseThrow(() -> {
            throw new UsernameNotFoundException("해당 id의 회원이 없습니다.");
        });

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(findMember.getRole().getValue()));
        findMember.increaseVisitCount();
        return new User(findMember.getLoginId(), findMember.getPassword(), authorities);
    }

}
