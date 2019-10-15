$('#register').on('shown.bs.modal', function () {
    $('#register_form').bootstrapValidator({
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
                        message: "用户名已经存在",
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
                                        exs = true;
                                    else if (result.code == 200)
                                        exs = false;
                                }
                            );
                            return exs == true;
                        }

                    }
                }
            },
            password: {
                enabled: false,
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {
                        field: 'password_confirm',
                        message: '两次密码输入不一致'
                    }
                }
            },
            password_confirm: {
                enabled: false,
                validators: {
                    notEmpty: {
                        message: '再次输入密码不能为空'
                    },
                    identical: {
                        field: 'password',
                        message: '两次密码输入不一致'
                    }
                }
            }
        }
    })

        .on('blur', '[name="password"]', function () {
            var isEmpty = $(this).val() == '';
            $('#register_form').bootstrapValidator('enableFieldValidators', 'password', !isEmpty)
                .bootstrapValidator('enableFieldValidators', 'password_confirm', !isEmpty);

            // Revalidate the field when user start typing in the password field
            if ($(this).val().length == 1) {
                $('#register_form').bootstrapValidator('validateField', 'password')
                    .bootstrapValidator('validateField', 'password_confirm');
            }
        })

        .on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $("#register_form");
            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');
            var name = bv.getFieldElements('name').val();
            var password = bv.getFieldElements('password').val();
            // Use Ajax to submit form data
            $.ajax({
                url: basePath + 'register',
                data: "name="+name+"&password="+password,
                type: 'POST',
                dataType:'json',
                success: function (result) {
                    if (result.code == 100){
                        $('#register').modal('hide');
                        $('#login').modal('show');

                    } else {
                        if (undefined != result.extend.errorFields.name) {
                            $form.bootstrapValidator('updateStatus', 'name', 'INVALID ')
                                .bootstrapValidator('updateMessage', 'name', '用户名必须是2-5位中文或者6-16位字母和数字');
                        }
                    }
                }
            });
        })
})
$('#register').on('hide.bs.modal', function () {
    $('#register_form').data('bootstrapValidator').resetForm(true);
})