function clickLikeOrDislike(isLike) {

    $.ajax({
        type: 'POST',
        url: '/post/' + id + '/click',
        data: {isLike},
        success: function(result) {
            if (isLike) {
                $("#likeNum").html(result);
                $("#titleLike").html('공감 수 : ' + result);
                sendName([[${loginId}]] ,[[${post.id}]]);
            } else {
                $("#dislikeNum").html(result);
            }

        },
        error: function(result) {
        },
        complete: function() {
        }
    })
}