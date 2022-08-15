package hg.community.controller;

import hg.community.dto.CommentDto;
import hg.community.dto.CommentRegisterDto;
import hg.community.service.CommentService;
import hg.community.util.SessionUtils;
import hg.community.vo.MyAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public String insertReview(@RequestParam("idx") Long postId,
                               @RequestParam("content") String content,
                               HttpSession session) {
        MyAuthentication auth = SessionUtils.getLoginMember(session);
        if (auth == null) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        commentService.save(new CommentRegisterDto(auth.getLoginId(), postId, content));
        return "redirect:/post/" + postId;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<CommentDto> getCommentList(@RequestParam("idx") Long postId) {
        return commentService.findCommentOrderByTimeDesc(postId);
    }
}
