/*删除按钮*/
//1.删除按钮绑定点击事件
$(document).on("click",".delete_btn",function(){
    //弹出是否删除确认框
    var fileName = $(this).attr("delete_name");
    if(confirm("确认删除【"+fileName+"】吗？")){
        $.ajax({
            url:getRootPath()+"/netdisk/home/"+fileName,
            type:"DELETE",
            success:function (result) {
                buildFileList(result);
            }
        });
    }
});
