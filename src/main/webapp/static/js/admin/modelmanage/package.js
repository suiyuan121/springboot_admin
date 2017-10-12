require([
    'jquery',
    'layuiAll',
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
            comJs.nav();
            comJs.setPriority();
            comJs.uploadImg($("#upload-figure"),350,230);
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
                var html = '<div class="document-list"><p>确定删除该模型包吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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
                    price: {
                        required: true,
                        number: true
                    },
                    buysInitial: {
                        required: true,
                        number: true
                    },
                    name: {
                        required: true
                    }
                },
                messages: {
                    price: {
                        required: "请填写模型包价格",
                        number: "请输入有效的数字"
                    },
                    buysInitial: {
                        required: "请填写购买次数",
                        number: "请输入有效的数字"
                    },
                    name: {
                        required: "请填写模型包名称"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },

            });

            //定义表单验证
            $('.save-btn').click(function () {
                //提交前保存修改的单个模型的价格
                if ($.trim($('tbody').text())) {
                    var modelInfos = [];
                    $('tr').not('.trHead').each(function () {
                        modelInfos.push($.trim($(this).find('td').eq(2).text()));
                    });
                    $('.modelInfo').val(modelInfos);
                }
                $('#form-package-add').submit();
            });

            //添加模型
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
                        if (code === services.CODE_SUCC) {
                            $('.delete-all').addClass('open');
                            $('#add-table').removeClass('table-closed');
                            var serverUrl = $('#aliyunoss-img').val();

                            html += '<tr data-id="' + data.id + '">' +
                                '<td>' +
                                '<div class="layui-form add-model-form" pane="">' +
                                '<div class="layui-input-block select-box">' +
                                '<input type="checkbox" name="like1[write]" lay-skin="primary" title="" lay-filter="selected">' +
                                '</div>' +
                                '</div>' +
                                '</td>' +
                                '<td><img src="' + serverUrl + data.previewFpath + '"/></td>' +
                                '<td>' + data.no + '</td>' +
                                '<td>' +
                                '<div class="layui-form-item">' +
                                '<div class="layui-input-block edit-price-block">' +
                                '<div class="edit-price-box closed">' +
                                '<input class="edit-input" value="' + data.discountPrice + '" maxlength="20"/>' +
                                '<span class="edit-complete"></span>' +
                                '<span class="edit-cancel closed"></span>' +
                                '</div>' +
                                '<span class="input-fee">' + data.discountPrice + '</span>' +
                                '<label class="layui-form-label">福币/个</label>' +
                                '<span class="price-edit-btn"></span>' +
                                '</div>' +
                                '</div>' +
                                '</td>' +
                                '<td>' +
                                '<div class="btnwrap">' +
                                '<button type="button" class="btn delete-model">删除</button>' +
                                '</div>' +
                                '</td>' +
                                '</tr>';
                            var $tbody = $('#add-table').find('tbody');
                            $tbody.append(html);
                            form.render();
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
                if ($.trim($('tbody').text())) {
                    var modelNums = [];
                    $('tr').not('.trHead').each(function () {
                        var $this = $(this);
                        modelNums.push($this.find('td').eq(2).text());
                    });
                    for (var i = 0; i < modelNums.length; i++) {
                        var curModelNum = modelNums[i];
                        if (modelNum === curModelNum) {
                            ui.alert("error", '该模型在本模型包中已存在，无需再添加');
                            return;
                        }
                    }
                    addModelNum();
                } else {
                    addModelNum();
                }
            });

            //批量设置模型价格
            $('.delete-all').on('click', function () {
                //需要批量设置模型价格的模型号列表
                if ($('.layui-form-checked').length === 0) {
                    ui.alert("error", '请选择要批量设置价格的模型');
                    return;
                }
                var selectedModelNoList = [];
                $('.layui-form-checked').each(function (i, val) {
                    var $this = $(this);
                    selectedModelNoList.push($this.parents('tr').find('td').eq(2).text());
                });
                var html = '<form class="set-priority-box" id="form-unify-set-price">' +
                    '<p class="set">请输入选中模型价格</p>' +
                    '<div class="layui-input-block"><input type="text" name="title" placeholder="请输入整数" class="layui-input priority unify-set-price" maxlength="20"></div>' +
                    '<button class="layui-btn layui-btn-primary">取消</button>' +
                    '<button class="layui-btn save-btn">确定</button>' +
                    '</form>';
                comJs.popInfo(html, '-160px', '-95px', '190px', '320px', 'absolute');
                $("#form-unify-set-price").validate({
                    ignore: "",
                    rules: {
                        title: {
                            required: true,
                            number: true
                        }
                    },
                    messages: {
                        title: {
                            required: "请输入模型价格",
                            number: "请输入数字"
                        }
                    },
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest(".layui-input-block"));
                    },
                    submitHandler: function () {
                        var newPrice = $.trim($('.unify-set-price').val());
                        var sendData = {
                            price: newPrice,
                            nos: selectedModelNoList + ''
                        };
                        adminApi.setModelPriceUnify(sendData, function (code, msg, data) {
                            if (code === services.CODE_SUCC) {
                                $('.layui-form-checked').each(function (i, val) {
                                    var $this = $(this);
                                    $this.parents('tr').find('.input-fee').text(newPrice);
                                    $(".screen-bg").remove();
                                    $(".pop").remove();
                                });
                                $('.layui-form-checkbox').attr('class', 'layui-unselect layui-form-checkbox');
                                ui.alert("success", '批量设置模型价格成功');
                            } else {
                                ui.alert("error", msg);
                            }
                        });
                    }
                });
                $(".save-btn").on('click', function () {
                    $('#form-unify-set-price').submit();
                });

            });

            //删除单个模型
            $(document).on('click', '.delete-model', function () {
                var $this = $(this);
                var html = '<div class="document-list"><p>确定删除该模型吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-model-btn">删除</button></div>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $(document).on('click', '.delete-model-btn', function () {
                    if ($this.parents('tbody').find('tr').not('.trHead').length <= 1) {
                        $this.parents('.add-table').addClass('table-closed');
                    }
                    $this.parents('tr').remove();
                    $(".screen-bg").remove();
                    $(".pop").remove();
                    ui.alert("success", '删除成功');
                });
            });

            //修改单个模型价格
            $(document).on('click', '.price-edit-btn', function () {
                var $this = $(this);
                $this.addClass('closed');
                $this.siblings('span').addClass('closed');
                $this.siblings('div').removeClass('closed');
            });

            $(document).on('click', '.edit-cancel', function () {
                var $this = $(this);
                var $editPriceBox = $this.parents('.edit-price-box');
                $editPriceBox.addClass('closed');
                $editPriceBox.siblings('.input-fee').removeClass('closed');
                $editPriceBox.siblings('.price-edit-btn').removeClass('closed');
            });
            $(document).on('click', '.edit-complete', function () {
                var $this = $(this);
                if (/[^\d]/.test($this.siblings('.edit-input').val())) {
                    ui.alert('error', '模型的价格请输入数字');
                }
                var sendData = {
                    no: $this.parents('tr').find('td').eq(2).text(),
                    price: $this.siblings('.edit-input').val()
                }
                adminApi.setModelPrice(sendData, function (code, msg, data) {
                    if (code === services.CODE_SUCC) {
                        var $editPriceBox = $this.parents('.edit-price-box');
                        var newEditValue = $this.siblings('.edit-input').val();
                        $editPriceBox.addClass('closed');
                        $editPriceBox.siblings('.input-fee').removeClass('closed').text(newEditValue);
                        $editPriceBox.siblings('.price-edit-btn').removeClass('closed');
                    } else {
                        ui.alert("error", '保存失败' + msg);
                    }
                })
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

                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }
            })();
        }
    };
    page.init();
});
