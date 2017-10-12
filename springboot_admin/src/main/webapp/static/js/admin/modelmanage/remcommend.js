
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
    'fileuploadValidate',
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
            comJs.header();
            comJs.uploadImg($("#upload-figure"),220,210);
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
                var html = '<div class="document-list"><p>确定删除该推荐模型吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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
        },
        validate: function () {
            //验证
            $("#form-recommend-add").validate({
                ignore: "",
                rules: {
                    price: {
                        required: true,
                        number: true
                    },
                    name: {
                        required: true
                    },
                    collectsInitial: {
                        required: true,
                        number: true
                    },
                    no: {
                        required: true
                    }
                },
                messages: {
                    price: {
                        required: "请填设置价格",
                        number: "请输入有效的数字"
                    },
                    name: {
                        required: "请填写模型名称"
                    },
                    collectsInitial: {
                        required: "请填写收藏次数",
                        number: "请输入有效的数字"
                    },
                    no: {
                        required: "请填写模型编号"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                }
            });

            //添加设计编号
            $('.add-btn').click(function () {
                var modelNum = $('.model-number').val();
                if (modelNum == '') {
                    ui.alert("error", "模型编号不能为空！");
                    return;
                }
                var sendDate = {
                    modelNo: modelNum
                };
                var html = '';
                var addModelNum = function () {
                    adminApi.addModelNum(sendDate, function (code, msg, data) {
                        var $selectedModelList = $('.selected-model-list');
                        $selectedModelList.parents('.layui-form-item').find('.layui-form-label').removeClass('selected-model');
                        if (code === services.CODE_SUCC) {
                            var serverUrl = $('#aliyunoss-img').val();
                            html += '<li class="selected-model-item"><img src="'+serverUrl+data.previewFpath+'"/></span></li>';
                            $selectedModelList.empty().append(html);
                        } else if (code === services.CODE_NOT_EXIST_ERR) {
                            html += '<div class="document-list"><p>你输入的模型编号有误，请核对后再输入</p><button class="layui-btn confirm-btn">确定</button></div>';
                            comJs.popInfo(html, '-195px', '-90px', '140px', '390px', 'absolute');
                            $('.confirm-btn').on('click', function () {
                                $(".screen-bg").remove();
                                $(".pop").remove();
                            });
                        }
                    });
                };
                addModelNum();
            });


            //定义表单验证
            $('.save-btn').click(function () {
                $('#form-recommend-add').submit();
            });

            //页面初始化赋值使用
            (function () {
                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }

            })();

        }
    };
    page.init();
});

