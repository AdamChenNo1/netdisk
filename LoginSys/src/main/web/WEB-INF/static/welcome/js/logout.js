$("#logout").on('shown.bs.modal', function () {
    var user = $("#userText").attr("user");
    $("#logoutBtn").click(function () {
        $.ajax({
            url:basePath + 'logout',
            type:"GET",
            data:"user="+user,
            success:function () {
                window.location.href= basePath;
            }
        })
    })
})