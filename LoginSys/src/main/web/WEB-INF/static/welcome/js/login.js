$('#login').on('shown.bs.modal', function () {
    $('#login_form').bootstrapValidator({
        submitButtons:'[id="loginBtn"]',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                trigger: 'blur',
                verbose: false,
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    regexp: {
                        regexp: /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u4e00-\u9fa5]{2,5})/,
                        message: "用户名可以是2-5位中文或者6-16位字母和数字"
                    },
                    callback: {
                        message: "用户名尚未注册",
                        callback: function (value, validator, $field) {
                            var exs;
                            $.ajax({
                                url: basePath + 'checkuser',
                                type: 'POST',
                                async: false,
                                data: "name=" + value
                            }).done(
                                function (result) {
                                    if (result.code == 100)
                                        exs = false;
                                    else if (result.code == 200)
                                        exs = true;
                                }
                            );
                            return exs == true;
                        },

                    }
                }
            },
            password: {
                trigger: 'blur',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');
            var data = $form.serialize();
            data= decodeURIComponent(data,true);//防止中文乱码

             //将从form中通过$('#form').serialize()获取的值转成json
            data = (function (data) {
                     data=data.replace(/&/g,"\",\"");
                     data=data.replace(/=/g,"\":\"");
                     data="{\""+data+"\"}";
                     return data;
                 })(data);
            // Use Ajax to submit form data
            $.ajax({
                url: basePath + 'login',
                data:data,
                contentType: "application/json",
                type: 'POST',
                dataType:'json',
                success: function (result) {
                    if (result.code == 100){
                        $('#login').modal('hide');
                        $("#userText").attr("user",result.extend.index.userName);
                        $("#netdisk").prop("href",result.extend.index.url);
                        $("#netdisk").prop("target","_blank");
                    } else {
                        $('#login').modal('hide');
                        $("#errorInfo").text(result.extend.error);
                        $('#login_error').modal('show');
                    }
                }
            });
        });
})

$('#login').on('hide.bs.modal', function () {
    $('#login_form').data('bootstrapValidator').resetForm(true);
})


