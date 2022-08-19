package hg.community.vo;

import lombok.Data;

@Data
public class Hello {

    private String content;

    public Hello() {
    }

    public Hello(String content) {
        this.content = content;
    }
}
