
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
            comJs.setPriority();
            _this.operation();
            _this.validate();

        },

        operation:function(){
            //删除预告
            $(document).on('click','.delete',function(){
                var $this = $(this);
                var sendData = {
                    order_id : $this.parents('tr').data('id')
                };
                var html ='<div class="document-list"><p>确定删除选中预告吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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
            $("#form-advance-add").validate({
                ignore: "",
                rules: {
                    teacher:{
                        required: true
                    },
                    time: {
                        required: true
                    },
                    contactWay:{
                        required: true
                    },
                    title: {
                        required: true
                    }
                },
                messages: {
                    teacher:{
                        required: "请填写授课老师"
                    },
                    time: {
                        required: "请填写授课时间"
                    },
                    contactWay: {
                        required: "请填写联系方式"
                    },

                    title:{
                        required: "请填写课程标题"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },
            });

            //定义表单验证
            $('.save-btn').click(function() {
                $('#form-advance-add').submit();
            });
        }
    };
    page.init();
});



