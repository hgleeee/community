package hg.community;

import hg.community.enumtype.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SearchCondition {
    SearchTarget searchTarget;
    String searchKeyword;
}
