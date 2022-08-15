package hg.community.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchTarget {
    TITLE("TITLE"),
    CONTENT("CONTENT"),
    TITLE_CONTENT("TITLE_CONTENT"),
    NICKNAME("NICKNAME");
    private String value;
}
