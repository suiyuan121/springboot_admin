requirejs.config({
    baseUrl: (window.g_config && window.g_config.staticHost != undefined) ? "http://" + window.g_config.staticHost + "/static/js/" : '/static/js/',
    waitSeconds: 30,
    map: {
        '*': {
            'css': 'require.plugin.css'
        }
    },
    paths: {
        //lib
        'jquery': 'modules/public/jquery',
        'jqueryValidate': 'modules/public/jquery.validate',
        'layuiAll':'/static/js/modules/public/layui/lay/dest/layui.all',
        'laydate':'/static/js/modules/public/layui/lay/modules/laydate',
        'fileupload':'lib/fileupload/jquery.fileupload',
        'iframeTransport':'lib/fileupload/jquery.iframe-transport',
        'fileuploadProcess':'lib/fileupload/jquery.fileupload-process',
        'fileuploadValidate':'lib/fileupload/jquery.fileupload-validate',
        'uiWidget':'lib/fileupload/jquery.ui.widget',
        'sortable':'modules/public/Sortable',
        'ueditorMain':'modules/public/ueditor/main',
        'ueditorConfig':'modules/public/ueditor/config',
        'zeroClipboard':'lib/ueditor/third-party/zeroclipboard/ZeroClipboard',
        'ueditorLang':'lib/ueditor/lang/zh-cn/zh-cn',
        'ueditorAll':'lib/ueditor/ueditor.all',
        'icheck':'lib/icheck',
        'table2excel':'lib/jquery.table2excel.min',


        //template engine
        'mustache':'modules/public/mustache.min',
        'template':'modules/public/template',

        //base private
        'config': 'modules/private/config',
        'ajax':'modules/private/ajax',
        'utils':'modules/private/utils',
        'adminApi':'admin/utils/api',
        'services':'modules/private/services',
        'ui':'modules/private/ui',
        'account':'modules/private/account',
        'paginator':'modules/private/paginator',
        'lazyload':'modules/private/jquery.lazyload',
        'validateExpand':'modules/private/validate.expand',

        //base public
        'iHousertype':'modules/public/ueditor/plugins/inserthousetype',
        'iDesign':'modules/public/ueditor/plugins/insertdesign',



    },
    shim: {
        'swiper':['jquery'],
        'layuiAll':['jquery'],
        'laydate':['jquery'],
        'sortable':['jquery'],
        'table2excel':['jquery'],
        'masonry':['jquery'],
        'waterfall':['jquery'],
        'validate':['jquery'],
        'lazyload':['jquery'],
        'imgpreload':['jquery'],
        'mousewheel':['jquery'],
        'layui':['jquery'],
        'fileupload':['jquery'],
        'iframeTransport':['jquery'],
        'fileuploadProcess':['jquery','fileupload'],
        'fileuploadValidate':['jquery','fileuploadProcess'],
        'uiWidget':['jquery','fileupload'],
        'validateExpand':['jquery','jqueryValidate'],
    },
    config: {
        'ueditorMain': {
            contentId: 'comment_content',
            sourceEditor: 'textarea',
            iframeUrl: '/static/js/lib/ueditor',
            plugins: ['inserthousetype', 'insertdesign']
        }
    },
    /*packages: [
     "ueditor"
     ]*/
});
