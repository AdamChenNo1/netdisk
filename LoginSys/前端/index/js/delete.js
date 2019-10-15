/*删除按钮*/
//1.删除按钮绑定点击事件
$(document).on("click",".delete_btn",function(){
    //弹出是否删除确认框
    //alert($(this).parents("tr").find("td:eq(1)").text());
    var empName = $(this).parents("tr").find("td:eq(2)").text();
    var empId = $(this).attr("delete_id");
    if(confirm("确认删除【"+empName+"】吗？")){
        $.ajax({
            url:basePath+"/emp/"+empId,
            type:"DELETE",
            success:function (result) {
                //alert(result.msg);
                to_page(currentPage);
            }
        });
    }
});
//2.全选/全不选
$("#check_all").click(function () {
    //prop用于获取原生属性，attr用于获取自定义属性
    //alert($(this).prop("checked"));
    $(".check_item").prop("checked",$(this).prop("checked"));
});
//3.选择
$(document).on("click",".check_item",function () {
    //判断当前选择元素个数
    var flag = $(".check_item:checked").length==$(".check_item").length;
    //console.log(flag);
    $("#check_all").prop("checked",flag);

});
//3.批量删除
$("#empMultiDeleteBtn").click(function () {
    var empNames = "";
    var del_idstr = "";
    $.each($(".check_item:checked"),function () {
        empNames +=  $(this).parents("tr").find("td:eq(2)").text()+",";
        del_idstr += $(this).parents("tr").find("td:eq(1)").text()+"-"
    });
    //去除末尾的“，”
    empNames = empNames.substring(0,empNames.length-1);
    //去除末尾的“-”
    del_idstr = del_idstr.substring(0,del_idstr.length-1);
    if(confirm("确认删除【"+empNames+"】吗？")){
        $.ajax({
            url:basePath+"/emp/"+del_idstr,
            type:"DELETE",
            success:function (result) {
                // alert(result.msg);
                to_page(currentPage);
                $("#check_all").prop("checked",false);
            }
        });
    }
});