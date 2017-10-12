require([
    'jquery',
    'layuiAll',
    'template',
    'admin/common',
    'ui',
    'services',
    'adminApi',
    'validateExpand'
], function ($,
             layuiAll,
             tpls,
             comJs,
             ui,
             services,
             adminApi,
             validateExpand,
             ) {
    var page = {
        init: function () {
            var _this = this;
            comJs.header();
            _this.validate();

        },
        validate: function () {
            //验证
            $("#form-beginner-document-add").validate({
                ignore: "",
                rules: {
                    title: {
                        required: true
                    },
                    poster: {
                        required: true
                    },
                    cont: {
                        required: true
                    }
                },
                messages: {
                    title: {
                        required: "请填写标题"
                    },
                    poster: {
                        required: "请填写发帖人"
                    },

                    cont: {
                        required: "请填写内容"
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest(".layui-form-item"));
                }
            });

            //定义表单验证
            $('.save-btn').on('click', function () {
                $('#form-beginner-document-add').submit();

            });

            var form = layui.form();
            var flagSubject = false;
            var flagState = false;
            form.on('select(subject)', function (data) {
                if (flagSubject) {
                    if (data.value == 'all') {
                        redirectPage("&subject=");
                    } else {
                        redirectPage("&subject=" + data.value);
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
                var subject = comJs.getUrlParam("subject");
                var state = comJs.getUrlParam("state");

                var subjectDd;
                if (subject != '') {
                    subjectDd = "dd[lay-value = " + subject + "]"
                    $('#form-subject').find(subjectDd).click();
                }

                var stateDd;
                if (state != '') {
                    stateDd = "dd[lay-value = " + state + "]"
                    $('#form-state').find(stateDd).click();
                }

                flagSubject = true;
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

