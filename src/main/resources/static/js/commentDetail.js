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
                    str += "<div class='space'><a class='nickname'>" + levelExp(result[i].memberLevel)
                        + "&nbsp;" + result[i].memberNickname+"</a>";
                    str += "<span>" + timeCompareToNow(result[i].registerTime) + " </span>&nbsp;&nbsp;"
                    str += "<span> 공감 수: " + result[i].likeNum + "</span>"
                    str += "</div>";
                    str += "&nbsp;&nbsp;" + result[i].content + "</div>";
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

function levelExp(level) {
    if (level == 9999) {
        return "Lv. 게시판지기";
    }
    if (level == 99999) {
        return "Lv. 운영자";
    }
    return "Lv. " + level;
}

function timeCompareToNow(time) {
    let now = new Date();
    let time1 = new Date(time);
    if (now.getFullYear() - time1.getFullYear() != 0) {
        return now.getFullYear() - time1.getFullYear() + "년 전";
    }
    if (now.getMonth() - time1.getMonth() != 0) {
        return now.getMonth() - time1.getMonth() + "개월 전";
    }
    if (now.getHours() - time1.getHours() != 0) {
        return now.getHours() - time1.getHours() + "시간 전";
    }
    if (now.getMinutes() - time1.getMinutes() != 0) {
        return now.getMinutes() - time1.getMinutes() + "분 전";
    }
    return now.getSeconds() - time1.getSeconds() + "초 전";
}