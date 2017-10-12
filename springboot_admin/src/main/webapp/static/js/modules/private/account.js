/*
 * 账号功能模块
 *
 * author: Glen
 * created:　2015/5/22
 */

define([
    'jquery',
    'utils',
    'ui',
    'services',
    'template',
    'icheck'
], function($, utils, ui, services, template) {

    //var debug = location.host.indexOf('.fuwo.com') !== -1 ? false : true;
    var debug = false;

    var _loginState = false,
        _userInfo = null,
        _userDetail = null,
        _role = {
            consumer: '业主',
            designer: '设计师',
            company: '装修公司',
            merchant: '家居建材商'
        };
         
    //取用户信息
    var _getUserInfo = function() {
        var info = $.cookie('userinfo');
        if (info !== null) {
            info = decodeURI(info);
            _userInfo = utils.parseURIParams(info);

            _loginState = true;
            _updateUserBanner();
        }else{
        }
        return _userInfo;
    };

    //取用户全部信息
    var _getUserDetail = function(parameters, callback) {
        if (debug === false && _loginState === false) {
            return;
        }
        if (_userDetail !== null && callback) {
            if (parseInt(parameters.idcard) === 1 && _userDetail.id_card_no === undefined) {} else {
                if (location.pathname.indexOf('/manage/') !== -1) {
                    $('.manage-loading').hide();
                }
                callback(_userDetail);
                return;
            }
        }
        parameters.wealth = 1;
        services.get('account', 'profile', parameters, function(code, msg, data) {
            if (code === services.CODE_SUCC) {
                _userDetail = data;
                if (callback) {
                    callback(data);
                }
            } else {
                ui.alert('error', msg);
            }
        });
    };

    //更新用户状态栏
    var _updateUserBanner = function() {
        if (_loginState === false) {
            return;
        }
        var userData;

        if (debug === true) {
            userData = {
                avatar: "http://static.fuwo.com/upload/28693/701524/avatar/1fcf979cbdb111e4bb0200163e000ee8.jpg",
                message_count: "1",
                role: "consumer",
                true_name: "glen@fuwo.com",
                user_id: "701524",
                user_name: "Glen"
            };
            userData.role = _role[userData.role];
        } else {
            userData = _userInfo;
        }
        
        var html='<span class="on"><a href="">'+ utils.subStr(userData.user_name, 8) +' <i class="icon-triangle-down"></i></a><ul><li><a href="http://3d.fuwo.com/iyun/designs/">我的设计</a></li><li><a href="http://www.fuwo.com/uc/setting/">账号设置</a></li><li><a href="javascript:void(0);" id="to-logout">退出登录</a></li></ul></span> ';

        $(".header-top").find("label").find("span").remove();
        $(".header-top").find("label").prepend(html);

    };

    /**
     * 登录
     */
    var _login = function(callback) {
        if ($('#login').length > 0) {
            return;
        }
        var popData = {
            title: '登录爱福窝',
            content: template('common/account/login'),
            style: {
                width: '340px',
                height: '312px',
                marginTop: '-200px',
                marginLeft: '-170px'
            }
        };
        $("body").append(template("common/popup/dialog", popData));
        ui.placeholder();
        //定义表单验证
        var loginValidator = $('#login').validate({
            //errorLabelContainer: $('#login div.error'),
            rules: {
                user: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                user: {
                    required: '请输入登录的邮箱手机或用户名'
                },
                password: {
                    required: '请输入登录密码'
                }
            },
            submitHandler: function(form) {
                var formData = {
                    value: utils.b64EncodeUrl($('input[name=password]').val() + '|福窝|' + $('input[name=user]').val()),
                    from: 'minilogin'
                };
                services.post('account', 'login', formData, function(code, msg, data) {
                    $(".pop .btn").removeClass('btn-disabled').addClass('btn-success');
                    if (code === services.CODE_SUCC) {
                        ui.alert('success', '登录成功！');

                        // 登录成功，添加header 用户个人中心菜单
                        if (debug === true) {
                            $.cookie('userinfo', "user_id=701524&true_name=glen%40fuwo.com&message_count=0&role=consumer&avatar=http%3A%2F%2Fstatic.fuwo.com%2Fupload%2F28693%2F701524%2Favatar%2F1fcf979cbdb111e4bb0200163e000ee8.jpg&user_name=glen");
                        }
                        _getUserInfo();
                        if (callback) {
                            callback(code, data);
                        }
                        $(".screen-bg").remove();
                        $(".pop").remove();
                    } else if (code === services.CODE_NOT_EXIST_ERR) {
                        $("#login .js_login_err").append('<label id="login-err" class="error">登录邮箱手机或者密码错误</label>');
                        $('input[name=user]').val('');
                        $('input[name=password]').val('');
                        setTimeout(function() {
                            $('#login-err').remove();
                        }, 3000);
                    } else {
                        $("#login .js_login_err").append('<label id="login-err" class="error">登录失败，请重试</label>');
                        setTimeout(function() {
                            $('#login-err').remove();
                        }, 3000);
                    }
                });
            }
        });
        $(".pop-close").click(function() {
            $(".screen-bg").remove();
            $(".pop").remove();
        });
        $(".pop .btn").click(function() {
            if ($(this).hasClass('btn-disabled')) {
                return;
            }
            var op = $(this).data('op');
            if (op === 'login') {
                $(this).removeClass('btn-success').addClass('btn-disabled');
                $('#login').submit();
            }
        });
        $('#login .js_open_login').click(function() {
            switch ($(this).data('open')) {
                case 'qq':
                    location.href = '/account/qz/req_auth/?next=' + location.pathname;
                    break;
                case 'weibo':
                    location.href = '/account/sina/req_auth/?next=' + location.pathname;
                    break;
                case 'taobao':
                    location.href = '/account/taobao/req_auth/?next=' + location.pathname;
                    break;
            }
        });
    };


    // 渲染登录组件
    var _renderLogin = function(callback, successHandler) {

        var __loginSuccessHandler = function(data) {

            if (successHandler) {
                /*weixin.destroy();*/
                successHandler(data);
                return;
            }
            ui.alert('success', '登录成功！');
            _getUserInfo();
            if (callback) {
                callback(services.CODE_SUCC, data);
            }
          /*  weixin.destroy();*/
            $('.J-Mask-Bg, .J-Mark-Close').remove();
            $("#sign_wrap").remove();
            location.reload();
        };

        //tab切换
        var tab_opt = $('.tab-opt'),
            tab_panel = $('.tab-panel');
        tab_opt.click(function() {
            var i = tab_opt.index($(this));
            tab_opt.removeClass('active');
            $(this).addClass('active');
            tab_panel.eq(i).show().siblings().hide();
           /* if (i == 0) {
                weixin.reset();
            } else {
                weixin.clear();
            }*/
        });

        //判断之前是否有设置cookie，如果有，则设置【记住我】选择框
        var $rememberMe = $("#sign_remember");
        var remembered = !!jQuery.cookie('fw_user');
        $("#sign_remember").prop("checked", remembered);

        //读取cookie
        if ($rememberMe.prop('checked')) {
            $('#sign_username').val(jQuery.cookie('fw_user'));
            $(".input").find(".placeholder").hide();
        }

        // 记住我的处理
        var __rememberMeHandle = function() {
            if ($rememberMe.prop('checked')) { //设置cookie
                jQuery.cookie('fw_user', $('#sign_username').val(), {
                    path: '/'
                });
            } else { //清除cookie
                jQuery.cookie('fw_user', null, {
                    path: '/'
                });
            }
        };

        //微信二维码组件
      /*  weixin.config({
            dom: $('.sign-wrap .weixin-panel'),
            scene: weixin.SCENE_LOGIN,
            successHandler: __loginSuccessHandler
        });
        weixin.render();*/

        ui.placeholder();

        //验证
        var validator = $("#login_form").validate({
            ignore: "",
            rules: {
                username: {
                    required: true,
                    userName: true
                },
                pwd: {
                    required: true,
                    passWord: true
                }
            },
            messages: {
                username: {
                    required: "请使用手机号或邮箱登录",
                    userName: "请填写正确的邮箱或手机号"
                },
                pwd: {
                    required: "请填写密码",
                    passWord: '请输入6-16位密码'
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.closest(".inputline p"));
            },
            submitHandler: function() {

                var formData = {
                    username: $('#sign_username').val(),
                    password: $('#sign_pwd').val()
                };
                $('#sign_submit').removeClass('btn-success').addClass('btn-disabled');
                services.post('sign', 'in', formData, function (code, msg, data) {
                    $('#sign_submit').removeClass('btn-disabled').addClass('btn-success');
                    if (code === services.CODE_SUCC) {
                        __rememberMeHandle();
                        __loginSuccessHandler(data);
                    } else {
                        $(".inputline-wrap").append('<label class="error">' + msg + '</label>');
                    }
                });
            }
        });

        //定义表单验证
        $('#sign_submit').click(function(event) {

            if ($(this).hasClass('btn-disabled')) {
                return;
            }
            $('#login_form').submit();
        });

        //键盘enter操作
        $("#sign_username").keypress(function(e) {
            if (e.which == 13) {
                $("#sign_pwd").focus();
            }
        });

        $("#sign_pwd").keypress(function(e) {
            if (e.which == 13) {
                $("#sign_submit").focus().click();
            }
        });

        $('.J-Open').on('click', function(event) {
            var openType = $(this).data('open');
            switch (openType) {
                case 'qq':
                    location.href = 'http://www.fuwo.com/account/qz/req_auth/?next=' + location.href;
                    break;
                case 'weibo':
                    location.href = 'http://www.fuwo.com/account/sina/req_auth/?next=' + location.href;
                    break;
                case 'taobao':
                    location.href = 'http://www.fuwo.com/account/taobao/req_auth/?next=' + location.href;
                    break;
                case 'sign':
                    location.href = 'http://www.fuwo.com/sign/signup/?next=' + location.href;
                    break;
            }
        });
    };

    /**
     * 登录新弹窗
     */
    var _loginNew = function(callback) {

        if ($('#sign_wrap').length > 0) {
            return;
        }
        if(window.g_config == undefined || window.g_config.staticHost == undefined){
            window.g_config.staticHost = "";
        }
        //Mask背景
        $('body').append('<div class="screen-bg J-Mask-Bg"></div>');
        $('body').append('<a href="javascript:;" class="mark-close J-Mark-Close"><img src="http://'+ window.g_config.staticHost +'/static/images/common/mask-close.png" /></a>');
        $("body").append(template("account/login"));

        _renderLogin(callback);

        $('#sign_remember').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
            cursor: true,
            increaseArea: '20%'
        });

        $('.J-Mask-Bg, .J-Mark-Close').on('click', function(event) {
           /* weixin.destroy();*/
            $('.J-Mask-Bg, .J-Mark-Close').remove();
            $("#sign_wrap").remove();
        });
    };

    //监听登录注册按钮事件
    $(function($) {
        $('#to-login').click(function(event) {
            event.preventDefault();
            if (_loginState === true) {} else {
                if (location.pathname.indexOf('/account/') !== -1) {
                    location.href = 'http://www.fuwo.com/sign/signin/';
                } else {
                    location.href = 'http://www.fuwo.com/sign/signin/?next=' + encodeURI(location.href);
                }
            }
        });
        $('#to-register').click(function(event) {
            event.preventDefault();
            if (location.pathname.indexOf('/account/') !== -1) {
                location.href = 'http://www.fuwo.com/sign/signup/';
            } else {
                location.href = 'http://www.fuwo.com/sign/signup/?next=' + encodeURI(location.href);
            }
        });

        $(document).delegate('#to-logout', 'click', function(event) {
            event.preventDefault();
            if (debug === true) {
                $.cookie('userinfo', null);
                location.reload();
            } else {
                location.href = "http://www.fuwo.com/account/signout/?next=" + encodeURI(location.href);
            }
        });
        //初始化
        if (_userInfo === null) {
            _getUserInfo();
        }

        // 全局登录判断
        window.loginRequired = function(callback) {
            callback = callback || function() {};
            if (_loginState === true) {
                callback();
            } else {
                _loginNew(callback);
            }
        };

    });

    return {
        /*
         * 从cookie中获取用户信息
         */
        userInfo: function() {
            return _userInfo === null ? _getUserInfo() : _userInfo;
        },
        /*
         * 从接口中取用户详细信息
         */
        getUserDetail: function(parameters, callback) {
            _getUserDetail(parameters, callback);
        },

        destoryUserDetail: function() {
            _userDetail = null;
        },
        /*
         * 登录态
         */
        isLogin: function() {
            var info = $.cookie('userinfo');
            if (info !== null) {
                return true;
            }
            return false;
        },
        /*
         * 登录框
         */
        login: function(callback) {
            _login(callback);
        },
        /*
         * 需要登录
         * callback 回调方法
         */
        loginRequired: function(callback) {
            callback = callback || function() {};
            if (_loginState === true) {
                callback();
            } else {
                _login(callback);
            }
        },
        newLoginRequired: function(callback) {
            callback = callback || function() {};
            if (_loginState === true) {
                callback();
            } else {
                _loginNew(callback);
            }
        },
        /*
         * 更新用户cookie
         */
        updateCookie: function(key, value) {
            if (_userInfo !== null) {
                _userInfo[key] = value;
                if (key === 'avatar' || key === 'user_name') {
                    $('.js_user_nav').html('<a href="javascript:void(0);"><img class="img-32" src="' + _userInfo.avatar + '"></a> ' + utils.subStr(_userInfo.user_name));
                }
                $.cookie('userinfo', utils.objToQuery(_userInfo));
            }
        },
        /*
         * 关注
         */
        // follow: function(userId, callback) {
        //     if (_loginState === false) {
        //         _login();
        //         return;
        //     }
        //     services.post('favorite', 'add', {
        //         app_label: 'auth',
        //         model: 'user',
        //         object_id: userId
        //     }, function(code, msg, data) {
        //         if (code == services.CODE_SUCC) {
        //             ui.alert('success', '关注成功');
        //             if (callback) {
        //                 callback(code, msg, data);
        //             }
        //         } else {
        //             ui.alert('error', msg);
        //         }
        //     });
        // },
        /*
         * 取消关注
         */
        // unFollow: function(userId, callback) {
        //     if (_loginState === false) {
        //         _login();
        //         return;
        //     }
        //     ui.confirm('取消关注', '你确定要取消关注该用户吗？', 350, 200, function() {
        //         services.post('favorite', 'delete', {
        //             app_label: 'auth',
        //             model: 'user',
        //             object_id: userId
        //         }, function(code, msg, data) {
        //             if (code == services.CODE_SUCC) {
        //                 ui.alert('success', '取消成功');
        //                 if (callback) {
        //                     callback(code, msg, data);
        //                 }
        //             } else {
        //                 ui.alert('error', msg);
        //             }
        //         });
        //     });
        // },
        //验证码
        verifycode: function(_this, user, callback, errorback) {
            var date, verifycode, left, top;
            date = Math.random();
            verifycode = "http://www.fuwo.com/verifycode/image/show/?code_type=word&width=75&height=30&t=" + date;
            left = _this.offset().left + "px";
            top = _this.offset().top + 50 + "px";
            var data = {
                istriangle: true,
                direction: true,
                classname: "verifycode",
                left: left,
                top: top,
                content: template("common/verification")
            };
            if ($(".float-window").length > 0) {
                return;
            } else {
                $("body").append(template("common/popup/float_window", data));
            }

            $("#verifycode").attr("src", verifycode);
            $("#verifycode,#js-refresh-code").click(function() {
                date = Math.random();
                verifycode = "http://www.fuwo.com/verifycode/image/show/?code_type=word&width=75&height=30&t=" + date;
                $("#verifycode").attr("src", verifycode);
            });
            $(".js-confirm").click(function() {
                if ($("#verification").val() === "") {
                    if (errorback) {
                        errorback('请填写验证码');
                    } else {
                        ui.alert("error", "请填写验证码");
                    }
                } else {
                    services.post("verifycode", "mobileCreate", {
	                    mobile: user,
	                    verifycode:$("#verification").val()
                    }, function(code, msg, data) {
	                    if(!errorback){
		                    $(".float-window").remove();
	                    };
                        if (code === services.CODE_SUCC) {
                            if (callback) {
                                callback(code, msg, data);
                            }
                        } else {
                            if (errorback) {
                                errorback(msg);
                            } else {
                                ui.alert("error", msg);
                            }
                        }
                    });
                }
            });
            $(document).click(function() {
                if ($(".float-window").length === 0) {
                    $(_this).removeClass('btn-disabled').addClass('btn-primary');
                }
            });
        },

        //新的获取验证码
        createverifycode: function(_this, user, callback, errorback, isFixed) {
            if(!user){
                ui.alert("error", "手机号不能为空");
                return;
            }
            var date, verifycode, offsetLeft, offsetTop, position, left, top;
            offsetLeft = _this.offset().left;
            offsetTop = _this.offset().top;
            left = offsetLeft + "px";
            top = isFixed ? (offsetTop - $(window).scrollTop() + 50) + 'px' :  offsetTop + 50 + "px";
            position = isFixed ? 'fixed': 'absolute';
            var data = {
                istriangle: true,
                direction: true,
                classname: "verifycode",
                position: position,
                left: left,
                top: top,
                content: template("common/verification")
            };
            if ($(".float-window").length > 0) {
                return;
            } else {
                $("body").append(template("common/popup/float_window", data));
            }

            function refreshVerifyCode (){
                date = Math.random();
                verifycode = "http://3d.fuwo.com/verifycode/image/show/?code_type=word&width=90&height=30&t=" + date;
                $('#verifycode').attr("src", verifycode);
            }
            refreshVerifyCode();
            $("#verifycode,#js-refresh-code").click(function() {
                refreshVerifyCode();
            });
            $(".js-confirm").click(function() {
                if ($("#verification").val() === "") {
                    if (errorback) {
                        errorback(_this,'请填写验证码');
                    } else {
                        ui.alert("error", "请填写验证码");
                    }
                } else {
                    services.post("brand", "verifycode", {
                        mobile: user,
                        verifycode:$("#verification").val()
                    }, function(code, msg, data) {
                        if(!errorback){
                            $(".float-window").remove();
                        };
                        if (code === services.CODE_SUCC) {
                            if (callback) {
                                callback(_this,code, msg, data);
                            }
                        } else {
                            if (errorback) {
                                errorback(_this,msg);
                            } else {
                                ui.alert("error", msg);
                            }
                        }
                    });
                }
            });
        }
    };
});