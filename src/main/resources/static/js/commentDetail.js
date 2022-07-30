$(document).ready(function() {
    getCommentList();
})

function getCommentList() {
    const idx = $('input[name=idx]').val();
    $.ajax({
        type: 'GET',
        url: '/comment/list',
        data: {idx},
        success: function(result) {
            var str = "<hr/>";
            var cnt = result.length;

            if (cnt > 0) {
                for (var i = 0; i < cnt; i++) {
                    str += "<div>";
                    str += "<div class='space'><a class='nickname'>" + result[i].memberNickname+"</a>";
                    str += "<span>" + result[i].registerTime + " </span>&nbsp;&nbsp;"
                    str += "<span> 공감 수: " + result[i].likeNum + "</span>"
                    str += "</div>";
                    str += result[i].content + "</div>";
                }
            } else {
                str += "<div>";
                str += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
                str += "</table></div>";
                str += "</div>";
            }
            $("#cnt").html(cnt);
            $("#comment").html(str);
        },
        error: function(result) {
        },
        complete: function() {
        }
    })
}

function timeTo