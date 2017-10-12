require([
    'jquery',
    'admin/common',
    'ui',
    'services',
    'adminApi',
    'validateExpand',
    'fileupload',
    'iframeTransport',
    'fileuploadProcess',
    'fileuploadValidate'
], function ($,
             comJs,
             ui,
             services,
             adminApi,
             validateExpand,
             fileupload) {
    var page = {
        init: function () {
            var _this = this;
            comJs.uploadImg($("#upload-figure"),300,170);
            comJs.header();
            comJs.setPriority();
            _this.operation();
            _this.validate();

        },

        operation: function () {
            //删除视频
            $(document).on('click', '.delete', function () {
                var $this = $(this);
                var sendData = {
                    id: $this.parents('tr').data('id')
                };
                var html = '<div class="document-list"><p>确定删除该视频吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $('.delete-btn').on('click', function () {
                    adminApi.deleteVideo(sendData, function (code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            $this.parents('tr').remove();
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            ui.alert("success", '删除成功');
                        } else {
                            ui.alert("error", '删除失败' + msg);
                        }
                    });
                });

            });
        },
        validate: function () {
            //验证
            $("#form-video-add").validate({
                ignore: "",
                rules: {
                    url: {
                        required: true
                    },
                    title: {
                        required: true
                    },
                    speaker: {
                        required: true
                    },
                    playsInitial: {
                        required: true,
                        number: true
                    }
                },
                messages: {
                    url: {
                        required: "请填写视频链接"
                    },
                    title: {
                        required: "请填写视频标题"
                    },
                    speaker: {
                        required: "请填写主讲人"
                    },

                    playsInitial: {
                        required: "请填写播放数量",
                        number: "请输入有效的数字"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },
                /*   submitHandler: function() {
                 console.log($('.title').val());
                 var titled =  $.trim($('.titled').val()),
                 personPosted = $.trim($('.person-posted').val()),
                 block = $('#block-list').find('li.active').children('a').text(),
                 status = $('input[type="radio"]:checked').val(),
                 pageview = $.trim($('.pageview').val()),
                 content = $.trim($('#LAY_demo_editor').val());

                 var formData = {
                 titled:titled,
                 personPosted:personPosted,
                 block:block,
                 status:status,
                 pageview:pageview,
                 content:content
                 };
                 adminApi.addTutorialDocument(formData, function(code, msg, data) {
                 if (code === services.CODE_SUCC) {

                 } else {
                 ui.alert("error", msg);
                 }
                 });
                 }*/
            });

            //定义表单验证
            $('.save-btn').click(function () {
                $('#form-video-add').submit();
            });

            $('#searchText').bind('keypress', function (event) {
                if (event.keyCode == 13) {
                    // alert($("#searchText").val());
                    var searchText = $("#searchText").val();
                    redirectPage("&searchText=" + searchText);
                }

            });


            var form = layui.form();
            var flagCategory = false;
            var flagTag = false;
            var flagState = false;
            form.on('select(category)', function (data) {
                if (flagCategory) {
                    if (data.value == 'all') {
                        redirectPage("&category=");
                    } else {
                        redirectPage("&category=" + data.value);
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
            form.on('select(tag)', function (data) {
                if (flagTag) {
                    if (data.value == 'all') {
                        redirectPage("&tag=");
                    } else {
                        redirectPage("&tag=" + data.value);
                    }

                }
            });


            // 清除按钮
            $('.clear-btn').on('click', function () {
                $('.layui-input-block').find('input').val('');
                $('.style').find('dd').removeClass('layui-this').first().addClass('layui-this');
                $('.space').find('dd').removeClass('layui-this').first().addClass('layui-this');
            });


            //页面初始化赋值使用
            (function () {
                var searchText = comJs.getUrlParam("searchText");
                var category = comJs.getUrlParam("category");
                var state = comJs.getUrlParam("state");
                var tag = comJs.getUrlParam("tag");

                var categoryDd;
                if (category != '') {
                    categoryDd = "dd[lay-value = " + category + "]"
                    $('#form-category').find(categoryDd).click();
                }

                var stateDd;
                if (state != '') {
                    stateDd = "dd[lay-value = " + state + "]"
                    $('#form-state').find(stateDd).click();
                }

                var tagDd;
                if (tag != '') {
                    tagDd = "dd[lay-value = " + tag + "]"
                    $('#form-tag').find(tagDd).click();
                }


                flagCategory = true;
                flagState = true;
                flagTag = true;
                $("#searchText").val(searchText);


                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }
            })();
        }
    };
    page.init();
});


