$("#register_form").bootstrapValidator({
    message: 'This value is not valid',
        feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        userName_register:{
            validators:{
                notEmpty:{
                    message:"用户名不能为空"
                },
                regexp:{
                    regexp: /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u4e00-\u9fa5]{2,5})/,
                    message: "用户名可以是2-5位中文或者6-16位字母和数字"
                }
            }
        }
    }
});