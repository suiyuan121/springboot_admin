
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
            //删除课程
            $(document).on('click','.delete',function(){
                var $this = $(this);
                var sendData = {
                    order_id : $this.parents('tr').data('id')
                };
                var html ='<div class="document-list"><p>确定删除选中课程吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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
            $("#form-neophytes-add").validate({
                ignore: "",
                rules: {
                    title:{
                        required: true
                    },
                    content: {
                        required: true
                    }
                },
                messages: {
                    title:{
                        required: "请填写标题"
                    },
                    content: {
                        required: "请填写内容"
                    }
                },
                errorPlacement: function(error, element) {
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
            $('.save-btn').click(function() {
                $('#form-neophytes-add').submit();
            });
        }
    };
    page.init();
});



