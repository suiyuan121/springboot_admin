

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
            comJs.uploadImg();
            _this.operation();
            _this.validate();

        },

        operation:function(){
            //置顶
            $('.set-first').on('click',function(){
                var $this = $(this);
                var sendData = {
                    order_id : $this.parents('tr').data('id')
                };
                adminApi.deleteVideo(sendData, function(code, msg, data) {
                    if (code === services.CODE_SUCC) {
                        $(".screen-bg").remove();
                        $(".pop").remove();
                        location.href=location.href;
                    } else {
                        ui.alert("error", msg);
                    }
                });

            });

            //删除样板间
            $(document).on('click','.delete',function(){
                var $this = $(this);
                var sendData = {
                    order_id : $this.parents('tr').data('id')
                };
                var html ='<div class="document-list"><p>确定删除此样板间吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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

            //添加样板间
            $('.add-btn').on('click',function(){
                if($('tbody').find('tr').length >=5) {
                    ui.alert('error','样板间个数最多添加5个，请删除部分后再添加')
                }
            });

            //设置限制文本编辑器中字体个数
            $('.layui-textarea').on('keyup',function(){
                var $this = $(this);
                var maxlength = 200;
                var currentContentLength = $this.val().length;
                var remainingLength = maxlength - currentContentLength;
                if(remainingLength >= 0) {
                    var currentContent = $this.val();
                    $this.val(currentContent);
                    $('.length').text(remainingLength);
                }
            })

        },
        validate:function(){
            //验证
            $("#form-sample-room-add").validate({
                ignore: "",
                rules: {
                    panoUrl:{
                        required: true
                    },
                    title: {
                        required: true
                    },
                    description:{
                        required: true
                    }
                },
                messages: {
                    panoUrl:{
                        required: "请填写全景图链接"
                    },
                    title: {
                        required: "请填写样板间链接"
                    },
                    description: {
                        required: "请填写样板间内容"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                },
            });

            //定义表单验证
            $('.save-btn').click(function() {
                $('#form-sample-room-add').submit();
            });
        }
    };
    page.init();
});



