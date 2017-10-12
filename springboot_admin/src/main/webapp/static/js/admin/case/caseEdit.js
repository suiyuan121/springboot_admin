
var sendData={};
//A开始：调用require函数
require([ 'jquery', 'layuiAll', 'admin/common','ui','services', 'adminApi'],function($, layuiAll,comJs,ui,services,adminApi){
           //1开始：定义页对象
            var page = {
                        //1.1开始：定义初始化函数
                        init: function(){
                            var _this = this;
                            comJs.header();
                            _this.operation();
                            _this.validate();
                            _this.cb();

                        },
                        //1.1结束
                        //1.2开始：定义操作函数
                        operation:function(){
                            var layer = layui.layer;
                            //上传图片
                            var uploadImg = function(fileIdArg,showId,limWidth,limHeight) {
                                //模型包添加效果图
                                var inputId="#"+fileIdArg;
                                // var picId="#"+picIdArg;
                                var showId="#"+showId;
                                var $uploadFigure = $(inputId);
                                // var moduleName = $uploadFigure.next().val();
                                $uploadFigure.fileupload({
                                    autoUpload: false,
                                    maxFileSize: '2000000',
                                    url: "/admin/common/uploadfile/",
                                    acceptFileTypes: /(\.|\/)(jpe?g|png|gif)$/i,
                                    dataType: 'json',
                                    formData: {
                                        'moduleName': "case"
                                    },
                                    done: function (e, data) {
                                        if (data.result.code == '10000') {
                                            // $(picId).val(data.result.data.fpath);
                                            if(showId=='#ulAreaId'){
                                                html = '<li class="effect-img-item" data-id="">'+
                                                    '<div class="img-show">'+
                                                    '<img class="" src="' + data.result.data.url + '" data-fpath="' +  data.result.data.fpath + '">'+
                                                    '<div class="operation-btn">'+
                                                    '<a class="effect-img-btn effect-img-edit" href="javascript:;">编辑</a>'+
                                                    '<a class="effect-img-btn effect-img-delete" href="javascript:;">删除</a>'+
                                                    '</div>'+
                                                    '</div>'+
                                                    '<input class="edit-title edited" type="text" disabled="disabled" value="" />'+
                                                    '<a class="effect-img-confirm closed" href="javascript:;">确定</a>'+
                                                    '</li>';
                                                $(showId).append(html);
                                            }else {
                                                $(showId).empty().append($('<a  href="' + data.result.data.url + '"target="_blank" class="img-link">' +
                                                    '<img class="" src="' + data.result.data.url + '" data-fpath="' + data.result.data.fpath + '"  ></a>'));
                                            }
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
                                                if(fileIdArg === 'upload-cover') {
                                                    if ((limWidth-50 <=w && w<= limWidth+50) && (limHeight-50 <=h && h<= limHeight + 50))  {
                                                        data.submit();
                                                    } else {
                                                        ui.alert("error", '请上传(' + (limWidth-50) + '~' + (limWidth+50) + ')px*(' + (limHeight-50) +'~' + (limHeight+50) +')px的图片');
                                                    }
                                                } else {
                                                    if (((limWidth-200 <=w && w<= limWidth+200) && (limHeight-200 <=h && h<= limHeight + 200)) || ((limWidth*2-200<=w && w<= limWidth*2+ 200) && (limHeight*2-200 <=h && h<= limHeight*2+ 300))) {
                                                        if($('#'+fileIdArg).parent()[0].tagName.toLowerCase() === 'li'){
                                                            if( $('#'+fileIdArg).parents('ul').find('li').length >= 13) {
                                                                console.log($('#'+fileIdArg).siblings('ul').find('li').length);
                                                                ui.alert('error','最多添加十二张效果图');
                                                            } else {
                                                                data.submit();
                                                            }
                                                        } else {
                                                            data.submit();
                                                        }
                                                    } else {
                                                        ui.alert('error','请上传(' + (limWidth-200) + '~' + (limWidth+200) + ')px*(' + (limHeight-200) +'~' + (limHeight+200) +')px的图片');
                                                    }
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
                            uploadImg('upload-cover2','coverShowId2',380,300);
                            uploadImg('upload-house-type2','houseShowId2',860,480);
                            uploadImg('upload-effect-img','ulAreaId',860,480);

                            //删除案例
                            $(document).on('click','.delete',function(){
                                var $this = $(this);
                                var sendData = {
                                    order_id : $this.parents('tr').data('id')
                                };
                                var html ='<div class="document-list"><p>确定删除选中的案例吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
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

                            //设置限制文本编辑器中字体个数
                            $('.layui-textarea').on('keyup',function(){
                                var $this = $(this);
                                var maxlength = 300;
                                var currentContentLength = $this.val().length;
                                var remainingLength = maxlength - currentContentLength;
                                if(remainingLength >= 0) {
                                    var currentContent = $this.val();
                                    $this.val(currentContent);
                                    $('.length').text(remainingLength);
                                }
                            });

                            var showWords = function(){
                                var currentContentLength = $('#description2').text().length;
                                $('.length').text(300-currentContentLength);
                            }
                            showWords();

                            //是否可售的样式调整
                            var form = layui.form();

                            //编辑上传的效果图
                            $(document).on('click','.effect-img-edit',function(){
                                var $this = $(this);
                                $this.parents('li').find('.edit-title').val('').removeClass('edited').removeAttr('disabled');
                                $this.parents('li').find('.effect-img-confirm').removeClass('closed');
                            });

                            //保存效果图的名称2
                            $(document).on('click','.effect-img-confirm',function(){
                                var $this = $(this);
                                var title = $this.siblings('.edit-title').val();
                                $this.siblings('.edit-title').addClass('edited').attr('disabled','disabled');
                                $this.addClass('closed');
                                layer.msg("编辑成功", {time: 2000});
                            });

                            //删除单个效果图2
                            $(document).on('click','.effect-img-delete',function(){
                                var $this = $(this);
                                $this.parents('li').remove();
                                layer.msg("删除成功", {time: 2000});
                            });

                            $(document).on('click','.set',function(){
                                var $this = $(this);
                                var desc = '';
                                var state = $this.parents('li').find('.state').text();
                                if(state === '公开'){
                                    desc =  '该案例正在展示中,确定设置为不公开吗'
                                } else {
                                    desc =  '确定设置选中案例为公开吗'
                                }

                                var html ='<div class="document-list"><p>'+desc+'</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn confirm-btn">确定</button></div>';
                                comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
                                var sendData = {
                                    id : $this.parents('li').data('id'),
                                    open:state
                                };
                                var  sendDataStr=JSON.stringify(sendData);
                                $('.confirm-btn').on('click',function(){
                                    $.ajax({
                                        url: "/admin/case/set",
                                        type: "POST",
                                        contentType : 'application/json;charset=utf-8', //设置请求头信息
                                        dataType:"json",
                                        data: sendDataStr,            //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
                                        success: function(data){
                                            var obj =data ;
                                            if(obj.code=='1'){
                                                $(".screen-bg").remove();
                                                $(".pop").remove();
                                                ui.alert('success',"设置成功");
                                            }

                                        },
                                        error: function(data){
                                            var obj =data ;
                                            if(obj.code=='-1'){
                                                ui.alert('error',"设置失败");
                                            }
                                        }
                                    });
                                })
                            })

                        },
                        //1.2结束
                        //1.3开始：定义校验函数
                        validate:function(){
                                    //1.3.1开始：对编辑表单进行校验
                                    $("#form-edit1").validate({
                                                                    //1.3.1.1开始：忽略
                                                                    ignore: "",
                                                                    //1.3.1.2结束
                                                                    //1.3.1.3开始：规则
                                                                    rules: {
                                                                                no:{  required: true },
                                                                                name: {required: true },
                                                                                designNo:{required: true,checkDesignNo:true},
                                                                                link:{required: true },
                                                                                 // price: {required: true,number: true },
                                                                                 description:{ required: true}
                                                                             },
                                                                    //1.3.1.3结束
                                                                    //1.3.1.4开始：校验显示信息
                                                                    messages: {
                                                                                 no:{required: "请填写案例编号" },
                                                                                 name: {required: "案例名称"},
                                                                                 designNo: {required: "请填设计编号"},
                                                                                 link:{required: "请填写全景图链接"},
                                                                                 // price: { required: "请填写价格",number: "请输入有效的数字" },
                                                                                 description: {required: "请填写描述"}
                                                                              },
                                                                    //1.3.1.4结束
                                                                    //1.3.1.5开始：错误占位符
                                                                    errorPlacement: function(error, element) {
                                                                                 error.appendTo(element.closest(".layui-form-item"));
                                                                    },
                                                                    //1.3.1.5结束
                                                                    //1.3.1.6开始：提交处理函数
                                                                    submitHandler: function() {
                                                                        submitHandlerToEditSubmit();
                                                                    }
                                                                   //1.3.1.6结束
                                                                    });
                                                                    //1.3.1结束
                                                                        //1.3.2开始：编辑页面点击保存，保存编辑的案例
                                                                        $('.save-btn').click(function() {
                                                                            $("#form-edit1").submit();
                                                                        });
                                                                      //1.3.2结束
                                                                    //1.3.3开始：点击是否公开
                                                                    var form = layui.form();
                                                                    // 获取select选择框及radio单选的值,开始时给的默认值
                                                                    var stateValue = true;
                                                                    form.on('radio(state)', function(data){
                                                                        stateValue = data.value;//被点击的radio的value值
                                                                    });
                                                                    //1.3.3结束
                                                                    //1.3.4开始：点击是否可售
                                                                    form.on('radio(sell)', function(data){
                                                                        sellValue = data.value;
                                                                        if(sellValue === 'true'){
                                                                            $('.price-box').removeClass('closed');
                                                                        } else {
                                                                            $('.price-box').addClass('closed');
                                                                            $("#price2").val("");
                                                                        }
                                                                    });
                                                                    //1.3.4结束

                                                                    //1.3.5开始：定义隐藏价格函数
                                                                    var priceHide = function(){
                                                                        var $inputChecked = $('.sell-box').find('input').eq(0);
                                                                        if($inputChecked.attr('checked')==='checked'){
                                                                            $('.price-box').removeClass('closed');
                                                                        } else {
                                                                            $('.price-box').addClass('closed');
                                                                            $("#price2").val("");
                                                                        }
                                                                    };
                                                                    //调用隐藏价格
                                                                    priceHide();
                                                                // var $uploadCover = $('#upload-cover');
                                                                // var $uploadHouseType = $('#upload-house-type');
                                                                // var $effectImgItem = $('.effect-img-item');
                        },
                        //1.3结束
                        //1.4开始：定义cb函数
                        cb:function(){
                            var form = layui.form();
                            var flagSpace = false;
                            form.on('select(searchText)', function (data) {
                                if (data.value == '全部') {
                                    redirectPage("&space='全部'");
                                } else {
                                    redirectPage("&space=" + data.value);
                                }

                            });
                        },
                        //1.4结束
                     };
            //2开始：页对象调用初始化函数
            page.init();
            //2结束
        });
//B结束


//上传图片2
function  uploadImg2 (fileIdArg,showId) {
    //模型包添加效果图
    var inputId="#"+fileIdArg;
    // var picId="#"+picIdArg;
    var showId="#"+showId;
    var $uploadFigure = $(inputId);
    // var moduleName = $uploadFigure.next().val();
    $uploadFigure.fileupload({
        autoUpload: true,
        maxFileSize: '2000000',
        url: "/admin/common/uploadfile/",
        acceptFileTypes: /(\.|\/)(jpe?g|png|gif)$/i,
        dataType: 'json',
        formData: {
            'moduleName': "case"
        },
        done: function (e, data) {
            if (data.result.code == '10000') {
                // $(picId).val(data.result.data.fpath);
                if(showId=='#ulAreaId'){
                    html = '<li class="effect-img-item" data-id="">'+
                        '<div class="img-show">'+
                        '<img class="" src="' + data.result.data.url + '" data-fpath="' +  data.result.data.fpath + '">'+
                        '<div class="operation-btn">'+
                        '<a class="effect-img-btn effect-img-edit" href="javascript:;">编辑</a>'+
                        '<a class="effect-img-btn effect-img-delete" href="javascript:;">删除</a>'+
                        '</div>'+
                        '</div>'+
                        '<input class="edit-title edited" type="text" disabled="disabled" value="" />'+
                        '<a class="effect-img-confirm closed" href="javascript:;">确定</a>'+
                        '</li>';
                    $(showId).append(html);
                }else {
                    $(showId).empty().append($('<a  href="' + data.result.data.url + '"target="_blank" class="img-link">' +
                        '<img class="" src="' + data.result.data.url + '" data-fpath="' + data.result.data.fpath + '"  ></a>'));
                }
            } else {
                ui.alert("error", data.result.msg);
            }
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

function addSubmit(){
    //获取select选择框及radio单选的值,开始时给的默认值
    var stateValue = false,
        sellValue = false,
        styleValue = 1,
        houseTypeValue  = 1,
        spaceValue = 1,
        areaValue = 1;
    var form = layui.form();

    var designStyleValue=$("#designStyleId").val();
    var houseTypeValue=$("#houseStyleId").val();
    var spaceValue=$("#spaceStyleId").val();
    var areaValue=$("#areaStyleId").val();

    var caseNo = $('input[name="no"]').val(),
        name = $('input[name="name"]').val(),
        designNo = $('input[name="designNo"]').val(),
        link = $('input[name="link"]').val(),
        price = $('input[name="price"]').val(),
        description = $('.layui-textarea').val();
    var  coverPath= $("#coverShowDiv").find("img").data("fpath")//取相对路径
    //写入户型图
    var houseImg={};
    houseImg["type"]="house";
    houseImg["url"]=$("#house-show").find("img").data("fpath");
    //写入效果图
    var effectImage = {};
    var effectImages = [];
    var $effectImgItem = $('.effect-img-item');
    $effectImgItem.not('.upload-btn').each(function(i,val){
        var $this = $(this);
        effectImage = {
            url : $this.find("img").data("fpath"),
            title: $this.find('.edit-title').val(),
            type:"effect"
        };
        effectImage["sort"]=++i;
        effectImages.push(effectImage);
    });
    //案例
    var designCase={
        caseNo : caseNo,
        name : name,
        designNo : designNo,
        designStyle : designStyleValue,
        houseType : houseTypeValue,
        spaceType : spaceValue,
        areaType : areaValue,
        url : link,
        open :stateValue,
        sale : sellValue,
        price : price,
        description : description,
        coverUrl : coverPath,//封面url
    };
    //发送数据
    sendData={
        designCase:designCase,
        houseImg:houseImg,
        effectImages : effectImages
    };

    var jsonString=JSON.stringify(sendData)
    $("#param3ID").val(jsonString);
    $("#caseAddForm").submit();
}

function ajaxEditSubmit(){
    var stateValue = false,
        sellValue = false,
        styleValue = 1,
        houseTypeValue  = 1,
        spaceValue = 1,
        areaValue = 1;
    var form = layui.form();
    //测试
    var id=$("#designCaseIdId2").val();
    var designStyleValue=$("#designStyleId2").val();
    var houseTypeValue=$("#houseStyleId2").val();
    var spaceValue=$("#spaceStyleId2").val();
    var areaValue=$("#areaStyleId2").val();
    //
    var caseNo = $('input[name="no"]').val(),
        name = $('input[name="name"]').val(),
        designNo = $('input[name="designNo"]').val(),
        link = $('input[name="link"]').val(),
        price = $('input[name="price"]').val(),
        description = $('.layui-textarea').val();
    var  coverPath= $("#coverShowId2").find("img").data("fpath")//取相对路径
    //写入户型图
    var houseImg={};
    houseImg["type"]="house";
    houseImg["url"]=$("#houseShowId2").find("img").data("fpath");
    //写入效果图
    var effectImage = {};
    var effectImages = [];
    var $effectImgItem = $('.effect-img-item');
    $effectImgItem.not('.upload-btn').each(function(i,val){
        var $this = $(this);
        effectImage = {
            url : $this.find("img").data("fpath"),
            title: $this.find('.edit-title').val(),
            type:"effect"
        };
        effectImage["sort"]=++i;
        effectImages.push(effectImage);
    });
    //案例
    var designCase={
        id:id,
        caseNo : caseNo,
        name : name,
        designNo : designNo,
        designStyle : designStyleValue,
        houseType : houseTypeValue,
        spaceType : spaceValue,
        areaType : areaValue,
        url : link,
        open :stateValue,
        sale : sellValue,
        price : price,
        description : description,
        coverUrl : coverPath,//封面url
    };
    //发送数据
    sendData={
        designCase:designCase,
        houseImg:houseImg,
        effectImages : effectImages
    };

    var jsonString=JSON.stringify(sendData)
    // $("#param4ID").val(jsonString);
    $.ajax({
        url: "/admin/case/ajaxEditSubmit",
        type: "POST",
        contentType : 'application/json;charset=utf-8', //设置请求头信息
        dataType:"json",
        data: jsonString,            //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
        success: function(data){
            if(data.code=='1'){
                window.location.href="http://admin.fuwo.com/admin/case/list"
            }
        },
        error: function(data){
            var obj =data ;
            if(obj.code=='-1'){
                ui.alert('error',"保存失败");
            }
        }
    });
}

function submitHandlerToEditSubmit(){
    var stateValue = false,
        sellValue = false,
        styleValue = 1,
        houseTypeValue  = 1,
        spaceValue = 1,
        areaValue = 1;
    var form = layui.form();
    var caseId=$("#idId").val();
    var caseNo=$("#designCaseId2").val();
    var name=$("#name2").val();
    var designNo=$("#designNo2").val();
    var designStyle=$("#designStyleId2").val();

    var houseType=$("#houseId2").val();
    var spaceType=$("#spaceId2").val();
    var areaType=$("#areaId2").val();
    var url=$("#fullViewId2").val();
    var open=$("input[name='open']:checked").val();

    var sale=$("input[name='sale']:checked").val();
    var price=$("#price2").val();
    var description= $.trim($("#description2").val());

    //图片相对地址
    var coverUrl=$("#coverShowId2").find("img").attr("data-fpath");//封面url
    //写入户型图
    var houseImg={};
    var id=$("#houseShowId2").find("img").attr("data-id");
    if(id!=undefined){
        houseImg["id"]=$("#houseShowId2").find("img").attr("data-id");
    }
    houseImg["url"]=$("#houseShowId2").find("img").attr("data-fpath");
    houseImg["type"]="house";

    var  effectImages=[];
    var $effectImgItem = $('.effect-img-item');
    $effectImgItem.not('.upload-btn').each(function(i,val){
        var $this = $(this);
        var  fpath=$this.find("img").attr("data-fpath");

        if(fpath!=undefined){
            var   effectImage={};
            effectImage["url"]=fpath;
            effectImage["description"]=$this.find('.edit-title').val();
            effectImage["type"]="effect";
            effectImage["sort"]=++i;
        }
        effectImages.push(effectImage);
    });
    //案例
    var designCase={
        id:caseId,
        caseNo : caseNo,
        name : name,
        designNo : designNo,
        designStyle : designStyle,
        houseType : houseType,
        spaceType : spaceType,
        areaType : areaType,
        url : url,
        open :open,
        sale : sale,
        price : price,
        description : description,
        coverUrl : coverUrl,//封面url
    };

    //发送数据
    sendData={
        designCase:designCase,
        houseImg:houseImg,
        effectImages : effectImages
    };

    var jsonString=JSON.stringify(sendData);
    $("#param4Id").val(jsonString);
    $("#form-edit2").submit();

}

function editSubmit(){
    var stateValue = false,
        sellValue = false,
        styleValue = 1,
        houseTypeValue  = 1,
        spaceValue = 1,
        areaValue = 1;
    var form = layui.form();
    var caseId=$("#idId").val();
    var caseNo=$("#designCaseId2").val();
    var name=$("#name2").val();
    var designNo=$("#designNo2").val();
    var designStyle=$("#designStyleId2").val();

    var houseType=$("#houseId2").val();
    var spaceType=$("#spaceId2").val();
    var areaType=$("#areaId2").val();
    var url=$("#fullViewId2").val();
    var open=$("input[name='open']:checked").val();

    var sale=$("input[name='sale']:checked").val();
    var price=$("#price2").val();
    var description= $.trim($("#description2").val());

    //图片相对地址
    var coverUrl=$("#coverShowId2").find("img").attr("data-fpath");//封面url
    //写入户型图
    var houseImg={};
    var id=$("#houseShowId2").find("img").attr("data-id");
    if(id!=undefined){
        houseImg["id"]=$("#houseShowId2").find("img").attr("data-id");
    }
    houseImg["url"]=$("#houseShowId2").find("img").attr("data-fpath");
    houseImg["type"]="house";

    var  effectImages=[];
    var $effectImgItem = $('.effect-img-item');
    $effectImgItem.not('.upload-btn').each(function(i,val){
        var $this = $(this);
        var  fpath=$this.find("img").attr("data-fpath");

        if(fpath!=undefined){
            var   effectImage={};
            effectImage["url"]=fpath;
            effectImage["description"]=$this.find('.edit-title').val();
            effectImage["type"]="effect";
            effectImage["sort"]=++i;
        }
        effectImages.push(effectImage);
    });
    //案例
    var designCase={
        id:caseId,
        caseNo : caseNo,
        name : name,
        designNo : designNo,
        designStyle : designStyle,
        houseType : houseType,
        spaceType : spaceType,
        areaType : areaType,
        url : url,
        open :open,
        sale : sale,
        price : price,
        description : description,
        coverUrl : coverUrl,//封面url
    };

    //发送数据
    sendData={
        designCase:designCase,
        houseImg:houseImg,
        effectImages : effectImages
    };

    var jsonString=JSON.stringify(sendData);
    $("#param4Id").val(jsonString);
    $("#form-edit2").submit();
}

function  deleteCase(obj) {
    var $this = $(obj);
    var  idValue=$this.parents('li').data('id');
    $("#idId").val(idValue);
    $("#deleteFormId").submit();

}


function backList(){
    window.location.href="http://admin.fuwo.com/admin/case/list";
}











