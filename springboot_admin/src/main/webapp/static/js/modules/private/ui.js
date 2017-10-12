/*
 * UI通用模块
 *
 * author: Glen
 * created:　2015/5/22
 */

define([
    'jquery',
    'lazyload',
    'template'
], function($, lazyload, template) {
    //滚动窗口 header
    $(document).on('scroll',function () {
        if($(this).scrollTop()>60){
            $("#home-header").addClass("fixed").css("box-shadow","0 2px 4px rgba(0, 0, 0, 0.05)");
            $(".header-nav-margin").find(".nav-list").css("padding-top","0px");
            $(".header-nav-margin").find(".user-center").css("margin-top","12px");
            $(".header-nav-margin").find(".nav-header").css("margin-top","12px");
            $(".header-nav-margin").css("height","54px").find(".nav-middle-search").css("display","none");
            $(".header-nav-margin").find(".set-city").css({"margin-top":"18px"});
        } else{
            $("#home-header").removeClass("fixed").css("box-shadow","none");
            $(".header-nav-margin").css("height","120px").find(".nav-middle-search").css("display","block");
            $(".header-nav-margin").find(".nav-list").css("padding-top","15px");
            $(".header-nav-margin").find(".user-center").css("margin-top","16px");
            $(".header-nav-margin").find(".nav-header").css("margin-top","16px");
            $(".header-nav-margin").find(".set-city").css({"margin-top":"23px"});
        }
    });



    // 文本输入框的place holder 效果
    var _placeholderHandle = function() {
        if ('placeholder' in document.createElement('input')) { //如果浏览器原生支持placeholder
            return;
        }

        function target(e) {
            var ee = ee || window.event;
            return ee.target || ee.srcElement;
        }

        function _getEmptyHintEl(el) {
            var hintEl = el.hintEl;
            return hintEl && g(hintEl);
        }

        function blurFn(e) {
            var el = target(e);
            if (!el || el.tagName !== 'INPUT' && el.tagName !== 'TEXTAREA') {
                return; //IE下，onfocusin会在div等元素触发
            }
            var emptyHintEl = el.__emptyHintEl;
            if (emptyHintEl) {
                //clearTimeout(el.__placeholderTimer||0);
                //el.__placeholderTimer=setTimeout(function(){//在360浏览器下，autocomplete会先blur再change
                if (el.value) {
                    emptyHintEl.style.display = 'none';
                } else {
                    emptyHintEl.style.display = '';
                }
                //},600);
            }
        }

        function focusFn(e) {
            var el = target(e);
            if (!el || el.tagName !== 'INPUT' && el.tagName !== 'TEXTAREA') {
                return; //IE下，onfocusin会在div等元素触发
            }
            var emptyHintEl = el.__emptyHintEl;
            if (emptyHintEl) {
                //clearTimeout(el.__placeholderTimer||0);
                emptyHintEl.style.display = 'none';
            }
        }
        if (document.addEventListener) { //ie
            document.addEventListener('focus', focusFn, true);
            document.addEventListener('blur', blurFn, true);
        } else {
            document.attachEvent('onfocusin', focusFn);
            document.attachEvent('onfocusout', blurFn);
        }

        var elss = [document.getElementsByTagName('input'), document.getElementsByTagName('textarea')];
        for (var n = 0; n < 2; n++) {
            var els = elss[n];
            for (var i = 0; i < els.length; i++) {
                var el = els[i];
                var placeholder = el.getAttribute('placeholder'),
                    emptyHintEl = el.__emptyHintEl;
                if (placeholder && !emptyHintEl) {
                    emptyHintEl = document.createElement('strong');
                    emptyHintEl.innerHTML = placeholder;
                    emptyHintEl.className = 'placeholder';
                    emptyHintEl.onclick = function(el) {
                        return function() {
                            try {
                                el.focus();
                            } catch (ex) {}
                        };
                    }(el);
                    if (el.value) {
                        emptyHintEl.style.display = 'none';
                    }
                    el.parentNode.insertBefore(emptyHintEl, el);
                    el.__emptyHintEl = emptyHintEl;
                }
            }
        }
    };

  /*  // 返回顶部
    var _retunrTop = function() {
        // var left = "",
        //     right = "";
        // if ($(".container").length > 0) {
        //     left = $(".container").offset().left + $(".container").width() + 20 + "px";
        // } else {
        //     right = "50px";
        // }
        if (window.g_config === undefined || window.g_config.disableRightFlex === undefined || window.g_config.disableRightFlex) {
            if ($(".pop-right-info").length == 0) {
                var html = '<div class="pop-right-info"><a class="J-Icon-Tab" href="http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT91798111&lng=cn" target="_blank"><i class="share-icon-zixun"></i><br>在线咨询</a><a class="J-Icon-Tab" href="http://www.fuwo.com/baojia/" target="_blank"><i class="share-icon-baojia-list"></i><br>智能报价</a><a class="J-Icon-Tab J-Show-Weixin" href="javascript:void(0);"><i class="share-icon-guanzhu"></i><br>关注有礼</a><a class="J-Icon-Tab J-Show-Download" href="http://www.fuwo.com/download/app"><i class="share-icon_fuceng-xiazaiapp-act"></i><br>下载app</a></div>'
                $("body").append(html);
            }

            if (window.g_config === undefined || window.g_config.disableBackTop === undefined || window.g_config.disableBackTop) {
                if ($(document).scrollTop() <= 100) {
                    $(".J-Return-Top").remove();
                } else {
                    $(".J-Return-Top").remove();
                    $(".pop-right-info").append('<a class="J-Icon-Tab J-Return-Top" href="javascript:void(0);"><i class="share-icon-zhiding"></i><br>回到顶部</a>');
                }
            }
            $(document).delegate(".J-Return-Top", "click", function() {
                $(document).scrollTop(0);
            });
        }
    };*/


    // 懒加载
    var _lazy = function() {
        //判断staticHost是否存在
        if (window.g_config == undefined || window.g_config.staticHost == undefined) {
            window.g_config.staticHost = "";
        }
        $("img.lazy").lazyload({
            effect: "fadeIn",
            placeholder: 'http://' + window.g_config.staticHost + "/static/images/new/common/placeholder.jpg",
            data_attribute: "lazyload",
            failure_limit: 10
        });
    };

    //消息提示窗口
    var _tipHandler = function(msg, result, callback) {
        $(".J-Auth-Expired").remove();
        var icons = result == 'success' ? "share-icon-tip" : "share-icon-warning",
            tipContentDom = '<div class="small-error J-Auth-Expired"><i class="' + icons + '"></i> <span>' + msg + '</span></div>'; //提示内容控件

        $("body").append(tipContentDom);
        var width = parseInt($('.J-Auth-Expired').css("width")) + 1,
            height = parseInt($('.J-Auth-Expired').css("height")),
            marginLeft = -width / 2,
            marginTop = -height / 2 - 200;
        $(".J-Auth-Expired").css({
            height: height,
            marginLeft: marginLeft,
            marginTop: marginTop,
            width: width
        });
        setTimeout(function() {
            if (callback) {
                callback();
            } else {
                $('.J-Auth-Expired').remove();
            }
        }, 2000);
    };

   /* //网站维护公告
    var _webNotice = function() {
        //判断staticHost是否存在
        if (window.g_config == undefined || window.g_config.staticHost == undefined) {
            window.g_config.staticHost = "";
        }

        var html = '<div class="container-fluid web-notice"><div class="container web-content-notice" id="fuwo_notice"><img src="http://' + window.g_config.staticHost + '/static/images/new/common/ring.gif">网站维护公告：  各位用户，本网站定于2014年3月29日12:00至2014年3月31日19:00进行网站维护；届时，本网站的访问和部分服务可能受到影响，敬请谅解。<span class="close-notice" id="fuwo_notice_close">x</span></div></div>';
        window.g_config = window.g_config || {};
        if (window.g_config.disableWeihuNotice) {
            $("#header").find(".navbar").before(html);
            $("#fuwo_notice_close").click(function() {
                $(".web-notice").remove();
            });
        }
    };*/


  /*  //晒家活动 2017-04-05
    var _webActivity = function() {
        window.g_config = window.g_config || {};
        //判断staticHost是否存在
        if (window.g_config == undefined || window.g_config.staticHost == undefined) {
            window.g_config.staticHost = "";
        }
        if (window.g_config.disableActivityNotice == undefined || !window.g_config.disableActivityNotice) {
            if (jQuery.cookie('shaijia') === "0" || jQuery.cookie('shaijia') === null) {
                var html = '<div class="screen-bg"></div><div class="app-activity" style="width:550px;height:328px;margin-top:-164px;margin-left:-275px;"><span class="J-Close activity-close-btn"></span><a href="http://www.fuwo.com/topic/1956752/" target="_blank"><img src="http://' + window.g_config.staticHost + '/static/images/new/common/ac_shaijia.png"></a></div>';
                $("body").append(html);
                jQuery.cookie("shaijia", "1", {
                    path: "/",
                    expires: 1
                });
                $(".screen-bg, .J-Close, .app-activity a").click(function() {
                    $(".screen-bg").remove();
                    $(".app-activity").remove();
                });
            }
        }
    };
*/
    //活动公告
    // var _webActivity = function() {
    //     window.g_config = window.g_config || {};
    //     //判断staticHost是否存在
    //     if (window.g_config == undefined || window.g_config.staticHost == undefined) {
    //         window.g_config.staticHost = "";
    //     }
    //     if (window.g_config.disableActivityNotice == undefined || !window.g_config.disableActivityNotice) {
    //         if (jQuery.cookie('webShow') === "0" || jQuery.cookie('webShow') === null) {
    //             var html = '<div class="screen-bg"></div><div class="app-activity"><span class="J-Close activity-close-btn"></span><a href="http://www.fuwo.com/topic/1862033/" target="_blank"><img src="http://' + window.g_config.staticHost + '/static/images/new/common/app-banner.png"></a></div>';
    //             $("body").append(html);
    //             jQuery.cookie("webShow", "1", {
    //                 path: "/",
    //                 expires: 1
    //             });
    //             $(".screen-bg, .J-Close, .app-activity a").click(function() {
    //                 $(".screen-bg").remove();
    //                 $(".app-activity").remove();
    //             });
    //         }
    //     }
    // };
    //福币活动
    //var _webActivity = function() {
    //    window.g_config = window.g_config || {};
    //    //判断staticHost是否存在
    //    if (window.g_config == undefined || window.g_config.staticHost == undefined) {
    //        window.g_config.staticHost = "";
    //    }
    //    if (window.g_config.disableActivityNotice == undefined || !window.g_config.disableActivityNotice) {
    //        if (jQuery.cookie('fubiActivity') === "0" || jQuery.cookie('fubiActivity') === null) {
    //            var html = '<div class="screen-bg"></div><div class="fubi-activity"><a class="activity-close-btn J-Close" href="javascript:;"></a><img src="http://' + window.g_config.staticHost + '/static/images/new/common/fubi_activity.png"><a class="fubi-activity-link" href="http://www.fuwo.com/activity/lottery/" target="_blank">点击火速开抢</a></div>'
    //            $("body").append(html);
    //            jQuery.cookie("fubiActivity", "1", {
    //                path: "/",
    //                expires: 1
    //            });
    //            $(".screen-bg, .J-Close, .fubi-activity-link").click(function() {
    //                $(".screen-bg").remove();
    //                $(".fubi-activity").remove();
    //            });
    //        }
    //    }
    //
    //};

    //ie判断
    var _ieVersion = function(callback) {
        /**
         * 对ie进行监测
         */
        var ieSize;
        var bowser = navigator.appName;
        var b_version = navigator.appVersion;
        // var version=b_version.split(";");
        // var trim_Version=version[1].replace(/[ ]/g,"");
        if (bowser === "Microsoft Internet Explorer" && b_version.indexOf("MSIE 6.0") > -1) {
            ieSize = 6;
        } else if (bowser === "Microsoft Internet Explorer" && b_version.indexOf("MSIE 7.0") > -1) {
            ieSize = 7;
        }
        if (ieSize < 8) {
            callback();
            return;
        }
    };


    $(function($) {

        _placeholderHandle();


        // 导航菜单事件处理
        $(".navbar-nav li").on('mouseenter', function() {
            $(this).addClass('tmp-active');
            $(this).find(".sub-nav").css("display", "block");
            $(this).find("i").attr("class","share-icon-up");
        }).on('mouseleave', function() {
            $(this).removeClass('tmp-active');
            $(this).find(".sub-nav").css("display", "none");
            $(this).find("i").attr("class","share-icon-down");
        });

        $(".js_navbar>ul>li").on('mouseenter', function() {
            if($(this).is(".border-navbottom")){
                $(".container-fluid .header-border").css("display", "block");
            }else{
                $(".container-fluid .header-border").css("display", "none");
            }
        }).on('mouseleave', function() {
                $(".container-fluid .header-border").css("display", "none");
        });

        //友链
        $(".link-fold").click(function(event) {
            event.preventDefault();
            if ($(this).find("i").attr("class") === "share-show") {
                $(this).parent().addClass("link-unfold");
                $(this).find("i").attr("class", "share-icon-down ");
            } else {
                $(this).parent().removeClass("link-unfold");
                $(this).find("i").attr("class", "share-show");
            }
        });

        //返回顶部
        /*_retunrTop();*/
        /*$(window).scroll(function() {
            _retunrTop();
        });

        $(window).resize(function() {
            _retunrTop();
        });*/

        //图标切换
        $(document).delegate(".J-Icon-Tab", "mouseenter", function() {
            var _this = $(this),
                currentClass = _this.find("i").attr("class");
            if (currentClass.indexOf("-act") > 0) {
                return;
            }
            _this.find("i").attr("class", currentClass + "-act");
        }).delegate(".J-Icon-Tab", "mouseleave", function() {
            if(!$(this).hasClass('J-Show-Download')){
                var _this = $(this),
                    currentClass = _this.find("i").attr("class"),
                    current = currentClass.replace('-act','');
                _this.find("i").attr("class", current)
            }
        });

        //显示微信
        $(".J-Show-Weixin").bind({
            mouseenter: function() {
                var html = "<i class='share-weixin J-Close-Weixin' style='display:inline-block;position:absolute;left:-120px;top:98px'></i>"
                $(".pop-right-info").append(html);
            },
            mouseleave: function() {
                $(".J-Close-Weixin").remove();
            }
        });
        //显示下载app
        $(".J-Show-Download").bind({
            mouseenter: function() {
                var html = "<i class='share-app J-Close-App' style='display:inline-block;position:absolute;left:-120px;top:160px'></i>"
                $(".pop-right-info").append(html);
            },
            mouseleave: function() {
                $(".J-Close-App").remove();
            }
        });

        // 百度统计
        if (window.baiduRequired && window.baiduSrc) {
            var _hmt = _hmt || [];
            var hm = document.createElement("script");
            hm.src = window.baiduSrc;
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        }

        //统计代码
        if (window.tongjiRequired && window.tongjiSrc) {
            var _hmt = _hmt || [];
            _hmt.push(['setAccountId', '84b178f4f8ac5344']);
            var hm = document.createElement("script");
            hm.src = window.tongjiSrc;
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        }

        // tip统一控制，鼠标移入显示并延迟操作
        var delayTime;
        $(".J-Pop-Parent, .J-Pop-Child").mousemove(function() {
            $(".J-Pop-Child").show();
            clearTimeout(delayTime);
        });
        $(".J-Pop-Child").mouseleave(function() {
            $(this).hide();
        });
        $(".J-Pop-Parent").mouseleave(function(e) {
            var _target = $('.J-Pop-Parent,.J-Pop-Child'); // 设置目标区域
            //if (!_target.is(e.target) && _target.has(e.target).length === 0) { // Mark 1
            delayTime = setTimeout(function() {
                if (_target.has(e.target).length === 0) { // Mark 1
                    $(".J-Pop-Child").hide();
                }
            }, 200);
        });

        //放大缩小插入设计或户型
        $(document).delegate(".J-Ifuwo-Insert,.J-Ifuwo-Beauty", "click", function() {
            var _this = $(this);
            if (_this.hasClass("active")) {
                _this.removeClass("active");
            } else {
                _this.addClass("active");
            }
        });

        /*//网站维护通告
        _webNotice();*/

        /*//活动公告
        _webActivity();*/

    });

    return {
        /*
         * 警告框
         *
         * status   状态 success|info|error|waring
         * msg      信息
         */
        alert: function(status, msg) {
            var messageDom = $('#message'),
                top = 0;
            className = 'message-success';
            switch (status) {
                case 'info':
                    className = 'message-info';
                    break;
                case 'waring':
                    className = 'message-waring';
                    break;
                case 'error':
                    className = 'message-error';
                    break;
                default:
                    className = 'message-success';
                    break;
            }
            if (messageDom.length === 0) {
                var data = {
                    classname: className,
                    message: msg,
                    top: top
                };
                $('body').append(template("common/popup/message", data));
                $(".message-close").click(function() {
                    $(this).parent().remove();
                });

                setTimeout(function() {
                    messageDom = $('#message');
                    messageDom.removeClass("message").addClass("message-out");
                    setTimeout(function() {
                        messageDom.remove();
                    }, 200);
                }, 3000);
            } else {
                messageDom.css('top', top);
                messageDom.attr('class', 'message ' + className);
                $('#message .message-content').text(msg);
            }
        },
        /*
         * 确认框
         *
         * title 标题
         * content 内容
         * confirmCallback　确认后回调方法
         * cancelCallback 取消后回调方法
         */
        confirm: function(title, content, width, height, confirmCallback, cancelCallback, arg) {
            var data = {
                title: title,
                content: content,
                width: (width ? width : '400') + 'px',
                height: (height ? height : '220') + 'px',
                marginTop: parseInt(-(height ? height : 220) / 2) + 'px',
                marginLeft: parseInt(-(width ? width : 400) / 2) + 'px'
            };
            $("body").append(template("common/popup/confirm", data));
            _ieVersion(function() {
                $(".J-Ie7").show();
            });
            $(".pop-close, .pop .btn").click(function() {
                var op = $(this).data('op');
                if (op === 'confirm' && confirmCallback) {
                    confirmCallback(arg);
                } else if (op === 'cancel' && cancelCallback) {
                    cancelCallback();
                }
                $(".screen-bg").remove();
                $(".pop").remove();
            });
        },
        /*
         * 处理文本输入框的placeholder
         */
        placeholder: function() {
            _placeholderHandle();
        },

        /**
         * 懒加载
         */
        lazy: function() {
            _lazy();
        },

        /*
         * 表单提交结果
         * title 如：恭喜，您已成功上传画册！
         * content 如：去其他地方逛逛吧~~
         * btnList 如：［{title:'查看更多', url:'/photo/list/'},{title:'回到主页', url:'/'}]
         * skip 如：{title:'相册列表', url:'/photo/list'}
         */
        submitResult: function(title, content, btnList, skip) {
            $("#submit_result").empty();
            var data = {
                title: title,
                content: content,
                btnList: btnList,
                skip: skip
            };
            $("#submit_result").append(template("common/submit_result", data));
            var sec = 5;
            var secTimer = null;
            $("#count-down").text(sec);
            $(window).scrollTop(0);
            secTimer = setInterval(function() {
                sec--;
                $("#count-down").text(sec);
                if (sec === 0) {
                    clearInterval(secTimer);
                    if (skip) {
                        location.href = skip.url;
                    }
                }
            }, 1000);
        },

        /*
         * 提示控制
         *
         * @param:
         *  msg - 提示消息
         *  result - 结果( success | warning )
         *  callback - 回调函数
         */
        tipHandler: function(msg, result, callback) {
            _tipHandler(msg, result, callback);
        },

        ieVersion: function(callback) {
            _ieVersion(callback);
        }
    };
});
