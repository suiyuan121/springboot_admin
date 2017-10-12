/**
 * Created by admin on 2017/8/19.
 */

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
],function(
    $,
    comJs,
    ui,
    services,
    adminApi,
    validateExpand,
    fileupload
){
    var page = {
        init: function(){
            var _this = this;
            comJs.header();
            comJs.uploadImg();
            _this.operation();
            _this.validate();

        },

        operation:function(){
            //表单操作
            $(document).on('click','.delete',function(){
                var $this = $(this);
                var sendData = {
                    order_id : $this.parents('tr').data('id'),
                };
                var html ='<div class="document-list"><p>确定删除该等级吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn save-btn">删除</button></div>';
                comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
                $('.delete-btn').on('click',function(){
                    adminApi.deleteVideo(sendData, function(code, msg, data) {
                        if (code === services.CODE_SUCC) {
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            $this.parents('tr').remove();
                        } else {
                            ui.alert("error", msg);
                        }
                    });
                });
            });
        },
        validate:function(){
            //验证
            $("#form-userlevel-add").validate({
                ignore: "",
                rules: {
                    name: {
                        required: true
                    },
                    priceMin:{
                        required: true,
                        number: true
                    },
                    priceMax:{
                        required: true,
                        number: true
                    }
                },
                messages: {
                    name: {
                        required: "请填写用户等级名称"
                    },
                    priceMin:{
                        required: "请填写充值金额的上限",
                        number: "请输入有效的数字"
                    },
                    priceMax:{
                        required: "请填写充值金额的下限",
                        number: "请输入有效的数字"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                }
            });

            //定义表单验证
            $('.save-btn').click(function() {
                $('#form-userlevel-add').submit();
            });
        }
    };
    page.init();
});


