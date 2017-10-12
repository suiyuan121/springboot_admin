define([
    'jquery',
    'msg'
], function(
    $,
    msg
){

    var successCode = '10000';

    var successHandler = function(options, data, status, xhr){
        if(typeof options.dataType === 'string' && options.dataType.toLowerCase() === 'json' && data && data.code === successCode){
            if(typeof options.success === 'function'){
                options.success.apply(this, [data.data]);
            }
        }else{
            if(typeof options.error === 'function'){
                options.error.apply(this, [data]);
            }else{
                msg.error(data.msg);
            }
        }
    };

    var errorHandler = function(options, xhr, errorType, error){
        var url = options.url || window.location.href;
        msg('请求失败!','error');
        try{ console.error('请求'+url+'失败!') }catch(e){}
    };

    var defaultOptions = {
        dataType: 'json'
        //xhrFields: window.location.origin !== 'http://m.fuwo.com' ? { withCredentials: true } : ''
    };

    return {
        invoke: function(options) {
            var opts = $.extend({}, defaultOptions, options || {});
            return $.ajax({
                type: opts.type,
                url: opts.url,
                data: opts.data,
                processData: opts.processData,                    
                contentType: opts.contentType,
                mimeType: opts.mimeType,
                dataType: opts.dataType,
                jsonp: opts.jsonp,
                jsonpCallback: opts.jsonpCallback,
                timeout: opts.timeout,
                headers: opts.headers,
                async: opts.async,
                global: opts.global,
                context: opts.context,
                traditional: opts.traditional,
                cache: opts.cache,
                xhrFields: opts.xhrFields,
                username: opts.username,
                password: opts.password,
                beforeSend: opts.beforeSend,
                complete: opts.complete,
                success: function(data, status, xhr) {
                    successHandler.apply(this, [opts, data, status, xhr]);
                },
                error: function(xhr, errorType, error) {
                    errorHandler.apply(this, [opts, xhr, errorType, error]);
                }
            });
        }
    };
});