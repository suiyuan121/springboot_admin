
require([
    'jquery',
    'admin/common',
    'template',
    'ui',
],function(
    $,
    comJs,
    tpls,
    ui
){
    var page = {
        init: function(){
            var _this = this;
            _this.operation();

        },

        operation:function(){
            //表单操作
            $(document).on('click','.delete',function(){
                var $this = $(this);
                /*var sendDate = {
                    order_id : $this.parents('tr').data('id'),
                    status: $this.data('status')
                };*/
                var html ='<div class="document-list"><p>确定删除该文档吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn save-btn">删除</button></div>';
                comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
                /*$(".delete-btn").on('click', function(){
                    orderApi.updateOrderStatus(sendDate, function(code, msg, data) {
                        if(code == services.CODE_SUCC) {
                            $(".screen-bg").remove();
                            $(".pop").remove();
                            window.location.href = window.location.href ;
                        } else {
                            ui.alert("error", msg);
                        }
                    });
                });*/
            });


            $(document).on('click','.set-priority',function(){
                var $this = $(this);
                /*var sendDate = {
                 order_id : $this.parents('tr').data('id'),
                 status: $this.data('status')
                 };*/
                var html = '<div class="set-priority-box">'+
                                '<p class="set">请输入1-5位数字，数字越大优先级越高</p>'+
                                '<input type="text" name="title" placeholder="请输入整数" class="layui-input" maxlength="20">'+
                                '<button class="layui-btn layui-btn-primary">取消</button>'+
                                '<button class="layui-btn save-btn">确定</button>'+
                            '</div>';
                comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
            });
        }
    };
    page.init();
});
