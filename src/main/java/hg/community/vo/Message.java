package hg.community.vo;

import lombok.Data;

@Data
public class Message {

    private String content;
    private String path;

    public Message(String content, String path) {
        this.content = content;
        this.path = path;
    }
}
