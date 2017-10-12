/**
 * ueditor插件：插入户型
 *
 * author: Mark
 * date: 2016/4/13
 */

define([
    'jquery',
    'template',
    'ui',
    'utils',
    'account',
    'paginator'
], function($, tpl, ui, utils, account, paginator) {

    var _contentId; // 编辑器id

    // 通过接口读取设计
    // page - 页
    // poptitle - 弹窗标题
    var _getDate = function(page, poptitle) {

        // 成功后处理
        var __successHandler = function(data, poptitle, page, pageCount) {
            var dataConetxt = {
                title: poptitle,
                content: tpl("ueditorext/sendhouselayout", data),
                style: {
                    width: "800px",
                    marginLeft: "-400px",
                    marginTop: "-220px"
                }
            };
            if ($(".J-ueditor-pop").length > 0) {
                $(".J-ueditor-pop .pop-content").empty().append(tpl("ueditorext/sendhouselayout", data));
            } else {
                $("body").append(tpl("common/popup/dialog", dataConetxt));
                $('.pop').last().addClass('J-ueditor-pop');
                $(".screen-bg").last().addClass('J-ueditor-screen');
            }

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
                $(".J-Ie7").show();
            }
            
            $(".pop-close").click(function() {
                $('.J-ueditor-pop').remove();
                $(".J-ueditor-screen").remove();
            });

            $('.J-Choice-Houselayout').click(function() {
                var $obj = $(this).parent(),
                    _src = $obj.find('img').attr("src"),
                    _id = $(this).data('id'),
                    _no = $(this).data('no');
                var html = '<p><img origin="ueditor_upload" class="topic-img J-Ifuwo" width="102" height="102" src="' + _src + '@!240x240" data-type="houselayout" data-no="' + _no + '"/></p>';
                UE.getEditor(_contentId).execCommand('insertHtml', html);

                $('.J-ueditor-pop').remove();
                $(".J-ueditor-screen").remove();
            });

            pages = utils.pageCount(data.total_count, pageCount);
            if (pages > 1) {
                paginator.init('Pagination_dialog', page, pages, _getDate);
                $('#Pagination_dialog').show();
            }
        };


        var pageCount = 3,
            url = 'http://www.fuwo.com/ifuwo/houselayout/own/';
        if (page === "" || page === undefined) {
            page = 1;
        }

        var start_index = (page - 1) * pageCount;
        var date = new Date();
        var parameters = {
            start_index: start_index,
            count: pageCount,
            seed: date.getTime()
        };

        window.g_config = window.g_config || {};
        if (window.g_config.serviceHost != undefined) {
            url = window.g_config.serviceHost == location.host ? url : "http://" + window.g_config.serviceHost + url;
        }

        //数据请求
        $.ajax({
            url: 'http://www.fuwo.com/ifuwo/houselayout/own/',
            dataType: 'json',
            type: 'GET',
            async: true,
            data: parameters,
            xhrFields: {
                "withCredentials": true
            },
            success: function(rsp) {
                __successHandler(rsp.data || {}, poptitle, page, pageCount);
            },
            error: function() {
                ui.alert('error', "连接失败");
            }
        });
    };

    var _init = function() {
        if (window.UE && UE.registerUI) {
            UE.registerUI('housetype', function(editor, uiName) {
                //注册按钮执行时的command命令，使用命令默认就会带有回退操作
                editor.registerCommand(uiName, {
                    execCommand: function() {
                        window.loginRequired(function() {
                            return;
                        });

                        if ($.cookie('userinfo') !== null) {
                            _contentId = editor.key;
                            _getDate(1, "插入户型");
                        }

                    }
                });

                var buttonTitle = {
                    'housetype': '插入户型'
                };
                //创建一个button
                var btn = new UE.ui.Button({
                    //按钮的名字
                    name: uiName,
                    //提示
                    title: buttonTitle[uiName],
                    //添加额外样式,指定icon图标,这里默认使用一个重复的icon
                    cssRules: 'background-position: 0 -37px!important;',
                    //点击时执行的命令
                    onclick: function() {
                        //这里可以不用执行命令,做你自己的操作也可
                        editor.execCommand(uiName);
                    }
                });
                //当点到编辑内容上时，按钮要做的状态反射
                editor.addListener('selectionchange', function() {
                    var state = editor.queryCommandState(uiName);
                    if (state == -1) {
                        btn.setDisabled(true);
                        btn.setChecked(false);
                    } else {
                        btn.setDisabled(false);
                        btn.setChecked(state);
                    }
                });
                //因为你是添加button,所以需要返回这个button
                return btn;
            });
        }
    };

    return {
        load: function() {
            _init();
        }
    };
});