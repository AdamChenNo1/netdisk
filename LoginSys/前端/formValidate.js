$("#empAddBtn").click(function () {
    $("#empAddModal form")[0].reset();
    var changeStatus = show_validate_status();
    $('#empAddModal form :text').each(function(){
        changeStatus.reset(this);
        //alert($(this).attr("id"));
    });
    $("#empAddModal form :input[name='email']").each(function(){
        changeStatus.reset(this);
        //alert($(this).attr("id"));
    });
   
    
    $('#empAddModal').modal({ 
        backdrop: "static"
    });
       
});

$("#empName_input").change(function () {
    //校验用户名
    //1.拿到要校验的值
    var empName = $("#empName_input").val();
    //2.正则表达式匹配
    var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u4e00-\u9fa5]{2,5})/;
    var changeStatus = show_validate_status();
    if (!regName.test(empName)) {
        //alert("用户名可以是2-5位中文或者6-16位字母和数字")
        changeStatus.error("#empName_input", "用户名可以是2-5位中文或者6-16位字母和数字");
        $("#emp_save_btn").attr("user_validate", "failed");
    }else {
        changeStatus.success("#empName_input", "");
        $("#emp_save_btn").attr("user_validate", "success");
    };
});
$("#email_input").change(function () {
    //校验用户名
    //1.拿到要校验的值
    var email = $("#email_input").val();
    //2.正则表达式匹配
    var regEmail = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    var changeStatus = show_validate_status();
    if (!regEmail.test(email)) {
        //alert("请输入正确的邮箱");
        changeStatus.error("#email_input", "请输入正确的邮箱");
        $("#emp_save_btn").attr("email_validate", "failed");
    } else {
        changeStatus.success("#email_input",  "");
        $("#emp_save_btn").attr("email_validate", "success");
    };
});

function show_validate_status(){
    return {
        success:function(ele,msg){
            //console.log(ele+" "+msg);
            this.reset(ele);
            $(ele).parent().addClass("has-success has-feedback");
            $(ele).next("span").text(msg).next("span").addClass("glyphicon glyphicon-ok form-control-feedback");
        },
        error:function(ele,msg){
            //console.log(ele+" "+msg);
            this.reset(ele);
            $(ele).parent().addClass("has-error has-feedback");
            $(ele).next("span").text(msg).next("span").addClass("glyphicon glyphicon-remove form-control-feedback");
        },
        reset:function(ele){
            $(ele).parent().removeClass("has-success has-error has-feedback");
            $(ele).next("span").text("").next("span").removeClass("glyphicon glyphicon-remove glyphicon-ok form-control-feedback");
        }
    }
}

$("#emp_save_btn").click(function () {
    //将填写的表单数据进行校验
    if ($("#emp_save_btn").attr("email_validate") == "failed") {
        return false;
    };
    if ($("#emp_save_btn").attr("user_validate") == "failed") {
        return false;
    };
    alert($("#empAddModal form").serialize());
});
