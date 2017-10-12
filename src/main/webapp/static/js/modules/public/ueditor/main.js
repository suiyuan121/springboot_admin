/*
 * ueditor富文本编辑器
 *
 * author: Glen
 * created:　2016/3/21
 */

define([
    'require',
    'module',
    'jquery',
    'ueditorConfig',
    'iHousertype',
    'iDesign',
    'zeroClipboard',
    'ueditorAll',
    'ueditorLang'
], function (require, module, $, config, iHousertype, iDesign, ZeroClipboard) {

    window.ZeroClipboard = ZeroClipboard;

    //加载ueditor的容器id，默认content
    var contentId = module.config().contentId || 'content',

        //源码的查看方式,需要高亮的设置为codemirror，默认textarea
        sourceEditor = module.config().sourceEditor || 'textarea',

        //插件的弹窗url,避免跨域需要进行设置，默认为相对地址
        iframeUrl = module.config().iframeUrl || '~';

    //插件(当前有插入户型inserthouselayout，插入设计insertdesign)
    var plugins = module.config().plugins || [];

    var ue, //ueditor对象
        ueDraftContent = ''; //草稿内容

    // 取当前页面编辑器的草稿箱内容
    var _getUeDraftContent = function () {
        var name = $('#to-login').text();
        var key =window.location.href;
       // alert(key);
        key = key.replace(/\//g, "_");
        key = key.replace('?', "_");
        key = key.replace(/\./g, "_");
        key = key.replace('&', "_");
        key = key.replace('=', "_");
        key = key.replace(/:/g, "_");
        key = "http_" + key + "_" + name + "_" + contentId + "-drafts-data";
        var keyValue = localStorage.ueditor_preference;
        if (keyValue) {
            keyValue = JSON.parse(keyValue);
            if (keyValue[key] && keyValue[key].length > 0) {
                ueDraftContent = keyValue[key];
                $(".J-Draf-Clear").show();
            }
        }
    };

    // 加载自定义插件
    var _loadPlugins = function () {
        if (plugins.length > 0) {
            $.each(plugins, function (i, val) {
                switch (val) {
                    case "inserthousetype": //加载插入户型
                        iHousertype.load();
                        break;
                    case "insertdesign": //加载插入设计
                        iDesign.load();
                        break;
                }
            });
        }
    };

    // 事件操作
    var _bindEvent = function () {

        //清除草稿事件处理
        $(".J-Draft-Clear").click(function () {
            ue.setContent("");
            ue.execCommand('clearlocaldata');
            $(this).hide();
        }).bind({
            mousemove: function () {
                $(this).find("i").removeClass("share-icon-delete-default").addClass("share-icon-delete-hover");
            },
            mouseleave: function () {
                $(this).find("i").removeClass("share-icon-delete-hover").addClass("share-icon-delete-default");
            }
        });

        //当草稿箱有内容时，显示草稿箱操作控件
        ue.addListener('contentChange', function (editor) {
            var contents = ue.getContent();
            contents = contents.replace(/(?!<IMG.+?>)(?!<img.+?>)<.+?>/g, ''); //去除HTML tag 保留img
            contents = contents.replace(/[ | ]*\n/g, '\n'); //去除行尾空白
            contents = contents.replace(/\n[\s| | ]*\r/g, '\n'); //去除多余空行
            if (contents.length > 0) {
                $(".J-Draft-Clear").show();
                $("#content-error").remove();
            } else {
                $(".J-Draft-Clear").hide();
            }
        });


    };

    // 初始化编辑器
    var _init = function () {

        _getUeDraftContent();

        _loadPlugins();

        if ($("#description").val() != undefined) {
            ueDraftContent = $("#description").val();
        }
        ue = UE.getEditor(contentId, {
            initialContent: ueDraftContent,
            sourceEditor: sourceEditor,
            iframeUrlMap: {
                'emotion': iframeUrl + '/dialogs/emotion/emotion.html',
                'link': iframeUrl + '/dialogs/link/link.html'
            },
            compressSide: 1,
            allowDivTransToP: false,
            autoFloatEnabled: true
        });

        _bindEvent();


    };

    $(function ($) {
        _init();
    });
});