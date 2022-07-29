package hg.community.service;

import hg.community.PagingConst;
import hg.community.SearchCondition;
import hg.community.domain.Category;
import hg.community.domain.Post;
import hg.community.domain.member.Member;
import hg.community.dto.PostCreateDto;
import hg.community.dto.PostDto;
import hg.community.dto.PostPreviewDto;
import hg.community.repository.CategoryRepository;
import hg.community.repository.MemberRepository;
import hg.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Long save(PostCreateDto postCreateDto) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(postCreateDto.getLoginId());
        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        Optional<Category> optionalCategory = categoryRepository.findById(postCreateDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 카테고리입니다.");
        }
        if (optionalCategory.get().getDepth() != 2) {
            throw new IllegalStateException("잘못된 요청입니다.");
        }
        Post post = Post.createPost(postCreateDto.getTitle(), postCreateDto.getContent(),
                optionalMember.get(), optionalCategory.get());
        return postRepository.save(post).getId();
    }

    @Override
    public Page<PostPreviewDto> findPostByCategoryOrderByTime(Pageable pageable, String categoryUrl, SearchCondition searchCondition) {
        int pageIdx = pageable.getPageNumber()-1;
        pageable = PageRequest.of(pageIdx, PagingConst.PAGE_LIMIT);
        return postRepository.findPostByCategoryOrderByTimeDesc(pageable, categoryUrl, searchCondition);
    }

    @Override
    public PostDto findOneById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 글입니다.");
        }
        return optionalPost.get().toPostDto();
    }
}
