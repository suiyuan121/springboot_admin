/**
 * Created by admin on 2017/8/19.
 */
require([
    'jquery',
    'layuiAll',
    'template',
    'admin/common',
    'ui',
    'services',
    'adminApi',
    'module',
    'ueditorMain',
    'validateExpand',
    'fileupload',
    'iframeTransport',
    'fileuploadProcess',
    'fileuploadValidate',
], function ($,
             layuiAll,
             tpls,
             comJs,
             ui,
             services,
             adminApi,
             module,
             ueditorMain,
             validateExpand,
             fileupload) {
    var page = {
        init: function () {
            var _this = this;
            comJs.header();
            comJs.setPriority();
            _this.operation();
            _this.validate();

        },

        operation: function () {
            /*//创建一个编辑器
            var layedit = layui.layedit;
            var editIndex = layedit.build('LAY_demo_editor');*/

            //表单操作
            $(document).on('click', '.delete', function () {
                var $this = $(this);
                var sendData = {
                    id: $this.parents('tr').data('id')
                };
                var html = '<div class="document-list"><p>确定删除该文档？</p>' +
                    '<button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $('.delete-btn').on('click', function () {
                    adminApi.deleteModelPackage(sendData, function (code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            $this.parents('tr').remove();
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            ui.alert("success", '删除成功');
                        } else {
                            ui.alert("error", '删除失败' + msg);
                        }
                    })
                })
            });

            //板块选择
            var blockList = $('#block-list');
            var blockItem = blockList.find('li');
            blockItem.on('click', function () {
                $(this).addClass('active').siblings('li').removeClass('active');
            });
        },
        validate: function () {
            //验证
            $("#form-document-add").validate({
                ignore: "",
                rules: {
                    title: {
                        required: true
                    },
                    poster: {
                        required: true
                    },
                    viewsInitial: {
                        required: true,
                        number: true
                    },
                    content: {
                        required: true
                    }
                },
                messages: {
                    title: {
                        required: "请填写标题"
                    },
                    poster: {
                        required: "请填写发帖人"
                    },

                    viewsInitial: {
                        required: "请填写浏览量",
                        number: "请输入有效的数字"
                    },
                    content: {
                        required: "请填写内容"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                }
            });

            //初始化ue
            var ue = UE.getEditor('comment_content');
            //定义表单验证
            $('.save-btn').on('click', function () {
                var blockItemActive = $('#block-list').find('.active').children('a').data('value');
                $('#block-type').val(blockItemActive);
                $('#form-document-add').submit();

            });

            var form = layui.form();
            var flagSubject = false;
            var flagState = false;
            form.on('select(subject)', function (data) {
                if (flagSubject) {
                    if (data.value == 'all') {
                        redirectPage("&subject=");
                    } else {
                        redirectPage("&subject=" + data.value);
                    }
                }
            });
            form.on('select(state)', function (data) {
                if (flagState) {
                    if (data.value == 'all') {
                        redirectPage("&state=");
                    } else {
                        redirectPage("&state=" + data.value);
                    }
                }
            });


            $('#searchText').bind('keypress', function (event) {
                if (event.keyCode == 13) {
                    // alert($("#searchText").val());
                    var searchText = $("#searchText").val();
                    redirectPage("&searchText=" + searchText);
                }
            });

            //页面初始化赋值使用
            (function () {
                var ue = UE.getEditor('comment_content');
                //清空草稿箱
                ue.execCommand('clearlocaldata');

                var searchText = comJs.getUrlParam("searchText");
                var subject = comJs.getUrlParam("subject");
                var state = comJs.getUrlParam("state");

                var subjectDd;
                if (subject != '') {
                    subjectDd = "dd[lay-value = " + subject + "]"
                    $('#form-subject').find(subjectDd).click();
                }

                var stateDd;
                if (state != '') {
                    stateDd = "dd[lay-value = " + state + "]"
                    $('#form-state').find(stateDd).click();
                }

                flagSubject = true;
                flagState = true;
                $("#searchText").val(searchText);

                var commentContent = $('.comment-content-textarea');
                if (commentContent) {
                    var content = commentContent.data('value');
                    $.get(content, function (result) {
                        ue.ready(function() {
                            ue.setContent(result);
                        });
                    });
                }


                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }

            })();
        }
    };
    page.init();
});

