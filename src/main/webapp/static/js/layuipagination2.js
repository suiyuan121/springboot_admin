(function () {
    loadPagination();
})();

function loadPagination() {
    var totalPages = $("#totalPages").val();
    var totalElements = $("#totalElements").val();
    //alert(totalPages);

    loadPaginationHtml();

    //  alert(totalPages + "--" + totalElements + "--");
    //侧边栏
    var element = layui.element();
    //分页
    var laypage = layui.laypage,
        layer = layui.layer;
    var curr = location.hash.replace('#!currpage=', '');
    if (curr == '') {
        curr = 1;
    }
    laypage({
        cont: 'changepage'
        , pages: totalPages
        , count: totalElements
        , skip: true
        , curr: curr
        , hash: 'currpage' //自定义hash值
        , jump: function (obj, first) {
            if (!first) {
                // console.log(obj)
                var curr = obj.curr;
                if (curr > totalPages + 1) {
                    curr = 1;
                }
                $("#currpage").val(curr);
                redirectPage();
            }
        }
    });
}

function redirectPage(param1) {
    //alert("redirectPage");
    var param = param1 || '';
    var curr = $("#currpage").val();
    var pageSize = $("#pageSize").val();

    var page = curr - 1;
    if (page < 0) {
        page = 0;
    }

    var href = '?page=' + page + '&size=' + pageSize;
    if (param != '') {
        href += param;
    }

    //获取原路径上的参数
    var data = getUrlParms();
    var params = '';
    $.each(data, function (k, v) {
        if (href.indexOf(k) >= 0) {
            //出现相同的参数,则不追加
        } else {
            params += "&" + k + "=" + v;
        }
    });

    href += params;
    href += "#!currpage=" + (page + 1);
    window.location.href = href;
}

function loadPaginationHtml() {
    var content =
        "       <input type='hidden' id='currpage'/>" +
        "       <div id='controlpage' class='pull-left'>" +
        "       <div class='layui-form-item'>" +
        "       <label class='layui-form-label'>每页显示</label>" +
        "       <div class='layui-input-inline'>" +
        "       <select name='pageSize' id='pageSize'>" +
        // "       <option value='2' >2" +
        "       <option value='10' >10" +
        "   </option>" +
        "   <option value='15' >15" +
        "   </option>" +
        "   <option value='20' >20" +
        "   </option>" +
        "   <option value='25' >25" +
        "   </option>" +
        "   <option value='30' >30" +
        "   </option>" +
        "   </select>" +
        "   </div>" +
        "   <span>条</span>" +
        "   </div>" +
        "   </div>";


    $('#changepage').before(content);
    var pageSize = getUrlParam("size");
    //alert(pageSize);
    if (pageSize > 0) {
        $("#pageSize ").val(pageSize);   // 设置Select的Value值为的项选中
    }
}

$("#pageSize").change(function () {
    //alert($("#pageSize").val());
    redirectPage();
});


//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}


function getUrlParms() {
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
        jsonData[argname] = unescape(value);
    }
    return jsonData;
}


