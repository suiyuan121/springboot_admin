require([
    'jquery',
    'layuiAll',
    'admin/common',
    'ui',
    'services',
    'adminApi',
    'validateExpand'
], function ($,
             layuiAll,
             comJs,
             ui,
             services,
             adminApi,
             validateExpand) {
    var page = {
        init: function () {
            var _this = this;
            comJs.header();
            comJs.setPriority();
            _this.operation();
            _this.validate();

        },

        operation: function () {
            //表单操作
            $(document).on('click', '.delete', function () {
                var $this = $(this);
                var sendData = {
                    id: $this.parents('tr').data('id')
                };
                var html = '<div class="document-list"><p>确定删除已选中公告吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $(document).on('click', '.delete-btn', function () {
                    adminApi.deleteBulletin(sendData, function (code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            $this.parents('tr').remove();
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            ui.alert("success", '删除成功');
                            redirectPage();
                        } else {
                            ui.alert("error", '删除失败' + msg);
                        }
                    })
                })
            });
            //设置公告状态
            $(document).on('click', '.set-state', function () {
                var $this = $(this);
                var id = $this.parents('tr').data('id');
                var html = '<form class="announcement-set">' +
                    '<p class="">设置状态</p>' +
                    '<div class="radio">' +
                    '<label><input name="states" type="radio" value="PUBLIC" />公开</label>' +
                    '<label><input name="states" type="radio" value="PRIVATE" />不公开</label>' +
                    '</div>' +
                    '<button class="layui-btn layui-btn-primary">取消</button>' +
                    '<a href="javascript:;" class="layui-btn save-btn">确定</a>' +
                    '</form>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $('.save-btn').on('click', function () {
                    var state = $('input[type="radio"]:checked').val();
                    var stateValue = $('input[type="radio"]:checked').parents('label').text();
                    var sendData = {
                        id: id,
                        state: state
                    };
                    adminApi.setState(sendData, function (code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            $this.parents('tr').find('td').eq(3).text(stateValue);
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            ui.alert("success", '设置成功');
                        } else {
                            ui.alert("success", '设置失败');
                        }

                    });
                });
            });
        },
        validate: function () {
            //创建一个编辑器
            var layedit = layui.layedit;
            layedit.set({
                uploadImage: {
                    url: '/attachment/ueditor/controller/',
                    type: 'post'
                }
            });
            var editIndex = layedit.build('comment_content');
            //验证
            $("#form-announcement-add").validate({
                ignore: "",
                rules: {
                    title: {
                        required: true
                    },
                    content: {
                        required: true
                    }
                },
                messages: {
                    title: {
                        required: "请填写标题"
                    },
                    content: {
                        required: "请填写内容"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                }
            });

            //定义表单验证
            $('.save-btn').click(function () {
                layedit.getContent(editIndex);
               // alert(layedit.getContent(editIndex));
                layedit.sync(editIndex);
                var content = layedit.getText(editIndex);
                //alert( layedit.getText(editIndex));

                /*var commentContent = $('.comment-content-textarea');
                if (commentContent) {
                    var content = commentContent.data('value');
                    $.get(content, function (result) {
                        layedit.sync(editIndex);
                        /!* ue.ready(function () {
                         ue.setContent(result);
                         });*!/
                    });
                }*/

                $('#form-announcement-add').submit();

            });

            // 清除按钮
            $('.clear-btn').on('click', function () {
                $('.layui-input-block').find('input').val('');
                $('.style').find('dd').removeClass('layui-this').first().addClass('layui-this');
                $('.space').find('dd').removeClass('layui-this').first().addClass('layui-this');
            });

            var form = layui.form();
            var flagState = false;
            form.on('select(state)', function (data) {
                if (flagState) {
                    redirectPage("&state=" + data.value);
                }
            });

            //页面初始化赋值使用
            (function () {
                /* var ue = UE.getEditor('comment_content');
                 //清空草稿箱
                 ue.execCommand('clearlocaldata');*/

                var state = comJs.getUrlParam("state");
                var stateDd = "dd[lay-value = " + state + "]"
                $('#state-form').find(stateDd).click();
                flagState = true;

                var commentContent = $('.comment-content-textarea');
                if (commentContent) {
                    var content = commentContent.data('value');
                    $.get(content, function (result) {
                        ue.ready(function () {
                            ue.setContent(result);
                        });
                    });
                }

                 var layedit = layui.layedit;
                 /*layedit.set({
                     uploadImage: {
                         url: '/attachment/ueditor/controller/',
                         type: 'post'
                     }
                 });*/
                 var index = layedit.build('comment_content');
                var commentContent = $('.comment-content-textarea');
                console.log(content);
                //$("#comment_content").siblings('.layui-layedit').find('body').text("要放入富文本的内容");
                var commentContent = $('.comment-content-textarea');
                if (commentContent) {
                    var content = commentContent.data('value');
                    $.get(content, function (result) {
                        document.getElementsByTagName("iframe")[0].contentWindow.document.body.innerHTML=result;
                    });
                }
                layedit.build('comment_content', {
                    tool: [
                        'strong' //加粗
                        ,'italic' //斜体
                        ,'underline' //下划线
                        ,'del' //删除线

                        ,'|' //分割线

                        ,'left' //左对齐
                        ,'center' //居中对齐
                        ,'right' //右对齐
                        ,'link' //超链接
                        ,'unlink' //清除链接
                        ,'face' //表情
                    ]
                });
                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }

            })();
        }
    };
    page.init();
});

