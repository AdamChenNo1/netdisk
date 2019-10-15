//在刷新或关闭时调用的事件
$(window).bind('beforeunload',function() {
    var user = $("#userText").attr("user");
    console.log("用户："+ user);
    if(user!=null){
        $.ajax({
            url: basePath + 'logout',
            type: "GET",
            data: "user=" + user,
            success: function () {
                window.location.href = basePath;
            }
        })
    }
});