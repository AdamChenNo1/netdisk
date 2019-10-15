/*新增按钮*/
//点击新增按钮弹出modal
$("#empAddBtn").click(function () {
    //清除表单数据和状态
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

    //发送Ajax请求，查出部门信息，显示在下拉列表
    getDepts("#dept_add_select");
    //弹出modal
    $('#empAddModal').modal({
        backdrop:"static"
    });
});


//校验用户名
$("#empName_add_input").change(function () {
    //1.拿到要校验的值
    var empName = $("#empName_add_input").val();
    //2.正则表达式匹配
    var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u4e00-\u9fa5]{2,5})/;
    var changeStatus = show_validate_status();
    if (!regName.test(empName)) {
        //alert("用户名可以是2-5位中文或者6-16位字母和数字")
        changeStatus.error("#empName_add_input", "用户名可以是2-5位中文或者6-16位字母和数字");
        $("#emp_save_btn").attr("user_validate", "failed");
    }else {
        changeStatus.success("#empName_add_input", "");
        $("#emp_save_btn").attr("user_validate", "success");
        checkUser(empName);
    };
});

//校验邮箱
$("#email_add_input").change(function () {
    //1.拿到要校验的值
    var email = $("#email_add_input").val();
    //2.正则表达式匹配
    var regEmail = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    var changeStatus = show_validate_status();
    if (!regEmail.test(email)) {
        //alert("请输入正确的邮箱");
        changeStatus.error("#email_add_input", "请输入正确的邮箱");
        $("#emp_save_btn").attr("email_validate", "failed");
    } else {
        changeStatus.success("#email_add_input",  "");
        $("#emp_save_btn").attr("email_validate", "success");
    };

});

//校验状态展示
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

//发送Ajax请求检验员工名是否可用
function checkUser(empName) {
    $.ajax({
        url:basePath+"/checkuser",
        type:"POST",
        data:"empName="+empName,
        success:function (result) {
            var changeStatus = show_validate_status();
            if(result.code==100){
                changeStatus.success("#empName_add_input","用户名可用");
                $("#emp_save_btn").attr("user_validate","success");
            }else{
                changeStatus.error("#empName_add_input","用户名已经存在");
                $("#emp_save_btn").attr("user_validate","failed");
            }
        }
    });
}

//新增页面保存按钮
$("#emp_save_btn").click(function () {
    //将填写的表单数据进行校验
    if($("#emp_save_btn").attr("email_validate")=="failed"){
        return false;
    };
    if($("#emp_save_btn").attr("user_validate")=="failed"){
        return false;
    };
    //alert($("#empAddModal form").serialize());
    //发送Ajax请求保存员工
    $.ajax({
        url:basePath+"/emp",
        type:"POST",
        data:$("#empAddModal form").serialize(),
        success:function (result) {
            // alert(result.msg);
            if(result.code == 100) {
                //关闭modal
                $("#empAddModal").modal('hide');
                //来到最后一页
                to_page(totalRecord);
            }else{
                //console.log(result);
                var changeStatus = show_validate_status();
                if(undefined != result.extend.errorFields.empName){
                    changeStatus.error("#empName_add_input", "用户名必须是2-5位中文或者6-16位字母和数字");
                }
                if(undefined != result.extend.errorFields.email){
                    changeStatus.error("#email_add_input", "邮箱格式不正确");
                }
            }
        }
    });
});