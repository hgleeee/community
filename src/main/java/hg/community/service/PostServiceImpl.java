package hg.community.service;

import hg.community.constant.BestPostConst;
import hg.community.constant.ExpConst;
import hg.community.constant.PagingConst;
import hg.community.SearchCondition;
import hg.community.domain.Category;
import hg.community.domain.Post;
import hg.community.domain.Member;
import hg.community.dto.PostCreateDto;
import hg.community.dto.PostDto;
import hg.community.dto.PostPreviewDto;
import hg.community.dto.PostSimplePreviewDto;
import hg.community.repository.CategoryRepository;
import hg.community.repository.MemberRepository;
import hg.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<PostPreviewDto> findBestPostOrderByBestTime(Pageable pageable, SearchCondition searchCondition) {
        pageable = PageRequest.of(pageable.getPageNumber()-1, PagingConst.PAGE_LIMIT);
        return postRepository.findBestPostOrderByBestTime(pageable, searchCondition);
    }

    @Transactional
    @Override
    public Long save(PostCreateDto postCreateDto, String loginId) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
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
        optionalMember.get().addExp(ExpConst.postExp);
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

    @Override
    public List<PostSimplePreviewDto> findBestPostByCategoryNameOrderByLikeNum(String mainCategoryUrl, int days) {
        return postRepository.findBestPostByCategoryNameOrderByLikeNum(mainCategoryUrl, days);
    }

    @Transactional
    @Override
    public Integer clickLikeOrDislikeButton(Long postId, Boolean isLike) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new IllegalStateException("해당 글이 없습니다.");
        }
        Post post = optionalPost.get();
        Integer ret = null;
        if (isLike) {
            ret = post.getLikeNum() + 1;
            post.setLikeNum(ret);
        } else {
            ret = post.getDisLikeNum() + 1;
            post.setDisLikeNum(ret);
        }
        if (ret >= BestPostConst.NUM) {
            post.setHotIssueTime(LocalDateTime.now());
        }
        return ret;
    }

    @Override
    public Optional<String> findContentById(Long id) {
        return postRepository.findContentById(id);
    }
}
