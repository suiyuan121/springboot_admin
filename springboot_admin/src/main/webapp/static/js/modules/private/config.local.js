define([], function (){

    var origin = 'http://192.168.4.200',
        staticPath = 'http://192.168.4.200/static';

    return {
        defaultPosition: {
            provinceId: '',
            provinceName: '上海',
            cityId: 3511,
            cityName: '上海'
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
        url: {

            // 默认图片  ===========================================================================================
            //默认帖子封面图片
            defaultTopicCoverImage: staticPath + '/msite/image/base/data/topic_cover_image.png',

            //默认日志集封面图片
            defaultDiarySetCoverImage: staticPath + '/msite/image/base/data/diary_set_cover_image.png',

            //懒加载占位图
            placeholderImage:  staticPath + '/msite/image/base/data/placeholder.jpg',


            // 页面地址  ===========================================================================================
            // 网站默认页
            defaultPage: origin + '/',

            //联系客服
            contactCustomerServicePage: 'http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT91798111&lng=cn',

            //用户中心
            userCenterPage: origin + '/html/user/admin/home.html',

            //用户自己的工地直播列表页面
            userDiaryListPage:  origin + '/html/user/admin/diary_list.html',

            //登录页面
            loginPage: origin + '/html/user/login.html',

            //预约设计页面
            inviteDesignPage: origin + '/html/invite_design.html',

            //报价页面
            evaluationPage: origin + '/html/evaluation.html',

            //城市定位页面
            citySelectionPage: origin + '/html/location.html',

            //日记详情页
            //{{id}} 日记id
            diaryDetailPage: origin + '/html/decoration/diary/detail.html?diaryid={{id}}',

            //帖子详情页
            //{{id}} 帖子id
            topicDetailPage: origin + '/html/decoration/bbs/detail.html?topicid={{id}}',

            //写日记页
            writeDiaryPage: origin + '/html/user/admin/diary_add.html',



            // ajax请求地址  =======================================================================================

            //判断账户名是否已存在
            //post { username }
            accountIsExist: origin + '/api/default.json',

            //获取详细用户信息
            //get {}
            getUserInfo: origin + '/api/default.json',

            //提交登录信息
            //post { username, password }
            login: origin + '/api/default.json',

            //提交注册信息
            //post { username, password, verifycode }
            register: origin + '/api/default.json',

            //获取短信验证码
            //post { mobile, verifycode }
            getSms: origin + '/api/default.json',


            //获取更多的帖子列表
            //get: { group_id, count, start_index }
            getMoreTopic: origin + '/api/default.json',

            //获取更多的评论
            //get: { app_label:forum, model:topic, object_id, count, start_index }
            getMoreComment: origin + '/api/default.json',

            //点赞
            //post: { object_id, app_label:comment, model:comment }
            like: origin + '/api/default.json',

            //提交评论/回复内容
            //post: { app_label:forum, model:topic, object_id, content:asdasd<p><img data-id="136535" src="http://img-test.fuwo.com/attachment/1612/22/84d76e9ac80d11e69019cd0f6677e29b.png" /></p>, parent_id }
            commentSubmit: origin + '/api/default.json',

            //评论/回复中的图片上传
            //post
            imageUpload: origin + '/api/default.json',

            //创建/编辑工地直播（日志集）
            //post { diarybook_id, title, area, style, write_date:2016-12-23, city_id }
            submitDiaryBook: origin + '/api/default.json',

            //提交工地直播（提交日志）
            //post { diarybook_id, content, write_date: 2016-12-25, fee, photo_ids:[468172,468173] }
            submitDiary: origin + '/api/default.json',

            //发表工地直播（日记）中的图片上传
            diaryImageUpload: origin + '/api/default.json',

            //获取更多的工地直播(日记组)
            //get { count, start_index }
            getMoreDiaryGroup: origin + '/api/default.json',

            //获取更多的工地直播(日记)
            //get { count, start_index }
            getMoreDiary: origin + '/api/default.json',


            //获取更多的设计师列表
            //get { count, start_index, city_id }
            getMoreDesigner: origin + '/api/default.json',

            //获取更多的工长列表
            //get { count, start_index, city_id }
            getMoreForeman: origin + '/api/default.json',

            //预约工长/设计师
            //post { name, mobile, city_id, service_type:32 }
            inviteForeman: origin + '/api/default.json',


            //获取全部省份信息
            //get {}
            getProvinceInfo: origin + '/api/province.json',

            //获取全部省份、城市信息
            //get {}
            getProvinceAndCityInfo: origin + '/api/province_city.json',

            //获取全部省份、城市、地区信息
            //get {}
            getProvinceAndCityAndAreaInfo:  origin + '/api/province_city_area.json',

            //根据省份id获取对应的城市信息
            //get { province_id }
            getCityInfoByProvinceId:  origin + '/api/get_city_info_by_province_id.json',

            //根据城市id获取对应的地区信息
            //get { city_id }
            getAreaInfoByCityId:  origin + '/api/get_area_info_by_city_id.json',



            //个人中心用户发帖
            getMoreUserOwnTopic:  origin + '/api/get_more_user_own_topic.json',

            //个人中心用户评论的帖子
            getMoreUserRepliedTopic:  origin + '/api/get_more_user_replied_topic.json',

            //个人中心用户收藏的帖子
            getMoreUserCollectedTopic:  origin + '/api/get_more_user_collected_topic.json'
        }
    };


});
