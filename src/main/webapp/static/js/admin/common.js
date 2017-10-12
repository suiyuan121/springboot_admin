define([
    'jquery',
    'ui',
    'services',
    'template',
    'adminApi',
    'validateExpand',
    'fileupload',
    'iframeTransport',
    'fileuploadProcess',
    'fileuploadValidate'
], function ($, ui, services, tpls, adminApi, validateExpand,fileupload) {
    var _popInfo = function (html, marginLeft, marginTop, height, width, absolute) {
        var data = {
            content: html,
            style: {
                marginLeft: marginLeft,
                marginTop: marginTop,
                height: height,
                width: width,
                position: absolute
            }
        };
        $("body").append(tpls("common/popup/dialog", data));
        ui.ieVersion(function () {
            $(".J-Ie7").show();
        });
        $(".layui-btn-primary").on('click', function () {
            $(".screen-bg").remove();
            $(".pop").remove();
        });
        /*$('.pop-close').on('click',function(){
            $(".screen-bg").remove();
            $(".pop").remove();
        });*/
        $(".pop-close").remove();
        $('.pop-title').remove();
    };


    return {
        //nav
        nav: function () {
            var $dd = $('.layui-nav').find('dd');
            $dd.on('click', function () {
                var $this = $(this);
                $this.addClass('lay-this');
                // $this.parents('li').children('a').css('border-left','5px solid #33B371');
            })
        },

        //header
        header: function () {
            var toLogin = $('#to-login');
            var loginOoperationList = $('.login-operation-list');
            toLogin.on('click', function () {
                loginOoperationList.addClass('open');
            })
            //点击下拉框的其它地方，下拉框隐藏
            $(document).on("click", function (e) {
                if (e.target !== $(".login-operation-list")[0] && e.target !== $('#to-login')[0]) {
                    $(".login-operation-list").removeClass('open')
                }
            });
        },

        //弹窗

        popInfo: function (html, marginLeft, marginTop, height, width, overflow) {
            _popInfo(html, marginLeft, marginTop, height, width, overflow);
        },

        //设置优先级
        setPriority: function () {
            $(document).on('click', '.set-priority', function () {
                var $this = $(this);
                var list_id = $this.parents('tr').data('id');
                var oldPriority = $this.parents('tr').find('td').eq(0).text();
                var html = '<form class="set-priority-box" id="form-set-priority">' +
                    '<p class="set">请输入1-5位数字，数字越大优先级越高</p>' +
                    '<div class="layui-input-block"><input type="text" name="title" placeholder="请输入整数" class="layui-input priority" maxlength="20" value="' + oldPriority + '"></div>' +
                    '<button class="layui-btn layui-btn-primary">取消</button>' +
                    '<button class="layui-btn save-btn">确定</button>' +
                    '</form>';
                _popInfo(html, '-160px', '-95px', '190px', '320px', 'absolute');
                $("#form-set-priority").validate({
                    ignore: "",
                    rules: {
                        title: {
                            required: true,
                            number: true,
                            range: [0, 99999]

                        }
                    },
                    messages: {
                        title: {
                            required: "请输入数字",
                            range: "请输5位以内的数字"
                        },
                    },
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest(".layui-input-block"));
                    },
                    submitHandler: function () {
                        var id = $.trim($this.parents('tr').data('id'));
                        var priority = $.trim($('.priority').val());
                        var trPriority = $this.parents('tr').find('td').eq(0);
                        var sendData = {
                            id: id,
                            priority: priority
                        };
                        adminApi.setPriority(sendData, function (code, msg, data) {
                            if (code === services.CODE_SUCC) {
                                trPriority.text(priority);
                                $(".screen-bg").remove();
                                $(".pop").remove();
                                ui.alert("success", '优先级设置成功');
                            } else {
                                ui.alert("error", msg);
                            }
                        });
                    }
                });
                $(".save-btn").on('click', function () {
                    $('#form-set-priority').submit();
                });
            });
        },

        //上传图片
        uploadImg: function ($uploadFigure,limWidth,limHeight) {
            //模型包添加效果图
            var limitImagesSize = function ($uploadFigure,limWidth,limHeight){
                var status = true;
                $uploadFigure.fileupload({
                    autoUpload: false,
                    maxFileSize: '2000000',
                    url: "/admin/common/uploadfile/",
                    acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                    disableImageMetaDataSave: true,
                    dataType: 'json',
                    formData: {
                        'moduleName': moduleName
                    },
                    done: function (e, data) {
                        if (data.result.code === services.CODE_SUCC) {
                            $('#picUrl').val(data.result.data.fpath);
                            $('.uploaded-show').empty().append($('<a  href="' + data.result.data.url + '"target="_blank" class="img-link">' +
                                '<img class="" src="' + data.result.data.url + '" data-fpath="' + data.result.data.fpath + '"></a>'));
                        } else {
                            ui.alert("error", data.result.msg);
                        }
                    },
                    add : function (e, data) {
                        var $this = $(this);
                        $.each(data.files, function (index, file) {
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                read(e.target.result);
                            };
                            reader.readAsDataURL(file);
                            function read(DataURl) {
                                var image = new Image();
                                image.onload = function () { //先要绑定load事件
                                    getSize(image);
                                    var widths = image.width;
                                    var heights = image.height;
                                };
                                image.src = DataURl;
                            };
                            function getSize(image) { //图片缩放的函数
                                var w = image.width;
                                var h = image.height;
                                if ((limWidth-50 <=w && w<= limWidth+50) && (limHeight-50 <=h && h<= limHeight + 50)) {
                                    data.submit();
                                } else {
                                    ui.alert("error", '请上传(' + (limWidth-50) + '~' + (limWidth+50) + ')px*(' + (limHeight-50) +'~' + (limHeight+50) +')px的图片');
                                }
                            }
                        });


                    },
                    messages: {
                        maxFileSize: '上传图片尺寸过大',
                        acceptFileTypes: '上传图片格式不正确'
                    },
                    processfail: function (e, data) {
                        var currentFile = data.files[data.index];
                        if (data.files.error && currentFile.error) {
                            ui.alert("error", currentFile.error);
                        }
                    }
                });
            }
            var $uploadFigure =  $("#upload-figure");
            var moduleName = $uploadFigure.next().val();
            limitImagesSize($uploadFigure,limWidth,limHeight);
        },



        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return decodeURI(r[2]);
            return null; //返回参数值
        },
        getUrlParms: function () {
            var jsonData = {};
            var query = location.search.substring(1);//获取查询串
            var pairs = query.split("&");//在逗号处断开

            for (var i = 0; i < pairs.length; i++) {
                var pos = pairs[i].indexOf('=');//查找name=value
                if (pos == -1) continue;//如果没有找到就跳过
                var argname = pairs[i].substring(0, pos);//提取name
                if (argname == "page") continue;
                if (argname == "size") continue;
                var value = pairs[i].substring(pos + 1);//提取value
                jsonData[argname] = decodeURI(value);
            }
            return jsonData;
        }

    }
});
