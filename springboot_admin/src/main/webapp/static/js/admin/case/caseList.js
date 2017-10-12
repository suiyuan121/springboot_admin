
var sendData={};
require(['jquery', 'layuiAll', 'admin/common', 'ui','services',  'adminApi' ],function( $,layuiAll, comJs, ui,services,adminApi ){
            //A开始:pageJson对象
            var page = {
                            //1开始 ：开始初始化函数
                            init: function(){// page 1函数
                                                var _this = this;
                                                comJs.header();
                                                _this.operation();
                                                _this.validate();
                                             },
                            //1结束：结束初始化函数

                            //2开始 ：开始操作函数
                           operation:function(){
                                                 var layer = layui.layer;
                                                //2.1开始：开始删除案例
                                                 $(document).on('click','.delete',function(){
                                                    var $this = $(this);
                                                    var html ='<div class="document-list"><p>确定删除选中的案例吗？</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn delete-btn">删除</button></div>';
                                                    comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
                                                    $('.delete-btn').on('click',function(){
                                                            var  idValue=$this.parents('li').data('id');
                                                            $("#idId").val(idValue);
                                                            $("#deleteFormId").submit();
                                                    });
                                                });
                                                //2.1结束：结束删除案例

                                               //2.2开始：设置案例是否公开
                                                $(document).on('click','.set',function(){
                                                            var $this = $(this);
                                                            var desc = '';
                                                            var state = $this.parents('li').find('.state').text();
                                                            var setValue="";
                                                            if(state === '公开'){
                                                                desc =  '该案例正在展示中,确定设置为不公开吗';
                                                                setValue='不公开';
                                                            } else {
                                                                desc =  '确定设置选中案例为公开吗';
                                                                setValue='公开';
                                                            }
                                                            var sendData = {
                                                                id : $this.attr("data-id"),
                                                                open:setValue
                                                            };
                                                            var  sendDataStr=JSON.stringify(sendData);
                                                            var html ='<div class="document-list"><p>'+desc+'</p><button class="layui-btn layui-btn-primary">取消</button><button class="layui-btn confirm-btn">确定</button></div>';
                                                            comJs.popInfo(html,'-160px','-90px','140px','320px','absolute');
                                                            //样式为confirm-btn点击执行ajax请求
                                                            $('.confirm-btn').on('click',function(){
                                                                        $.ajax({
                                                                            url: "/admin/case/set",
                                                                            type: "POST",
                                                                            contentType : 'application/json;charset=utf-8', //设置请求头信息为json字符串，编码utf-8
                                                                            dataType:"json",//设置返回数据为json对象
                                                                            data: sendDataStr,
                                                                            success: function(data){
                                                                                var obj =data ;
                                                                                if(obj.code=='1'){
                                                                                    $(".screen-bg").remove();
                                                                                    $(".pop").remove();
                                                                                    if(state =='公开'){
                                                                                        $this.parents('li').find('.state').text("不公开");
                                                                                    }else{
                                                                                        $this.parents('li').find('.state').text("公开");
                                                                                    }
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
                                                            });
                                                  });
                                               //2.2结束:结束设置是否公开

                                                //2.3开始：设置限制文本编辑器中字体个数
                                                $('.layui-textarea').on('keyup',function(){
                                                        var $this = $(this);
                                                        var maxlength = 300;
                                                        var currentContentLength = $this.val().length;
                                                        var remainingLength = maxlength - currentContentLength;//剩余长度
                                                        if(remainingLength >= 0) {
                                                            var currentContent = $this.val();
                                                            $this.val(currentContent);
                                                            $('.length').text(remainingLength);
                                                        }
                                                });
                                                //2.3结束：结束限制字数

                                                //2.4开始：是否可售的样式调整
                                                var form = layui.form();
                                                form.on('radio(sell)', function(data){
                                                            sellValue = data.value;
                                                            if(sellValue === 'true'){
                                                                $('.price-box').removeClass('closed');
                                                            } else {
                                                                $('.price-box').addClass('closed');
                                                                //可添加清空价格
                                                            }
                                                        });
                                                //2.4结束：是否可售的样式调整

                                                //2.5开始：定义隐藏价格函数
                                                 var priceHide = function(){
                                                                        var $inputChecked = $('.sell-box').find('input').eq(0);
                                                                        if($inputChecked.attr('checked')==='checked'){
                                                                            $('.price-box').removeClass('closed');
                                                                        } else {
                                                                            $('.price-box').addClass('closed');
                                                                            //可添加清空价格
                                                                        }
                                                                     };
                                                 //2.5结束：定义结束隐藏价格

                                                  //2.6开始：开始调用价格隐藏函数
                                                 priceHide();
                                                  //2.6结束：结束调用价格隐藏函数

                                                //2.7开始：页面点击开始
                                                $(document).on('click','.effect-img-edit',function(){
                                                    var $this = $(this);
                                                    $this.parents('li').find('.edit-title').val('').removeClass('edited').removeAttr('disabled');
                                                    $this.parents('li').find('.effect-img-confirm').removeClass('closed');
                                                });
                                                //2.7结束

                                                //2.8开始：编辑名称点击确认
                                                $(document).on('click','.effect-img-confirm',function(){
                                                    var $this = $(this);
                                                    var title = $this.siblings('.edit-title').val();
                                                    $this.siblings('.edit-title').addClass('edited').attr('disabled','disabled');
                                                    $this.addClass('closed');
                                                    layer.msg("编辑成功", {time: 2000});
                                                });
                                                //2.8结束

                                                //2.9开始：删除单个效果图
                                                $(document).on('click','.effect-img-delete',function(){
                                                    var $this = $(this);
                                                    $this.parents('li').remove();
                                                    layer.msg("编辑成功", {time: 2000});
                                                });
                                                //2.9结束
                                          },
                            //2结束

                            //3开始 ： 开始验证函数
                            validate:function(){
                                       //3.1开始：  校验的具体实现
                                        $("#form-case-add").validate({
                                                        ignore: "",
                                                        rules: {
                                                                    no:{required: true},
                                                                    name: { required: true},
                                                                    designNo:{ required: true},
                                                                    link:{ required: true  },
                                                                    price: {   required: true,number: true},
                                                                    description:{  required: true }
                                                                },
                                                        messages: {
                                                                    no:{ required: "请填写案例编号"},
                                                                    name: { required: "案例名称"},
                                                                    designNo: { required: "请填设计编号"},
                                                                    link:{  required: "请填写全景图链接" },
                                                                    price: { required: "请填写价格", number: "请输入有效的数字"},
                                                                    description: {required: "请填写描述"  }
                                                                   },
                                                        errorPlacement: function(error, element) {
                                                                        error.appendTo(element.closest(".layui-form-item"));
                                                        },
                                                        submitHandler: function() {

                                                        }
                                });
                                        //3.1结束

                                        //3.2开始：点击保存提交
                                        $('.save-btn').click(function() {
                                                    var formobj=$("#form-case-add");
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
                                                    // $("#param4ID").val(jsonString);
                                                    //开始ajax
                                                    $.ajax({
                                                            url: "/admin/case/ajaxAddSave",
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
                                                    //结束ajax
                                        });
                                        //3.2结束

                                          // var flagCategory = true;
                                        // var flagTag = false;
                                        //3.3开始：方案列表页面公开筛选实现
                                        var form = layui.form();
                                        var flagState = false;//标志状态设置为false
                                        form.on('select(open-state)', function (data) {
                                                if (flagState) {
                                                    redirectPage("&searchText=" + data.value);
                                                }
                                        });
                                        //3.3结束

                                        //3.4开始：页面初始化赋值使用
                                        (function () {
                                                var searchText = comJs.getUrlParam("searchText");
                                                var searchTextDd;
                                                if (searchText != '') {
                                                    searchTextDd = "dd[lay-value = " + searchText + "]"
                                                    $('#form-open').find(searchTextDd).click();
                                                }
                                                flagState = true;
                                        })();
                                        //3.4结束
                                 }
                            //3结束：  结束验证函数
                         };
            //A结束

            //B开始：page对象初始化
            page.init();
            //B结束

      });

//上传图片2
function  uploadImg2 (fileIdArg,showId,limWidth,limHeight) {
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
                        // acceptFileTypes: /(\.|\/)(jpe?g|png|gif)$/i,
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
                                        '<input class="edit-title edited" type="text" disabled="disabled" value="编辑名称" />'+
                                        '<a class="effect-img-confirm closed" href="javascript:;">确定</a>'+
                                        '</li>';
                                    $(showId).append(html);
                                }else {
                                    $(showId).empty().append($('<a  href="' + data.result.data.url + '"target="_blank" class="img-link">' +
                                        '<img class="" src="' + data.result.data.url + '" data-fpath="' + data.result.data.fpath + '"  ></a>'));
                                }
                            } else {
                                alert(data.result.msg);
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
                                                //read函数
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
                                                        alert('请上传(' + (limWidth-50) + '~' + (limWidth+50) + ')px*(' + (limHeight-50) +'~' + (limHeight+50) +')px的图片');
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
    $("#param4ID").val(jsonString);
    // $("#caseAddForm").submit();
}

//上传图片3
function  uploadImg3 (fileIdArg,showId) {
    //模型包添加效果图
    var inputId="#"+fileIdArg;
    var showId="#"+showId;
    var $uploadFigure = $(inputId);
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
                        '<input class="edit-title edited" type="text" disabled="disabled" value="编辑名称" />'+
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


function editSubmit(){
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
    var description=$("#description2").val();

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
    $("#caseEditForm").submit();
}

function  deleteCase(obj) {
    var $this = $(obj);
    var  idValue=$this.parents('li').data('id');
    $("#idId").val(idValue);
    $("#deleteFormId").submit();

}


function backList(){
    $("#caseEditForm2").submit();
}

function backList2(){
    $("#caseAddForm2").submit();
}












