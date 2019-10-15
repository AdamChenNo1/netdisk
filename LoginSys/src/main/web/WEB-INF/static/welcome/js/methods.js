//输入框前端校验
function show_validate_status(){
    return {
        success:function(ele,msg){
            this.reset(ele);
            $(ele).parent().addClass("has-success has-feedback");
            $(ele).next("span").text(msg).next("span").addClass("glyphicon glyphicon-ok form-control-feedback");
        },
        error:function(ele,msg){
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
//输入框后端校验
