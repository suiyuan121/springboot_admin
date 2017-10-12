define([], function() {

    var origin = 'http://m.fuwo.com',
        staticPath = 'http://static.fuwo.com/static';

    return {
        staticPath: 'http://static.fuwo.com/static',
        defaultPosition: {
            provinceId: 35,
            provinceName: '上海',
            cityId: 3511,
            cityName: '上海'
        },
        settings: {
            xgtListCountStep: 20
        },
        reg: {
            //邮箱
            email: /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/,

            //手机号码
            mobile: /^1(3|4|5|7|8)[0-9]{9}$/,

            //账号密码
            accountPassword: /^[^\s]{6,16}$/,

            //价格（浮点数）
            price: /^(\d*\.)?\d+$/,

            //面积（0 或 正整数）
            area: /^(0|[1-9][0-9]*)$/
        },
        data: {
            //创建日记集时的风格选项数据
            diarySetStyleOptions: [{
                value: '1',
                text: '简约'
            }, {
                value: '2',
                text: '现代'
            }, {
                value: '3',
                text: '中式'
            }, {
                value: '4',
                text: '欧式'
            }, {
                value: '5',
                text: '美式'
            }, {
                value: '6',
                text: '田园'
            }, {
                value: '7',
                text: '新古典'
            }, {
                value: '8',
                text: '混搭'
            }, {
                value: '9',
                text: '地中海'
            }, {
                value: '10',
                text: '东南亚'
            }, {
                value: '11',
                text: '日式'
            }, {
                value: '12',
                text: '宜家'
            }, {
                value: '13',
                text: '北欧'
            }, {
                value: '14',
                text: '简欧'
            }]
        },
        url: {

            // ajax请求地址  =======================================================================================

            //判断账户名是否已存在
            //post { username }
            accountIsExist: origin + '/account/check_username/',

            //获取详细用户信息
            //get {}
            getUserInfo: origin + '/account/profile/api/user_info/get/',

            //提交登录信息
            //post { username, password }
            login: origin + '/account/signin/',

            //提交注册信息
            //post { username, password, verifycode }
            register: origin + '/account/signup/',

            //获取短信验证码
            //post { mobile, verifycode }
            getSms: origin + '/verifycode/mobile/create/',


            //模型包中添加模型编号
            addModelPackageNum: origin + '/verifycode/mobile/create/'


        }
    };


});
