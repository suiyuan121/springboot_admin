require([
    'jquery',
    'layuiAll',
    'laydate',
    'admin/common',
    'ui',
    'services'
], function ($,
             layuiAll,
             laydate,
             comJs,
             ui,
             services) {
    var page = {
        init: function () {
            var _this = this;
            comJs.header();
            _this.operation();

        },
        operation: function () {
            //表格导出下拉框
            $('#export-table').on('click',function(){
                var $this = $(this);
                $this.find('.export-table-time-list').toggleClass('open');
                //点击下拉框的其它地方，下拉框隐藏
                $(document).on("click", function (e) {
                    if(e.target !== $(".export-table-time-list")[0] && e.target !== $('#export-table')[0]){
                        $(".export-table-time-list").removeClass('open')
                    }
                });
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

            //页面初始化赋值使用
            $(function () {
                var startDate = comJs.getUrlParam("startDate");
                var endDate = comJs.getUrlParam("endDate");
                $("#start-time").val(startDate);
                $("#end-time").val(endDate);
                $('#searchText').bind('keypress', function (event) {
                    if (event.keyCode == 13) {
                        var searchText = $("#searchText").val();
                        redirectPage("&searchText=" + searchText);
                    }
                });
                var searchText = comJs.getUrlParam("searchText");
                $("#searchText").val(searchText);
            });

        }
    };
    page.init();
});

