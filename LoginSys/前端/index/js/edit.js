/*编辑按钮*/
//1.编辑按钮绑定点击事件
$(document).on("click",".edit_btn",function(){
    //弹出模态框并显示信息
    //alert("edit");

    //清空表单数据和状态
    $("#empUpdateModal form")[0].reset();
    var changeStatus = show_validate_status();
    $('#empUpdateModal form :text').each(function(){
        changeStatus.reset(this);
        //alert($(this).attr("id"));
    });
    $("empUpdateModal form :input[name='email']").each(function(){
        changeStatus.reset(this);
        //alert($(this).attr("id"));
    });

    //发送Ajax请求，查询员工信息并显示
    getEmp($(this).attr("edit_id"));
    //传递员工id给更新按钮
    $("#emp_update_btn").attr("edit_id",$(this).attr("edit_id"));
    //发送Ajax请求，查出部门信息，显示在下拉列表
    getDepts("#dept_update_select");
    //弹出modal
    $('#empUpdateModal').modal({
        backdrop:"static"
    });
});

//2.校验邮箱
$("email_update_input").change(function () {
    //1.拿到要校验的值
    var email = $("email_update_input").val();
    //2.正则表达式匹配
    var regEmail = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    var changeStatus = show_validate_status();
    if (!regEmail.test(email)) {
        //alert("请输入正确的邮箱");
        changeStatus.error("#email_update_input", "请输入正确的邮箱");
        $("#emp_update_btn").attr("email_validate", "failed");
    } else {
        changeStatus.success("#email_update_input", "");
        $("#emp_update_btn").attr("email_validate", "success");
    };

});

//2.点击更新按钮更新信息
$("#emp_update_btn").click(function () {
    if($("#emp_update_btn").attr("email_validate") == "failed"){
        return false;
    }else{
        //alert($("#empUpdateModal form").serialize()+"&_method=PUT");
        $.ajax({
            url:basePath+"/emp/"+$(this).attr("edit_id"),
            // type:"POST",
            type:"PUT",
            data:$("#empUpdateModal form").serialize(),
            //data:$("#empUpdateModal form").serialize()+"&_method=PUT",
            success:function(result){
                //alert(result.msg);
                //关闭modal
                $("#empUpdateModal").modal("hide");
                //回到当前页
                to_page(currentPage);
            }
        });
    }
});