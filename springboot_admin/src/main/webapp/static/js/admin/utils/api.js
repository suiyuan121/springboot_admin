define([
    'jquery',
    'ui',
    'services'
], function ($, ui, services) {
    var designNum = $('.design-number').val();
    //designNum.substr(1,designNum.length);
    // designNum=designNum.replace('?', ' ');
    var _ADD_MODEL_PACKAGE_NUM_API = 'http://www.fuwo.com/attachment/img/upload/',
        _SET_PRIORITY_API = 'setPriority',
        _DELETE_MODEL_MATCHING_API = 'delete',
        _DELETE_MODEL_MATCHING_UNIFY_API = 'deleteModelForBatch',
        _DELETE_VIDEO_API = 'delete',
        _ADD_MODEL_MATCHING_API = 'http://www.fuwo.com/attachment/img/upload/',
        _ADD_TUTORIAL_DOCUMENT_API = 'http://www.fuwo.com/attachment/img/upload/',
        _SET_ANNOUNCEMENT__API = 'setState',
        _DELETE_BULLETIN_API = 'delete',
        _ADD_MODEL_NUM_API = 'model',
        _DELETE_MATCHED_MODEL_API = 'http://www.fuwo.com/attachment/img/upload/',
        _DELETE_MODEL_PACKAGE_API = 'delete',
        _UPDATE_MODEL_CATEGORY_API = '../model/setCategory',
        _SAVE_DESIGN_NO_API = 'http://3d.fuwo.com/ifuwo/design/item/new/list/{{designNo}}/',
        _DELETE_MODEL_APT = 'deleteModel'
        _SET_MODEL_PRICE_APT = '../model/setPrice'
        _SET_MODEL_PRICE_UNIFY_APT = '../model/setPriceForBatch'
        _CHANGE_PERSONAL_CENTER_API = 'http://www.fuwo.com/attachment/img/upload/';

    var _addModelPackageNum = function (senddata, callback) {
        services.get(_ADD_MODEL_PACKAGE_NUM_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _deleteModelMatching = function (senddata, callback) {
        services.post(_DELETE_MODEL_MATCHING_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _deleteModelMatchingUnify = function (senddata, callback) {
        services.post(_DELETE_MODEL_MATCHING_UNIFY_API, senddata, function (code, msg, data) {
            if (callback) {
                callback(code, msg, data);
            }
        });
    };

    var _deleteVideo = function (senddata, callback) {
        services.post(_DELETE_VIDEO_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _setPriority = function (senddata, callback) {
        services.post(_SET_PRIORITY_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _addModelMatching = function (senddata, callback) {
        services.get(_ADD_MODEL_MATCHING_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _addTutorialDocument = function (senddata, callback) {
        services.get(_ADD_MODEL_MATCHING_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _setState = function (senddata, callback) {
        services.post(_SET_ANNOUNCEMENT__API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _deleteBulletin = function (senddata, callback) {
        services.post(_DELETE_BULLETIN_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _addModelNum = function (senddata, callback) {
        services.get(_ADD_MODEL_NUM_API, senddata, function (code, msg, data) {
                if (callback) {
                    callback(code, msg, data);
                }
        });
    };

    var _deleteMatchedModel = function (senddata, callback) {
        services.post(_ADD_MODEL_NUM_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _deleteModelPackage = function (senddata, callback) {
        services.post(_DELETE_MODEL_PACKAGE_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _updateModelCategory = function (senddata, callback) {
        services.post(_UPDATE_MODEL_CATEGORY_API, senddata, function (code, msg, data) {
            if (callback) {
                callback(code, msg, data);
            }
        });
    };

    var _saveDesignNo = function (designNo, callback) {
        var url = _SAVE_DESIGN_NO_API.replace('{{designNo}}', designNo);
        services.get(url, {}, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        },undefined, undefined, undefined, true); //强制发送cookie
    };

    var _deleteModel = function (senddata, callback) {
        services.post(_DELETE_MODEL_APT, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _setModelPrice = function (senddata, callback) {
        services.post(_SET_MODEL_PRICE_APT, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };

    var _setModelPriceUnify = function (senddata, callback) {
        services.post(_SET_MODEL_PRICE_UNIFY_APT, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };


    var _changePersonalCenter = function (senddata, callback) {
        services.get(_CHANGE_PERSONAL_CENTER_API, senddata, function (code, msg, data) {
            if (code === services.CODE_SUCC) {
                if (callback) {
                    callback(code, msg, data);
                }
            } else {
                ui.alert("error", msg);
            }
        });
    };


    return {
        //添加模型包编号：
        addModelPackageNum: function (senddata, callback) {
            _addModelPackageNum(senddata, callback);
        },

        //设置优先级

        setPriority: function (senddata, callback) {
            _setPriority(senddata, callback);
        },

        //删除模型搭配
        deleteModelMatching: function (senddata, callback) {
            _deleteModelMatching(senddata, callback);
        },

        //批量删除模型
        deleteModelMatchingUnify: function (senddata, callback) {
            _deleteModelMatchingUnify(senddata, callback);
        },

        //删除视频
        deleteVideo: function (senddata, callback) {
            _deleteVideo(senddata, callback);
        },

        //添加模型搭配
        addModelMatching: function (senddata, callback) {
            _addModelMatching(senddata, callback);
        },

        //添加教程
        addTutorialDocument: function (senddata, callback) {
            _addTutorialDocument(senddata, callback);
        },

        //设置公告
        setState: function (senddata, callback) {
            _setState(senddata, callback);
        },

        //删除公告
        deleteBulletin: function (senddata, callback) {
            _deleteBulletin(senddata, callback);
        },

        //添加模型编号
        addModelNum: function (senddata, callback) {
            _addModelNum(senddata, callback);
        },

        //删除匹配的模型
        deleteMatchedModel: function (senddata, callback) {
            _deleteMatchedModel(senddata, callback);
        },

        //删除模型包
        deleteModelPackage: function (senddata, callback) {
            _deleteModelPackage(senddata, callback);
        },

        //更新模型的分类
        updateModelCategory: function (senddata, callback) {
            _updateModelCategory(senddata, callback);
        },

        //通过设计编号获取模型
        saveDesignNo: function (designNo, callback) {
            _saveDesignNo(designNo, callback);
        },

        //删除模型
        deleteModel: function (senddata, callback) {
            _deleteModel(senddata, callback);
        },

        //设置模型价格
        setModelPrice: function (senddata, callback) {
            _setModelPrice(senddata, callback);
        },

        //批量设置模型价格
        setModelPriceUnify: function (senddata, callback) {
            _setModelPriceUnify(senddata, callback);
        },


        //个人中心修改
        changePersonalCenter: function (senddata, callback) {
            _changePersonalCenter(senddata, callback);
        }
    };

});
