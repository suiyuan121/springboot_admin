/*
 * 扩展validate,添加自定义验证规则
 *
 * author: Glen
 * created:　2015/5/28
 */

define([
    'jquery',
    'jqueryValidate',
    'utils',
    'services'
], function($, validate, utils, services) {

    $(function(){
        if(jQuery.validator){
            //用户名使用邮箱或者手机
            jQuery.validator.addMethod('userName', function(value, element) {
                return this.optional(element) || utils.reMobileEmail.test(value);
            }, '请填写正确的邮箱或手机号');

            jQuery.validator.addMethod('imobile', function(value, element){
                return this.optional(element) || utils.reMobile.test(value);
            }, '请填写正确的手机号');

            jQuery.validator.addMethod('iemail', function(value, element){
                return this.optional(element) || utils.reEmail.test(value);
            }, '请填写正确的邮箱');

             //密码
            jQuery.validator.addMethod('passWord', function(value, element){
                return this.optional(element) || /^.{6,16}$/.test(value);
            }, '请输入6-16位密码');

            //验证码
            jQuery.validator.addMethod('authCode', function(value, element) {
                return this.optional(element) || /^\d{6}$/.test(value);
            }, '请填写六位数字验证码');

            //中文名
            jQuery.validator.addMethod('realName', function(value, element){
                return this.optional(element) || /^[\u4E00-\u9FA5]{2,4}$/.test(value);
            }, '请输入中文真实姓名');

            //不允许5位及以上数字
            jQuery.validator.addMethod('authNumber', function(value, element){
                return this.optional(element) || !(/[0-9]{5}/.test(value));
            }, '不允许输入带有5位及以上的数字'),

            //不允许6位及以上数字
            jQuery.validator.addMethod('priorityNumber', function(value, element){
                return this.optional(element) || !(/[0-9]{6}/.test(value));
            }, '不允许输入带有6位及以上的数字'),

             //验证金额
            jQuery.validator.addMethod('checkMoney', function(value, element){
                return this.optional(element) || (/^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/.test(value));
            }, '金额必须大于等于零，小于等于99999999.99的数字'),

            //验证正整数
            jQuery.validator.addMethod("pInt", function(value, element) {
                var reg = /\b(0+)/gi;
                return this.optional(element) || (!reg.test(value));
            }, "请输入正整数"),

            //验证设计编号是32位数字和字母的组合
            jQuery.validator.addMethod('checkDesignNo', function(value, element){
                return this.optional(element) || (/(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{32}/.test(value));
            }, '设计编号由32位数字和字母组成'),

            //验证邮箱或手机是否存在
            jQuery.validator.addMethod('userSame', function(value, element) {
                var flag = 1;
                services.get('/account/check_username/', {
                    field: 'user',
                    username: value
                }, function(code, msg, data) {
                    if (code === services.CODE_SUCC) {
                        flag = data.exists;
                    }
                }, null, false);
                if (flag === 0) {
                    return true;
                } else {
                    return false;
                }
            }, '该账号已被注册，<a href="javascript:;">直接登录?</a>');
        }

    });


});
