package hg.community.config.websocket;

import hg.community.dto.PostDto;
import hg.community.service.PostService;
import hg.community.vo.Hello;
import hg.community.vo.Inform;
import hg.community.vo.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EchoController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final PostService postService;

    @MessageMapping("/recommend")
    public void sendRecommendMessage(Inform inform) throws Exception {
        PostDto findOne = postService.findOneById(inform.getPostId());

        log.info(findOne.getLoginId());

        simpMessagingTemplate.convertAndSendToUser(findOne.getLoginId(), "/topic/public",
                new Message(MyParser.parse(Type.RECOMMEND, inform.getSenderId(), findOne.getTitle()), "/post/" + findOne.getId()));
    }

    @MessageMapping("/comment")
    public void sendCommentMessage(@DestinationVariable String memberId, Inform inform) throws Exception {
        PostDto findOne = postService.findOneById(inform.getPostId());

        simpMessagingTemplate.convertAndSendToUser(findOne.getLoginId(), "/topic/public",
                new Message(MyParser.parse(Type.COMMENT, inform.getSenderId(), findOne.getTitle()), "/post/" + findOne.getId()));
    }


    enum Type {
        RECOMMEND, COMMENT
    }

    static class MyParser {
        static String parse(Type type, String senderNickname, String postTitle) {
            if (type == Type.RECOMMEND) {
                return senderNickname + "님이 " + postTitle + "글에 추천을 눌렀습니다!";
            }
            return senderNickname + "님이 " + postTitle + "글에 댓글을 달았습니다!";
        }
    }

}
