package hg.community.vo;

import lombok.Data;

@Data
public class Inform {
    private String senderId;
    private Long postId;

    public Inform() {

    }

    public Inform(String senderId, Long postId) {
        this.senderId = senderId;
        this.postId = postId;
    }
}
