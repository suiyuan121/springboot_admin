/*
 * 通用方法
 *
 * author: Glen
 * created:　2015/5/29
 */

define(['jquery'], function($) {

    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(elt) {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0) ? Math.ceil(from) : Math.floor(from);
            if (from < 0)
                from += len;
            for (; from < len; from++) {
                if (from in this &&
                    this[from] === elt)
                    return from;
            }
            return -1;
        };
    }

    //移除数组元素
    Array.prototype.remove = function(val){
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };

    var _toQueryPair = function(key, value) {
        if (typeof value === 'undefined') {
            return key;
        }
        return key + '=' + encodeURIComponent(value === null ? '' : String(value));
    };

    return {
        //手机，邮箱验证正则式
        reMobileEmail: /^(1[34578]\d{9}|[a-zA-Z0-9_\.\-]+@(([a-zA-Z0-9])+\.)+([a-zA-Z0-9]{2,4}))$/,
        //手机
        reMobile: /^(1[34578]\d{9})$/,
        //邮件
        reEmail: /^[a-zA-Z0-9_\.\-]+@(([a-zA-Z0-9])+\.)+([a-zA-Z0-9]{2,4})$/,
        /**
         * 字符串截取
         */
        subStr: function(str, length) {
            if (str.length > length) {
                return str.substr(0, parseInt(length)) + '...';
            }
            return str;
        },
        /*
         * 去掉前后空格
         */
        strTrim: function(s) {
            return s.replace(/(^\s+)|(\s+$)/g, "");
        },
        /*
         * 解析RUI参数
         * str: uri字符串
         */
        parseURIParams: function(str) {
            var params = {},
                e,
                a = /\+/g,
                r = /([^&=]+)=?([^&]*)/g,
                d = function(s) {
                    return decodeURIComponent(s.replace(a, " "));
                };

            while ((e = r.exec(str))) {
                params[d(e[1])] = d(e[2]);
            }
            return params;
        },

        /*
         * 对像转成URI
         */
        objToQuery: function(obj) {
            var ret = [];
            for (var key in obj) {
                key = encodeURIComponent(key);
                var values = obj[key];
                if (values && values.constructor === Array) {
                    var queryValues = [];
                    for (var i = 0, len = values.length, value; i < len; i++) {
                        value = values[i];
                        queryValues.push(_toQueryPair(key, value));
                    }
                    ret = ret.concat(queryValues);
                } else {
                    ret.push(_toQueryPair(key, values));
                }
            }
            return ret.join('&');
        },
        /*
         * 取当前路径的参数值
         * arg: 参数名
         */
        parseLocation: function(arg) {
            var uri = location.search;
            if (uri !== "") {
                var argsObj = this.parseURIParams(uri.substr(1));
                return argsObj[arg] || "";
            }
            return "";
        },
        /*
         * 中文链接编码
         */
        b64EncodeUrl: function(string) {
            if (window.BASE64) {
                return BASE64.encoder(string.replace('风格', '')).replace('+', '-').replace('/', '_').replace('=', '');
            }
            return string;
        },
        /*
         * Timeago 相对时间美化  2011-05-06 12:30:22  ---> 三分钟之前
         */
        prettyDate: function(time) {
            var date = new Date((time || "").replace(/-/g, "/").replace(/[TZ]/g, " ")),
                diff = (((new Date()).getTime() - date.getTime()) / 1000),
                day_diff = Math.floor(diff / 86400);

            if (isNaN(day_diff) || day_diff < 0) {
                return;
            } else if (day_diff >= 31) {
                return time;
            }


            return day_diff === 0 && (
                    diff < 60 && "刚刚" ||
                    diff < 120 && "1分钟前" ||
                    diff < 3600 && Math.floor(diff / 60) + "分钟前" ||
                    diff < 7200 && "1个小时前" ||
                    diff < 86400 && Math.floor(diff / 3600) + "小时前") ||
                day_diff === 1 && "昨天" ||
                day_diff < 7 && day_diff + "天前" ||
                day_diff < 31 && Math.ceil(day_diff / 7) + "周前";
        },
        /**
         * 切换城市刷新URL
         */
        changeURLArg: function(url, arg, arg_val) {
            if (url.indexOf('#')) {
                url = url.split('#')[0];
            }
            var pattern = arg + '=([^&]*)';
            var replaceText = arg + '=' + arg_val;
            if (url.match(pattern)) {
                var tmp = '/(' + arg + '=)([^&]*)/gi';
                tmp = url.replace(eval(tmp), replaceText);
                return tmp;
            } else {
                if (url.match('[\?]')) {
                    return url + '&' + replaceText;
                } else {
                    return url + '?' + replaceText;
                }
            }
            return url + '\n' + arg + '\n' + arg_val;
        },
        /**
         * url跳转
         */
        locationUrl: function(url) {
            var w = window.open();
            return w.location = url;
        },
        /**
         * 计算总页数
         * total 记录总数
         * size 每页显示的记录个数
         */
        pageCount: function(total, size) {
            var count = Math.floor(total / size),
                vod = total % size;
            if (vod > 0) {
                count += 1;
            }
            return count;
        },
        /**
         * 金额格式化
         * money 数额
         * split 是否每3位添加一个分隔，通常是','，不分不要传
         */
        formatCurrency: function(money, split) {
            split = split || '';
            var num = money.toString().replace(/\$|\,/g, ''),
                sign;
            if (isNaN(num)) {
                num = "0";
            }
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if (cents < 10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                num = num.substring(0, num.length - (4 * i + 3)) + split +
                num.substring(num.length - (4 * i + 3));
            return (((sign) ? '' : '-') + num + '.' + cents);
        },
        /**
         * 链接中的Next参数
         */
        uriNext: function(def) {
            uriObj = this.parseURIParams(location.search.substr(1));
            return uriObj.next || (def || '');
        },

        //优化url，去掉url中不合法的token
        optimizeUrl: function(url) {
            var re = new RegExp("<[^>]*>", "gi");
            url = url.replace(re, "");
            return url;
        },

        //判断是否邮件
        isEmail: function(str) {
            return this.reEmail.test(str);
        },

        /*
         * 检查发布内容是否包含链接
         */
        checkContentUrl: function(content) {
            var matchStr = "fuwo";
            var flag = false;
            var indexResult;
            var re_http = new RegExp("(http[s]{0,1}|ftp)?(:)?(//)?[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?", "gi");
            var pic_re = new RegExp(".+\.(png|PNG|jpg|JPG|bmp|gif|GIF)$");
            if (content.match(re_http) === null) {
                return true;
            } else {
                var result_http = content.match(re_http) === null ? '' : content.match(re_http).toString();
                var resultArray_http = [];
                resultArray_http = result_http.split(",");
                //http验证
                if (resultArray_http !== '') {
                    for (var i = 0; i < resultArray_http.length; i++) {
                        resultArray_http[i] = this.optimizeUrl(resultArray_http[i]);
                        if (!pic_re.test(resultArray_http[i])) {
                            if (!this.isEmail(resultArray_http[i])) {
                                indexResult = resultArray_http[i].indexOf(matchStr) >= 0 ? true : false;
                                if (!indexResult) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (flag) {
                    return false;
                }
                return true;
            }
        },

        //判断发布内容中是否有广告链接
        checkUrl: function(content) {
            if (this.checkContentUrl(content) === false) {
                alert("发布内容中包含非本站点链接，请检查您的发布内容！");
                return false;
            }
            return true;
        },

        //改变锚点标签颜色
        changeAnchorColor: function(content) {
            var re_color = new RegExp("<a", 'gi');
            return content.replace(re_color, '<a style="color:rgb(120,120,200)"');
        },

        //首字母大写
        ucFirst: function(word) {
            return word.substring(0, 1).toUpperCase() + word.substring(1);
        },

        //解析url
        parseUrl: function(url){
            var regUrl = {
                protocol: /([^\/]+:)\/\/(.*)/i,
                host: /(^[^\:\/]+)((?:\/|:|$)?.*)/,
                port: /\:?([^\/]*)(\/?.*)/,
                pathname: /([^\?#]+)(\??[^#]*)(#?.*)/
            };
            var tmp, res = {};

            res["href"] = url;
            for (p in regUrl) {
                tmp = regUrl[p].exec(url);
                res[p] = tmp[1];
                url = tmp[2];
                if (url === "") {
                    url = "/";
                }
                if (p === "pathname") {
                    res["pathname"] = tmp[1];
                    res["search"] = tmp[2];
                    res["hash"] = tmp[3];
                }
            }
            return res;
        },

        //将毫秒时间转化为xx-xx-xx的格式
        getStyleTime: function(time){
            var oDate = new Date(time*1000),
                oYear = oDate.getFullYear(),
                oMonth = oDate.getMonth()+1,
                oDay = oDate.getDate(),
                oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间
            return oTime;
            function getzf(num){
                if(parseInt(num) < 10){
                    num = '0'+num;
                }
                return num;
            }
        }
    };
});
