/**
 * Created by admin on 2017/8/19.
 */

require([
    'jquery',
    'layuiAll',
    'laydate',
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
             layuiAll,
             laydate,
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
            comJs.setPriority();
            comJs.uploadImg();
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
                var html = '<div class="document-list"><p>确定删除该模型吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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
            $("#form-package-add").validate({
                ignore: "",
                rules: {
                    fee: {
                        required: true,
                        number: true
                    },
                    payedNumber: {
                        required: true,
                        number: true
                    },
                    modelName: {
                        required: true
                    },
                    modelNumber: {
                        required: true
                    }
                },
                messages: {
                    fee: {
                        required: "请填写模型包价格",
                        number: "请输入有效的数字"
                    },
                    payedNumber: {
                        required: "请填写购买次数",
                        number: "请输入有效的数字"
                    },
                    modelName: {
                        required: "请填写模型包名称"
                    },
                    modelNumber: {
                        required: "请填写模型编号"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },

            });

            //定义表单验证
            $('.save-btn').click(function () {
                //单个模型中价格修改保存
                if ($.trim($('tbody').text())) {
                    var modelInfos = [],
                        modelInfo = {};
                    $('tr').not('.trHead').each(function () {
                        var $this = $(this);
                        modelInfo = {
                            discoutPrice: $.trim($this.find('.input-fee').val()),
                            modelNo: $.trim($this.find('td').eq(1).text())
                        };
                        var nmodelInfo = JSON.stringify(modelInfo);
                        modelInfos.push(nmodelInfo);
                    });
                    $('.modelInfo').val('[' + modelInfos + ']');
                    alert($('.modelInfo').val());
                }
                $('#form-package-add').submit();

            });

            // 清除按钮
            $('.clear-btn').on('click', function () {
                $('.layui-input-block').find('input').val('');
                $('.style').find('dd').removeClass('layui-this').first().addClass('layui-this');
                $('.space').find('dd').removeClass('layui-this').first().addClass('layui-this');
            });


            var form = layui.form();
            var flagType = false;
            var flagState = false;
            form.on('select(type)', function (data) {
                if (flagType) {
                    if (data.value == 'all') {
                        redirectPage("&type=");
                    } else {
                        redirectPage("&type=" + data.value);
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


            //日期
            var laydate = layui.laydate;
            laydate.render({
                elem: '#start-time',
                done: function (value, date, endDate) {
                    redirectPage("&startDate=" + value);
                }
            });
            laydate.render({
                elem: '#end-time',
                done: function (value, date, endDate) {
                    redirectPage("&endDate=" + value);
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
                var searchText = comJs.getUrlParam("searchText");
                var type = comJs.getUrlParam("type");
                var state = comJs.getUrlParam("state");
                var startDate = comJs.getUrlParam("startDate");
                var endDate = comJs.getUrlParam("endDate");

                var typeDd;
                if (type != '') {
                    typeDd = "dd[lay-value = " + type + "]"
                    $('#type-form').find(typeDd).click();
                }
                if (state != '') {
                    var stateDd = "dd[lay-value = " + state + "]"
                    $('#state-form').find(stateDd).click();
                }

                flagType = true;
                flagState = true;
                $("#searchText").val(searchText);

                $("#start-time").val(startDate);
                $("#end-time").val(endDate);
            })();

        }
    };
    page.init();
});
