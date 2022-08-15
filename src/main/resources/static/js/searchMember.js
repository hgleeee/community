$(document).ready(function() {
    $('#memberList').empty();
})

function getMemberList() {
    const refer = $('#search').val();
    $.ajax({
        type: 'GET',
        url: '/admin/authorize/getMemberList',
        data: {refer},
        success: function(result) {
            $('#memberList').empty()
            if (result.length > 0) {
                for (let i = 0; i < result.length; i++) {
                    let id = result[i].id;
                    let loginId = result[i].loginId;
                    let name = result[i].name;
                    let role = result[i].role;
                    getMemberHtml(id, loginId, name, role);
                }
            } else {
                $("#memberList").append("해당되는 구성원이 없습니다.");
            }
        },
        error: function(result) {
        },
        complete: function() {
        }
    })
}

function getMemberHtml(id, loginId, name, role) {
    let memberHtml = "<a href='/admin/authorize/" + id + "'><ul class='memberList'> <li class='loginId'>" +
                loginId + "</li><li class='name'>" +
                name + "</li><li class='memberGrade'>" +
                role + "</li></ul></a>";
    $("#memberList").append(memberHtml);
}