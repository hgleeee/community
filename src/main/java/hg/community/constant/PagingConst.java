package hg.community.constant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PagingConst {

    public static final int PAGE_LIMIT = 10; // 한 페이지에 보여줄 글 갯수
    public static final int BLOCK_LIMIT = 10; // 한 화면에 페이지 갯수
    public static final int COMMENT_PAGE_LIMIT = 50; // 한 화면에 보여줄 댓글 갯수

    public static int startPage(Pageable pageable) {
        return (((int)(Math.ceil((double)pageable.getPageNumber()/ PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT+1;
    }

    public static int endPage(int startPage, Page contents) {
        return (startPage+PagingConst.BLOCK_LIMIT-1 < contents.getTotalPages()) ? startPage+PagingConst.BLOCK_LIMIT-1 : contents.getTotalPages();
    }
}
