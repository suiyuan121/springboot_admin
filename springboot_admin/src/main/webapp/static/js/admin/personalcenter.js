require([
    'jquery',
    'admin/common',
    'template',
    'ui',
    'services',
    'adminApi',
    'validateExpand',
    'fileupload',
    'iframeTransport',
    'fileuploadProcess',
    'fileuploadValidate',
], function ($,
             comJs,
             tpls,
             ui,
             services,
             adminApi,
             validateExpand,
             fileupload) {
    var page = {
        init: function () {
            var _this = this;
            comJs.header();
            _this.validate();
        },

        validate: function () {
            //验证
            $("#form-personal-center").validate({
                ignore: "",
                rules: {
                    firstname: {
                        required: true
                    },
                    lastname: {
                        required: true
                    },
                    "profile.mobile": {
                        required: true,
                        imobile: true
                    },
                    oldPassword: {
                        required: true
                    },
                    newPassword: {
                        required: true,
                        passWord: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: ".newPassword"
                    }
                },
                messages: {
                    firstname: {
                        required: "请填写姓氏"
                    },
                    lastname: {
                        required: "请填写名字"
                    },
                    "profile.mobile": {
                        required: "请填写手机号码"
                    },
                    oldPassword: {
                        required: "请填写原始密码"
                    },
                    newPassword: {
                        required: "请填写新密码"
                    },
                    confirmPassword: {
                        required: "请填写确认密码",
                        equalTo: "两次密码不一致"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },
                /*   submitHandler: function() {
                       var account = $.trim($('.account').val()),
                           title = $.trim($('.title').val()),
                           tel = $.trim($('.tel').val()),
                           oldPassword = $.trim($('.oldPassword').val()),
                           newPassword = $.trim($('.newPassword').val());

                       var formData = {
                           account: account,
                           title: title,
                           tel: tel,
                           old_password: oldPassword,
                           new_password: newPassword
                       };
                       adminApi.changePersonalCenter(formData, function(code, msg, data) {
                           if (code === services.CODE_SUCC) {
                               location.href = "/account/signout/?next=" + encodeURI(location.href);
                           } else {
                               $(".confrm-password").append('<label class="error">' + msg + '</label>');
                           }
                       });
                   }*/
            });

            //定义表单验证
            $('#complete').click(function (event) {
                $('#form-personal-center').submit();
            });

            //键盘enter操作
            $('.tel').on('keypress', function (e) {
                if (e.which == 13) {
                    $(".oldPassword").focus();
                }
            });
            $('.oldPassword').on('keypress', function (e) {
                if (e.which == 13) {
                    $(".newPassword").focus();
                }
            });
            $('.newPassword').on('keypress', function (e) {
                if (e.which == 13) {
                    $(".confirmPassword").focus();
                }
            });
            $('.confirmPassword').on('keypress', function (e) {
                if (e.which == 13) {
                    $("#complete").click();
                }
            });

        }
    };
    page.init();
});
