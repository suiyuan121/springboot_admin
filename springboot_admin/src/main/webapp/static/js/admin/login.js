
require([
    'jquery',
    'ui',
    'services',
    'adminApi',
    'validateExpand'
],function(
    $,
    ui,
    services,
    adminApi,
    validateExpand
){
    var page = {
        init: function(){
            var _this = this;
            _this.validate();
        },
        validate:function(){
            //验证
            $("#form-login").validate({
                ignore: "",
                rules: {
                    account: {
                        required: true,
                        userName: true
                    },
                    password: {
                        required: true,
                        passWord: true
                    }
                },
                messages: {
                    account: {
                        required: "请填写用户名"
                    },
                    password: {
                        required: "请填写密码",
                        passWord: '请输入6-16位密码'
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.closest("dl"));
                },
                submitHandler: function() {
                    var account= $.trim($('#account').val()),
                        password = $.trim($('#password').val());
                    var remember = $('input[name="rememberPassword"]:checked').length > 0 ? 1 : 0;

                    var formData = {
                        account: account,
                        password:password,
                        remember:remember
                    };
                    adminApi.addModelMatching(formData, function(code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            location.href = "/account/signout/?next=" + encodeURI(location.href);
                        } else {
                            $(".confrm-password").append('<label class="error">' + msg + '</label>');
                        }
                    });
                }
            });

            //定义表单验证
            $('#success-password').click(function() {
                $('#form-login').submit();
            });

            //键盘enter操作
            $("#account").keypress(function(e) {
                if ( e.which == 13 ){
                    $("#password").focus();
                }
            });
            $("#password").keypress(function(e) {
                if ( e.which == 13 ){
                    $("#success-password").focus().click();
                }
            });
        }
    };
    page.init();
});

