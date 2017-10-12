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
            comJs.uploadImg($("#upload-figure"),590,440);
            comJs.setPriority();
            _this.operation();
        },

        operation: function () {
            //验证
            $("#form-matching-add").validate({
                ignore: "",
                rules: {
                    collectsInitial: {
                        required: true,
                        number: true
                    },
                    price: {
                        required: true,
                        number: true
                    },
                    name: {
                        required: true
                    },
                    designNo: {
                        required: true
                    }
                },
                messages: {
                    collectsInitial: {
                        required: "请填写收藏次数",
                        number: "请输入有效的数字"
                    },
                    price: {
                        required: "请填写模型总价格",
                        number: "请输入有效的数字"
                    },
                    name: {
                        required: "请填写模型组合名称"
                    },
                    designNo: {
                        required: "请填写设计编号"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },
            });

            //定义表单验证
            $(document).on('click', '.save-btn', function () {
                var addModelsTable = $('#add-table');
                var trLength = addModelsTable.find('tr').length;
                if (trLength <= 21) {
                    //搭配新增模型保存
                    if ($.trim($('tbody').text())) {
                        var modelInfos = [];
                        $('tr').not('.trHead').each(function () {
                            var $this = $(this);
                            modelInfo = $.trim($this.find('td').eq(2).text());
                            modelInfos.push(modelInfo);
                        });
                        $('.modelInfo').val(modelInfos);
                    }
                    $('#form-matching-add').submit();
                } else {
                    ui.alert('error', '提交的模型个数必须小于等于20个，请删除一些模型');
                    return;
                }
            });

            //表单操作
            $(document).on('click', '.delete', function () {
                var $this = $(this);
                var sendDate = {
                    id: $this.parents('tr').data('id'),
                };
                var html = '<div class="document-list"><p>确定删除选中搭档吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn save-btn">删除</button></div>';
                comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                $(".save-btn").on('click', function () {
                    adminApi.deleteModelMatching(sendDate, function (code, msg, data) {
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

            //删除模型
            var deleteModel = function () {
                $(document).on('click', '.delete-model', function () {
                    var $this = $(this);
                    var $id = $('#id').val();
                    var sendDate = {
                        id: $id,
                        no: $this.parents('tr').find('td').eq(2).text()
                    };
                    var html = '<div class="document-list"><p>确定删除选中的模型吗？</p><button class="layui-btn layui-btn-primary">取消</button><a class="layui-btn delete-model-btn">删除</a></div>';
                    comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                    $(".delete-model-btn").on('click', function () {
                        if ($id === '') {
                            if ($this.parents('tbody').find('tr').length === 1) {
                                $('#add-table').addClass('table-closed');
                            }
                            $this.parents('tr').remove();
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            ui.alert("success", '删除成功');
                        } else {
                            adminApi.deleteModel(sendDate, function (code, msg, data) {
                                if (code === services.CODE_SUCC) {
                                    if ($this.parents('tbody').find('tr').length === 1) {
                                        $('#add-table').addClass('table-closed');
                                    }
                                    $this.parents('tr').remove();
                                    $(".screen-bg").remove();
                                    $(".pop").remove();
                                    ui.alert("success", '删除成功');

                                } else {
                                    ui.alert("error", '删除失败' + msg);
                                }
                            });
                        }

                    });
                });
            }
            deleteModel();

            //添加设计编号
            $('.add-btn').off('click');
            $('.add-btn').on('click', function () {
                var designNo = $('.design-number').val();
                var html = '';
                adminApi.saveDesignNo(designNo, function (code, msg, data) {
                    if (code === services.CODE_SUCC) {
                        $('.delete-all').addClass('open');
                        $('#add-table').removeClass('table-closed');
                        for (var i = 0; i < data.records.length; i++) {
                            html += '<tr data-id="'+data.records[i].id+'">' +
                                '<td>' +
                                '<div class="layui-form add-model-form" pane="">' +
                                '<div class="layui-input-block select-box">' +
                                '<input type="checkbox" name="like1[write]" lay-skin="primary" title="" lay-filter="selected">' +
                                '</div>' +
                                '</div>' +
                                '</td>' +
                                '<td><img src="' + data.records[i].preview_url + '"/></td>' +
                                '<td>' + data.records[i].no + '</td>' +
                                '<td>' +
                                '<form class="layui-form" lay-filter="sort-set">' +
                                '<div class="layui-form-item">' +
                                '<div class="layui-input-block">' +
                                '<input type="radio" name="category" value="JJ" title="家具" lay-filter="model-category-set">' +
                                '<input type="radio" name="category" value="YZ" title="硬装" lay-filter="model-category-set">' +
                                '<input type="radio" name="category" value="PS" title="配饰" lay-filter="model-category-set">' +
                                '</div>' +
                                '</div>' +
                                '</form>' +
                                '</td>' +
                                '<td>' +
                                '<div class="btnwrap">' +
                                '<button type="button" class="btn delete-model">删除</button>' +
                                '</div>' +
                                '</td>' +
                                '</tr>';
                        }
                        var $tbody = $('#add-table').find('tbody');
                        $tbody.append(html);

                        //更新表单渲染
                        var form = layui.form();
                        form.render(null, 'sort-set');

                        //删除模型
                        deleteModel();
                    } else if (code === services.CODE_NOT_EXIST_ERR) {
                        html = '<div class="document-list"><p>你输入的设计编号有误，请核对后再输入</p><button class="layui-btn confirm-btn">确定</button></div>';
                        comJs.popInfo(html, '-195px', '-90px', '140px', '390px', 'absolute');
                        $('.confirm-btn').on('click', function () {
                            $(".screen-bg").remove();
                            $(".pop").remove();
                        });
                    }
                });
            });

            //批量删除已选取的模型
            $('.delete-all').on('click',function(){
                //需要批量删除模型的模型号列表
                if($('.layui-form-checked').length === 0){
                    ui.alert("error", '请选择要批量删除的模型');
                    return;
                }
                var selectedModelNoList =[];
                var trs =[];
                $('.layui-form-checked').each(function(i,val){
                    var $this = $(this);
                    selectedModelNoList.push($this.parents('tr').find('td').eq(2).text());
                });
                var html ='<div class="document-list"><p>确定批量删除选中模型吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn unify-delete-model" id="unify-delete-model">删除</button></div>';
                comJs.popInfo(html,'-160px','-95px','190px','320px','absolute');
                $('#unify-delete-model').on('click', function(){
                    var id = $('#id').val();
                    function unifyDelete(){
                        $('.layui-form-checked').each(function(i,val){
                            var $this = $(this);
                            $this.parents('tr').remove();
                        });
                        $(".screen-bg").remove();
                        $(".pop").remove();
                        ui.alert("success", '批量删除成功');
                    }
                    if ( id === '') {
                        if ($this.parents('tbody').find('tr').length === 1) {
                            $('#add-table').addClass('table-closed');
                        }
                        unifyDelete();
                    } else {
                        var sendData = {
                            id : $('#id').val(),
                            nos : selectedModelNoList + ''
                        };
                        adminApi.deleteModelMatchingUnify(sendData, function (code, msg, data){
                            if (code === services.CODE_SUCC) {
                                unifyDelete();
                            } else {
                                ui.alert("error", msg);
                            }
                        });
                    }
                });
            });

            //分类设置接口
            var element = layui.element();
            var form = layui.form();
            form.on('radio(model-category-set)', function (data) {
                var $this = $(data.elem);
                    var html = '<div class="document-list"><p>确定更改分类设置吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn confirm-btn">确定</button></div>';
                    comJs.popInfo(html, '-160px', '-90px', '140px', '320px', 'absolute');
                    $(".confirm-btn").on('click', function () {
                        var sendData = {
                            no: $this.parents('tr').find('td').eq(2).text(),
                            category: $this.val()
                        };
                        adminApi.updateModelCategory(sendData, function (code, msg, data) {
                            if (code === services.CODE_SUCC) {
                                $(".screen-bg").remove();
                                $(".pop").remove();
                            } else {
                                ui.alert("error", msg);
                            }
                        });
                    });
                $(".layui-btn-primary").on('click', function () {
                    $this.prop('checked', false);
                    form.render(null, 'sort-set');
                });

            });


            var form = layui.form();
            var flagSpace = false;
            var flagStyle = false;
            form.on('select(space)', function (data) {
                if (flagSpace) {
                    if (data.value == 'all') {
                        redirectPage("&space=");
                    } else {
                        redirectPage("&space=" + data.value);
                    }

                }
            });
            form.on('select(style)', function (data) {
                if (flagStyle) {
                    if (data.value == 'all') {
                        redirectPage("&style=");
                    } else {
                        redirectPage("&style=" + data.value);
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
                var s = comJs.getUrlParam("searchText");
                var style = comJs.getUrlParam("style");
                var space = comJs.getUrlParam("space");

                var styleDd;
                if (style != '') {
                    styleDd = "dd[lay-value = " + style + "]"
                    $('#form-style').find(styleDd).click();
                }
                var spaceDd;
                if (space != '') {
                    spaceDd = "dd[lay-value = " + space + "]"
                    $('#form-space').find(spaceDd).click();
                }

                flagStyle = true;
                flagSpace = true;
                $("#searchText").val(s);

                var errmsg = $("#errorMessages").text();
                if (typeof(errorMessages) != "undefined" && errmsg != '') {
                    ui.alert("error", errmsg);
                }

            })();

        }
    };
    page.init();
});
