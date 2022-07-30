package hg.community.service;

import hg.community.PagingConst;
import hg.community.domain.Comment;
import hg.community.domain.Post;
import hg.community.domain.member.Member;
import hg.community.dto.CommentDto;
import hg.community.dto.CommentRegisterDto;
import hg.community.repository.CommentRepository;
import hg.community.repository.MemberRepository;
import hg.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long save(CommentRegisterDto commentRegisterDto) {
        Optional<Post> optionalPost = postRepository.findById(commentRegisterDto.getPostId());
        Optional<Member> optionalMember = memberRepository.findByLoginId(commentRegisterDto.getLoginId());
        if (optionalPost.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 글입니다.");
        }
        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        Comment comment = Comment.createComment(commentRegisterDto.getContent(), optionalPost.get(), optionalMember.get());
        return commentRepository.save(comment).getId();
    }

    @Override
    public List<CommentDto> findCommentOrderByTimeDesc(Long postId) {
        return commentRepository.findCommentOrderByTimeDesc(postId);
    }
}
